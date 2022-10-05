package com.mugane.MakMuGaNeTalk.controller;

import com.mugane.MakMuGaNeTalk.dto.request.ChatRoomListRequestDto;
import com.mugane.MakMuGaNeTalk.dto.response.ChatRoomListResponseDto;
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

    @GetMapping("/v1/chat-rooms")
    public List<ChatRoomListResponseDto> getChatRoomList(
        ChatRoomListRequestDto req
    ) {
        return chatRoomService.getChatRoomList(req)
                .stream()
                .map(ChatRoomListResponseDto::of)
                .collect(Collectors.toList());
    }
}
