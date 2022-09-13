package com.mugane.MakMuGaNeTalk.repository;

import com.mugane.MakMuGaNeTalk.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
