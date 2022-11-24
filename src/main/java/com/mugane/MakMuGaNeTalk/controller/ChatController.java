package com.mugane.MakMuGaNeTalk.controller;

import com.mugane.MakMuGaNeTalk.dto.request.MessageRequestDto;
import com.mugane.MakMuGaNeTalk.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("chat.message.{chatRoomId}")
    public void chat(@DestinationVariable("chatRoomId") Long chatRoomId,
        MessageRequestDto messageRequestDto,
        @Header("Authorization") String accessToken) {
        chatService.sendMessage(chatRoomId, accessToken, messageRequestDto);
    }

}
