package com.mugane.MakMuGaNeTalk.service;

import com.mugane.MakMuGaNeTalk.entity.ChatRoom;
import com.mugane.MakMuGaNeTalk.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public ChatRoom findById(Long id) {
        return chatRoomRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 아이디를 가지는 채팅방이 존재하지 않습니다."));
    }

    public ChatRoom findByTitle(String title) {
        return chatRoomRepository
            .findByTitle(title)
            .orElseThrow(() -> new IllegalArgumentException("해당 이름을 가지는 채팅방이 존재하지 않습니다."));
    }

}
