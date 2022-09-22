package com.mugane.MakMuGaNeTalk.controller;

import com.mugane.MakMuGaNeTalk.model.req.ReqChatRoomList;
import com.mugane.MakMuGaNeTalk.model.res.ResChatRoomList;
import com.mugane.MakMuGaNeTalk.repository.ChatRoomRepository;
import com.mugane.MakMuGaNeTalk.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @GetMapping("v1/chat-rooms")
    public List<ResChatRoomList> getChatRoomList(
        ReqChatRoomList req
    ) {
        return chatRoomService.getChatRoomList()
                .stream()
                .map(ResChatRoomList::of)
                .collect(Collectors.toList());
    }
}
