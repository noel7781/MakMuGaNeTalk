package com.mugane.MakMuGaNeTalk.unittest.service;

import com.mugane.MakMuGaNeTalk.entity.ChatRoom;
import com.mugane.MakMuGaNeTalk.entity.ChatRoomTag;
import com.mugane.MakMuGaNeTalk.entity.Tag;
import com.mugane.MakMuGaNeTalk.enums.ChatRoomType;
import com.mugane.MakMuGaNeTalk.repository.ChatRoomRepository;
import com.mugane.MakMuGaNeTalk.service.ChatRoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ActiveProfiles("test")
@SpringBootTest
public class ChatRoomServiceTest {
    @Autowired
    ChatRoomService chatRoomService;

    @Autowired
    ChatRoomRepository chatRoomRepository;

    @BeforeEach
    public void setUp() {
        Long userId = 1L;
        ChatRoomType chatRoomType = ChatRoomType.OPEN_CHAT;
        String title = "TEST TITLE";
        String password = "TEST_PASSWORD";
        List<String> tagContentList = Arrays.asList("TAG1", "TAG2", "TAG3");

        chatRoomService.createGroupChatRoom(
                userId,
                chatRoomType,
                title,
                password,
                tagContentList
        );
    }

    @Transactional
    @Test
    public void testCreatGroupChatRoom_tagList() {

        ChatRoom chatRoom = chatRoomService.getChatRoomById(1L);

        List<ChatRoomTag> chatRoomTagList = chatRoom.getChatRoomTagList();

        assertThat(chatRoomTagList.get(0).getTag().getContent(), is("TAG1"));
        assertThat(chatRoomTagList.get(1).getTag().getContent(), is("TAG2"));
        assertThat(chatRoomTagList.get(1).getTag().getContent(), is("TAG3"));
    }

    @Test
    public void findAll() {
        List<ChatRoom> chatRoomList = chatRoomRepository.findAllByKeywordAndTagsAndPaging(null, null, 1,10);

        assertThat(chatRoomList.size(), is(1));
    }
}
