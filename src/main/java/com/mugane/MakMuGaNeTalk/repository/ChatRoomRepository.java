package com.mugane.MakMuGaNeTalk.repository;

import com.mugane.MakMuGaNeTalk.model.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, ChatRoomSupport {
}
