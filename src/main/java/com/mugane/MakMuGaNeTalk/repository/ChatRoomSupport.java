package com.mugane.MakMuGaNeTalk.repository;

import com.mugane.MakMuGaNeTalk.entity.ChatRoom;

import java.util.List;

public interface ChatRoomSupport {
    List<ChatRoom> findAllByKeywordAndTagsAndPaging(
            List<String> tagList,
            String keyword,
            Integer pageSize,
            Integer pageNumber
    );
}
