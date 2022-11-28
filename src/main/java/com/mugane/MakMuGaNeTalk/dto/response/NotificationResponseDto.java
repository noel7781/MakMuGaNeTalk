package com.mugane.MakMuGaNeTalk.dto.response;

import com.mugane.MakMuGaNeTalk.entity.Notification;
import com.mugane.MakMuGaNeTalk.enums.NotificationType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
public class NotificationResponseDto {

    private String senderNickname;
    private String content;

    private String url;

    private NotificationType notificationType;


    @Builder
    public NotificationResponseDto(String senderNickname, String content, String url,
        NotificationType notificationType) {
        this.senderNickname = senderNickname;
        this.content = content;
        this.url = url;
        this.notificationType = notificationType;
    }

    private static NotificationResponseDto toDto(Notification notification) {
        return NotificationResponseDto.builder()
            .senderNickname(notification.getSenderNickname())
            .content(notification.getContent())
            .url(notification.getUrl())
            .notificationType(notification.getNotificationType())
            .build();
    }

    public static NotificationResponseDto create(Notification notification) {
        return toDto(notification);
    }
}
