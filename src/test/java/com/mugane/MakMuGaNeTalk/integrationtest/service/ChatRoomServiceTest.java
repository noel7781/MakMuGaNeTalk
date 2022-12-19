package com.mugane.MakMuGaNeTalk.integrationtest.service;


import static org.assertj.core.api.Assertions.assertThat;

import com.mugane.MakMuGaNeTalk.entity.ChatRoom;
import com.mugane.MakMuGaNeTalk.entity.ChatRoomTag;
import com.mugane.MakMuGaNeTalk.entity.User;
import com.mugane.MakMuGaNeTalk.enums.ChatRoomType;
import com.mugane.MakMuGaNeTalk.repository.ChatRoomRepository;
import com.mugane.MakMuGaNeTalk.repository.TagRepository;
import com.mugane.MakMuGaNeTalk.repository.UserRepository;
import com.mugane.MakMuGaNeTalk.service.ChatRoomService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Autowired
    TagRepository tagRepository;
    @Autowired
    UserRepository userRepository;

    @BeforeAll
    public void setUp() {
        Long userId = 1L;
        User user = User.builder()
            .id(userId)
            .build();
        userRepository.save(user);
        ChatRoomType chatRoomType = ChatRoomType.PRIVATE_CHAT;
        String title = "TEST TITLE";
        String password = "TEST_PASSWORD";
        List<String> tagContentList = Arrays.asList("TAG1", "TAG2", "TAG3");

        chatRoomService.createChatRoom(
            userId,
            chatRoomType,
            title,
            password,
            tagContentList
        );
        Long userId2 = 2L;
        User user2 = User.builder()
            .id(userId2)
            .build();
        userRepository.save(user2);
        ChatRoomType chatRoomType2 = ChatRoomType.OPEN_CHAT;
        String title2 = "TEST TITLE2";
        String password2 = "";
        List<String> tagContentList2 = Arrays.asList("TAG4", "TAG2", "TAG3");

        ChatRoom chatRoom = chatRoomService.createChatRoom(
            userId2,
            chatRoomType2,
            title2,
            password2,
            tagContentList2
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

    @Transactional
    @Test
    @DisplayName("중복 태그 생성 테스트")
    public void duplicate_tag_insert() {
        Long userId = 1L;
        ChatRoomType chatRoomType = ChatRoomType.OPEN_CHAT;
        String title = "TEST TITLE2";
        String password = "";
        List<String> tagContentList = Arrays.asList("TAG4", "TAG2", "TAG3");

        ChatRoom chatRoom = chatRoomService.createChatRoom(
            userId,
            chatRoomType,
            title,
            password,
            tagContentList
        );
        assertThat(tagRepository.findAll().size()).isEqualTo(4);
    }

    @Test
    public void findAll() {
        Page<ChatRoom> chatRoomList = chatRoomRepository.findAllByKeywordAndTagsAndPaging(null,
            null, PageRequest.of(0, 10));
        Page<ChatRoom> uId1ChatRoomList = chatRoomRepository.findAllByKeywordAndTagsAndPaging(1L,
            null,
            null, PageRequest.of(0, 10));

        assertThat(chatRoomList.getNumberOfElements()).isEqualTo(2);
        assertThat(uId1ChatRoomList.getNumberOfElements()).isEqualTo(1);
    }
}
