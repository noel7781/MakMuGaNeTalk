package com.mugane.MakMuGaNeTalk.repository;

import com.mugane.MakMuGaNeTalk.entity.ChatRoomLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomLikeRepository extends JpaRepository<ChatRoomLike, Long> {

    ChatRoomLike findByUserId(Long userId);

    ChatRoomLike findByUserIdAndChatRoomId(Long userId, Long chatRoomId);
}
