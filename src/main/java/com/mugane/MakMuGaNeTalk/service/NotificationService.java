package com.mugane.MakMuGaNeTalk.service;

import com.mugane.MakMuGaNeTalk.dto.response.NotificationResponseDto;
import com.mugane.MakMuGaNeTalk.entity.Notification;
import com.mugane.MakMuGaNeTalk.entity.User;
import com.mugane.MakMuGaNeTalk.enums.NotificationType;
import com.mugane.MakMuGaNeTalk.repository.EmitterRepository;
import com.mugane.MakMuGaNeTalk.repository.NotificationRepository;
import java.io.IOException;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class NotificationService {

    private final EmitterRepository emitterRepository;
    private final NotificationRepository notificationRepository;

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    public NotificationService(EmitterRepository emitterRepository,
        NotificationRepository notificationRepository) {
        this.emitterRepository = emitterRepository;
        this.notificationRepository = notificationRepository;
    }

    public SseEmitter subscribe(Long userId, String lastEventId) {
        String emitterId = makeTimeIncludeId(userId);
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        // 503 에러를 방지하기 위한 더미 이벤트 전송
        String eventId = makeTimeIncludeId(userId);
        sendNotification(emitter, eventId, emitterId,
            NotificationResponseDto.create(
                Notification.builder().content("EventStream Created. [userId=" + userId + "]")
                    .build()));

        // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
        if (hasLostData(lastEventId)) {
            sendLostData(lastEventId, userId, emitterId, emitter);
        }

        return emitter;
    }

    private String makeTimeIncludeId(Long userId) {
        return userId + "_" + System.currentTimeMillis();
    }

    private void sendNotification(SseEmitter emitter, String eventId, String emitterId,
        NotificationResponseDto data) {
        try {
            emitter.send(SseEmitter.event()
                .id(eventId)
                .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(emitterId);
        }
    }

    private boolean hasLostData(String lastEventId) {
        return !lastEventId.isEmpty();
    }

    private void sendLostData(String lastEventId, Long userId, String emitterId,
        SseEmitter emitter) {
        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByMemberId(
            String.valueOf(userId));
        eventCaches.entrySet().stream()
            .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
            .forEach(
                entry -> sendNotification(emitter, entry.getKey(), emitterId,
                    (NotificationResponseDto) entry.getValue()));
    }

    public void send(User receiver, NotificationType notificationType, String senderNickname,
        String content,
        String url) {
        Notification notification = notificationRepository.save(
            createNotification(receiver, notificationType, senderNickname, content, url));

        String receiverId = String.valueOf(receiver.getId());
        String eventId = receiverId + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByMemberId(
            receiverId);
        emitters.forEach(
            (key, emitter) -> {
                emitterRepository.saveEventCache(key, notification);
                sendNotification(emitter, eventId, key,
                    NotificationResponseDto.create(notification));
            }
        );
    }

    private Notification createNotification(User receiver,
        NotificationType notificationType,
        String senderNickname,
        String content, String url) {
        return Notification.builder()
            .receiver(receiver)
            .notificationType(notificationType)
            .senderNickname(senderNickname)
            .content(content)
            .url(url)
            .isRead(false)
            .build();
    }

}
