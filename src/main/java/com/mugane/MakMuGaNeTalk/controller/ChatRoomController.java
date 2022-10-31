package com.mugane.MakMuGaNeTalk.controller;

import com.mugane.MakMuGaNeTalk.dto.request.ChatRoomListRequestDto;
import com.mugane.MakMuGaNeTalk.dto.request.CreateChatRoomInvitationRequestDto;
import com.mugane.MakMuGaNeTalk.dto.request.CreateChatRoomRequestDto;
import com.mugane.MakMuGaNeTalk.dto.response.ChatRoomListResponseDto;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/v1/chat-rooms")
    public ResponseEntity<List<ChatRoomListResponseDto>> getChatRoomList(
        @RequestParam ChatRoomListRequestDto req
    ) {

        return ResponseEntity.status(HttpStatus.OK).body(
            chatRoomService.getChatRoomList(req)
                .stream()
                .map(ChatRoomListResponseDto::of)
                .collect(Collectors.toList()));
    }

    @PostMapping("/v1/chat-rooms")
    public ResponseEntity<?> createChatRoom(
        @RequestBody CreateChatRoomRequestDto req
    ) {

        log.info("req : " + req.toString());

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

        return ResponseEntity.status(HttpStatus.OK).body();
    }

    @PostMapping("/v1/chat-room-invitations")
    public ResponseEntity<?> createChatRoom(
        @RequestBody CreateChatRoomInvitationRequestDto req
    ) {

        chatRoomService.createChatRoomInvitation(req.getHostUserId(), req.getGuestUserId(),
            req.getFirstMessage());
        return ResponseEntity.status(HttpStatus.OK).body();
    }


}
