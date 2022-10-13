package com.mugane.MakMuGaNeTalk.service;

import com.mugane.MakMuGaNeTalk.dto.request.ChatRoomListRequestDto;
import com.mugane.MakMuGaNeTalk.entity.ChatRoom;
import com.mugane.MakMuGaNeTalk.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public List<ChatRoom> getChatRoomList(ChatRoomListRequestDto req) {
        return chatRoomRepository.findAllByKeywordAndTagsAndPaging(
                req.getTagList(),
                req.getKeyword(),
                req.getReqList().getPageSize(),
                req.getReqList().getPageNumber()
        );
    }
}
