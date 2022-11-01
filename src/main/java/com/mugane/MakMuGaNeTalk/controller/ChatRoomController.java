package com.mugane.MakMuGaNeTalk.controller;

import com.mugane.MakMuGaNeTalk.dto.request.ChatRoomListRequestDto;
import com.mugane.MakMuGaNeTalk.dto.request.CreateChatRoomInvitationRequestDto;
import com.mugane.MakMuGaNeTalk.dto.request.CreateChatRoomRequestDto;
import com.mugane.MakMuGaNeTalk.dto.response.ChatRoomResponseDto;
import com.mugane.MakMuGaNeTalk.entity.ChatRoom;
import com.mugane.MakMuGaNeTalk.enums.ChatRoomType;
import com.mugane.MakMuGaNeTalk.service.ChatRoomService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ChatRoomResponseDto>> getChatRoomList(
        ChatRoomListRequestDto req
    ) {
        if (req == null) {
            req = new ChatRoomListRequestDto();
        }

        List<ChatRoom> chatRoomList = chatRoomService.getChatRoomList(req);

        return ResponseEntity.status(HttpStatus.OK).body(
            chatRoomService.getChatRoomList(req)
                .stream()
                .map(ChatRoomResponseDto::of)
                .collect(Collectors.toList()));
    }

    @PostMapping("/v1/chat-rooms")
    public ResponseEntity<?> createChatRoom(
        @RequestBody CreateChatRoomRequestDto req
    ) {

        ChatRoomType chatRoomType = req.getChatRoomType();

        if (chatRoomType == ChatRoomType.ONEONONE_CHAT) {
            Long chatRoomId = chatRoomService.createChatRoom(req);

            return ResponseEntity.status(HttpStatus.OK).body(chatRoomId);
        }
        if (chatRoomType == ChatRoomType.PRIVATE_CHAT && req.getPassword() == null) {
            throw new IllegalArgumentException("채팅방 비밀번호가 없습니다.");
        }
        if (req.getTitle().isBlank()) {
            throw new IllegalArgumentException("채팅방 제목이 없습니다.");
        }

        Long chatRoomId = chatRoomService.createChatRoom(req);

        return ResponseEntity.status(HttpStatus.OK).body(chatRoomId);
    }

    @PostMapping("/v1/chat-room-invitations")
    public ResponseEntity<?> createChatRoom(
        @RequestBody CreateChatRoomInvitationRequestDto req
    ) {

        chatRoomService.createChatRoomInvitation(req.getHostUserId(), req.getGuestUserId(),
            req.getFirstMessage());
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }


}
