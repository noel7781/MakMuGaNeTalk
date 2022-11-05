package com.mugane.MakMuGaNeTalk.unittest.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.mugane.MakMuGaNeTalk.entity.ChatRoom;
import com.mugane.MakMuGaNeTalk.repository.ChatRoomRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("test")
@DataJpaTest
class ChatRoomRepositoryTest {

    @Autowired
    ChatRoomRepository chatRoomRepository;

    @Test
    void should_find_no_chatRooms_if_repository_is_empty() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAll();

        assertThat(chatRooms.isEmpty()).isTrue();
    }
}
