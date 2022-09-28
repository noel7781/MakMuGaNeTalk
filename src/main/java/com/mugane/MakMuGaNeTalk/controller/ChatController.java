package com.mugane.MakMuGaNeTalk.controller;

import com.mugane.MakMuGaNeTalk.dto.MessageDto;
import com.mugane.MakMuGaNeTalk.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;


    @MessageMapping("/chat/{chatRoomTitle}")
    public void chat(@DestinationVariable("chatRoomTitle") String chatRoomTitle, MessageDto message)
        throws Exception {
        chatService.sendMessage(chatRoomTitle, message);
    }

}
