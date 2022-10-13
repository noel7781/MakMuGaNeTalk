package com.mugane.MakMuGaNeTalk.unittest.repository;

import com.mugane.MakMuGaNeTalk.entity.ChatRoom;
import com.mugane.MakMuGaNeTalk.repository.ChatRoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ActiveProfiles("test")
@DataJpaTest
public class ChatRoomRepositoryTest {

    @Autowired
    ChatRoomRepository chatRoomRepository;

    @Test
    public void should_find_no_chatRooms_if_repository_is_empty() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAll();

        assertThat(chatRooms.size(), is(3));
    }
}
