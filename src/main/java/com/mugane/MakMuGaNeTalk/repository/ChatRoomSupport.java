package com.mugane.MakMuGaNeTalk.repository;

import com.mugane.MakMuGaNeTalk.entity.ChatRoom;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChatRoomSupport {

    Page<ChatRoom> findAllByKeywordAndTagsAndPaging(
        List<String> tagList,
        String keyword,
        Pageable pageable
    );

    Page<ChatRoom> findAllByKeywordAndTagsAndPaging(
        Long userId,
        List<String> tagList,
        String keyword,
        Pageable pageable
    );
}
