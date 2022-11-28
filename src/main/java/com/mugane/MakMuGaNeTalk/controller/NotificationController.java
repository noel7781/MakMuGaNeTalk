package com.mugane.MakMuGaNeTalk.controller;

import com.mugane.MakMuGaNeTalk.entity.User;
import com.mugane.MakMuGaNeTalk.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RestController
@RequestMapping("/api/v1/alarm")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    public SseEmitter subscribe(
        @AuthenticationPrincipal User user,
        @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId
    ) {
        SseEmitter subscribe = notificationService.subscribe(user.getId(), lastEventId);
        return subscribe;
    }
}
