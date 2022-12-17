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

    @MessageMapping("chat.enter.{chatRoomId}")
    public void enter(@DestinationVariable("chatRoomId") Long chatRoomId,
        @Header("Authorization") String accessToken) {
        chatService.enter(chatRoomId, accessToken);
    }

    @MessageMapping("chat.leave.{chatRoomId}")
    public void leave(@DestinationVariable("chatRoomId") Long chatRoomId,
        @Header("Authorization") String accessToken) {
        chatService.leave(chatRoomId, accessToken);
    }

    @MessageMapping("chat.message.{chatRoomId}")
    public void chat(@DestinationVariable("chatRoomId") Long chatRoomId,
        MessageRequestDto messageRequestDto,
        @Header("Authorization") String accessToken) {
        chatService.sendMessage(chatRoomId, accessToken, messageRequestDto);
    }

}
