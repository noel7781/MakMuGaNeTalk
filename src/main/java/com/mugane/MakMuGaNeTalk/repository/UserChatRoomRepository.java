package com.mugane.MakMuGaNeTalk.repository;

import com.mugane.MakMuGaNeTalk.entity.UserChatRoom;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserChatRoomRepository extends JpaRepository<UserChatRoom, Long> {

    public Optional<UserChatRoom> findByChatRoomIdAndUserId(Long chatRoomId, Long userId);
}
