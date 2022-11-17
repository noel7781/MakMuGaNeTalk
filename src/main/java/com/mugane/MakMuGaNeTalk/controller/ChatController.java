package com.mugane.MakMuGaNeTalk.controller;

import com.mugane.MakMuGaNeTalk.dto.request.MessageRequestDto;
import com.mugane.MakMuGaNeTalk.entity.User;
import com.mugane.MakMuGaNeTalk.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;


    @MessageMapping("chat.message.{chatRoomId}")
    public void chat(@DestinationVariable("chatRoomId") Long chatRoomId,
        MessageRequestDto messageRequestDto, @AuthenticationPrincipal User user) {
        chatService.sendMessage(chatRoomId, user, messageRequestDto);
    }

}
