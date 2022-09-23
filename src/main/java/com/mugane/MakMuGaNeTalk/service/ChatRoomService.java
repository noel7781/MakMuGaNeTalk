package com.mugane.MakMuGaNeTalk.service;

import com.mugane.MakMuGaNeTalk.model.entity.ChatRoom;
import com.mugane.MakMuGaNeTalk.model.req.ReqChatRoomList;
import com.mugane.MakMuGaNeTalk.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public List<ChatRoom> getChatRoomList(ReqChatRoomList req) {
        return chatRoomRepository.findAll(
                req.getTagList(),
                req.getKeyword(),
                req.getReqList().getPageSize(),
                req.getReqList().getPrevLastSeq()
        );
    }
}
