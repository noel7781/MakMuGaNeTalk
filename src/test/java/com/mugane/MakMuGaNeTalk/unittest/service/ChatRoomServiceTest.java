package com.mugane.MakMuGaNeTalk.unittest.service;


import static org.assertj.core.api.Assertions.assertThat;

import com.mugane.MakMuGaNeTalk.entity.ChatRoom;
import com.mugane.MakMuGaNeTalk.entity.ChatRoomTag;
import com.mugane.MakMuGaNeTalk.enums.ChatRoomType;
import com.mugane.MakMuGaNeTalk.repository.ChatRoomRepository;
import com.mugane.MakMuGaNeTalk.service.ChatRoomService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class ChatRoomServiceTest {

    @Autowired
    ChatRoomService chatRoomService;

    @Autowired
    ChatRoomRepository chatRoomRepository;

    @BeforeAll
    public void setUp() {
        Long userId = 1L;
        ChatRoomType chatRoomType = ChatRoomType.OPEN_CHAT;
        String title = "TEST TITLE";
        String password = "TEST_PASSWORD";
        List<String> tagContentList = Arrays.asList("TAG1", "TAG2", "TAG3");
//        List<String> tagContentList = new ArrayList<>();

        chatRoomService.createChatRoom(
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

        assertThat(chatRoomTagList.get(0).getTag().getContent()).isEqualTo("TAG1");
        assertThat(chatRoomTagList.get(1).getTag().getContent()).isEqualTo("TAG2");
        assertThat(chatRoomTagList.get(2).getTag().getContent()).isEqualTo("TAG3");
    }

    @Test
    public void findAll() {
        List<ChatRoom> chatRoomList = chatRoomRepository.findAllByKeywordAndTagsAndPaging(null,
            null, 10, 0);

        assertThat(chatRoomList.size()).isEqualTo(1);
    }
}
