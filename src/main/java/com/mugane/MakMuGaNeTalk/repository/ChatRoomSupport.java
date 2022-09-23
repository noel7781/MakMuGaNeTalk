package com.mugane.MakMuGaNeTalk.repository;

import com.mugane.MakMuGaNeTalk.model.entity.ChatRoom;

import java.util.List;

public interface ChatRoomSupport {
    List<ChatRoom> findAll(
//            TODO Add Long userSeq,
            List<String> tagList,
            String keyword,
            Integer pageSize,
            Long prevLastPostSeq
    );
}
