package com.mugane.MakMuGaNeTalk.controller;

import com.mugane.MakMuGaNeTalk.common.ApiResponse;
import com.mugane.MakMuGaNeTalk.dto.request.ChatRoomListRequestDto;
import com.mugane.MakMuGaNeTalk.dto.request.CreateChatRoomInvitationRequestDto;
import com.mugane.MakMuGaNeTalk.dto.request.CreateChatRoomRequestDto;
import com.mugane.MakMuGaNeTalk.dto.response.ChatRoomListResponseDto;
import com.mugane.MakMuGaNeTalk.enums.ChatRoomType;
import com.mugane.MakMuGaNeTalk.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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

    @PostMapping("/v1/chat-rooms")
    public ApiResponse<?> createChatRoom(
            CreateChatRoomRequestDto req
    ) {

        ChatRoomType chatRoomType = req.getChatRoomType();

        if (chatRoomType == ChatRoomType.ONEONONE_CHAT) {
            // TODO
        }

        if (chatRoomType == ChatRoomType.OPEN_CHAT || chatRoomType == ChatRoomType.PRIVATE_CHAT) {
            if (req.getTitle().isBlank()) {
                throw new IllegalArgumentException("채팅방 제목이 없습니다.");
            }

            if (chatRoomType == ChatRoomType.PRIVATE_CHAT) {
                throw new IllegalArgumentException("채팅방 비밀번호가 없습니다.");
            }
        }

        return ApiResponse.success();
    }

    @PostMapping("/v1/chat-room-invitations")
    public ApiResponse<?> createChatRoom(
            CreateChatRoomInvitationRequestDto req
    ) {

        chatRoomService.createChatRoomInvitation(req.getHostUserId(), req.getGuestUserId(), req.getFirstMessage());
        return ApiResponse.success();
    }



}
