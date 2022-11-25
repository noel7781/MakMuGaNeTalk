package com.mugane.MakMuGaNeTalk.controller;

import com.mugane.MakMuGaNeTalk.dto.request.ChatRoomListRequestDto;
import com.mugane.MakMuGaNeTalk.dto.request.CreateChatRoomInvitationRequestDto;
import com.mugane.MakMuGaNeTalk.dto.request.CreateChatRoomRequestDto;
import com.mugane.MakMuGaNeTalk.dto.request.LikeButtonRequestDto;
import com.mugane.MakMuGaNeTalk.dto.response.ChatRoomListResponseDto;
import com.mugane.MakMuGaNeTalk.entity.ChatRoom;
import com.mugane.MakMuGaNeTalk.entity.User;
import com.mugane.MakMuGaNeTalk.enums.ChatRoomType;
import com.mugane.MakMuGaNeTalk.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/v1/chat-rooms")
    public ResponseEntity<ChatRoomListResponseDto> getChatRoomList
        (
            ChatRoomListRequestDto req, Pageable pageable,
            @AuthenticationPrincipal User user
        ) {
        log.warn("req = {}", req);
        if (req == null) {
            req = new ChatRoomListRequestDto();
        }

        Page<ChatRoom> chatRoomList = chatRoomService.getChatRoomList(req, user.getId(), pageable);
        ChatRoomListResponseDto chatRoomListResponseDto = ChatRoomListResponseDto
            .builder()
            .chatRoom(chatRoomList.toList())
            .userId(user.getId())
            .currentPageNumber(pageable.getPageNumber())
            .totalPageNumber(chatRoomList.getTotalPages())
            .build();

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(chatRoomListResponseDto);
    }

    @PostMapping("/v1/chat-rooms")
    public ResponseEntity<?> createChatRoom(
        @RequestBody CreateChatRoomRequestDto req, @AuthenticationPrincipal User user
    ) {

        log.warn("user : {}", user);
        ChatRoomType chatRoomType = req.getChatRoomType();

        if (chatRoomType == ChatRoomType.ONEONONE_CHAT) {
            Long chatRoomId = chatRoomService.createChatRoom(req, user.getId());

            return ResponseEntity.status(HttpStatus.OK).body(chatRoomId);
        }
        if (chatRoomType == ChatRoomType.PRIVATE_CHAT && req.getPassword() == null) {
            throw new IllegalArgumentException("채팅방 비밀번호가 없습니다.");
        }
        if (req.getTitle().isBlank()) {
            throw new IllegalArgumentException("채팅방 제목이 없습니다.");
        }

        Long chatRoomId = chatRoomService.createChatRoom(req, user.getId());

        return ResponseEntity.status(HttpStatus.OK).body(chatRoomId);
    }

    @PostMapping("/v1/chat-room-invitations")
    public ResponseEntity<?> createChatRoom(
        @RequestBody CreateChatRoomInvitationRequestDto req, @AuthenticationPrincipal User user
    ) {

        chatRoomService.createChatRoomInvitation(user, req.getGuestUserId(), req.getFirstMessage());
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    @PostMapping("/v1/chat-rooms-likes")
    public ResponseEntity<?> clickLikeButton(
        @RequestBody LikeButtonRequestDto req, @AuthenticationPrincipal User user
    ) {
        log.warn("like req = {}", req);
        chatRoomService.handleLikeButton(req, user);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }
}
