package com.mugane.MakMuGaNeTalk.service;

import com.mugane.MakMuGaNeTalk.dto.request.ChatRoomListRequestDto;
import com.mugane.MakMuGaNeTalk.dto.request.CreateChatRoomRequestDto;
import com.mugane.MakMuGaNeTalk.entity.ChatRoom;
import com.mugane.MakMuGaNeTalk.entity.ChatRoomInvitation;
import com.mugane.MakMuGaNeTalk.entity.ChatRoomTag;
import com.mugane.MakMuGaNeTalk.entity.Tag;
import com.mugane.MakMuGaNeTalk.entity.User;
import com.mugane.MakMuGaNeTalk.entity.UserChatRoom;
import com.mugane.MakMuGaNeTalk.enums.ChatRoomType;
import com.mugane.MakMuGaNeTalk.enums.InvitationState;
import com.mugane.MakMuGaNeTalk.enums.UserType;
import com.mugane.MakMuGaNeTalk.repository.ChatRoomInvitationRepository;
import com.mugane.MakMuGaNeTalk.repository.ChatRoomRepository;
import com.mugane.MakMuGaNeTalk.repository.ChatRoomTagRepository;
import com.mugane.MakMuGaNeTalk.repository.TagRepository;
import com.mugane.MakMuGaNeTalk.repository.UserChatRoomRepository;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final UserService userService;
    private final ChatRoomRepository chatRoomRepository;
    private final TagRepository tagRepository;
    private final ChatRoomTagRepository chatRoomTagRepository;
    private final UserChatRoomRepository userChatRoomRepository;
    private final ChatRoomInvitationRepository chatRoomInvitationRepository;

    public ChatRoom getChatRoomById(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
            .orElseThrow(() -> new IllegalStateException("Chat Room Not Found"));
    }

    public List<ChatRoom> getChatRoomList(ChatRoomListRequestDto req) {

        return chatRoomRepository.findAllByKeywordAndTagsAndPaging(
            req.getTagList(),
            req.getKeyword(),
            req.getReqList().getPageSize(),
            req.getReqList().getPageNumber()
        );
    }

    @Transactional
    public Long createChatRoom(CreateChatRoomRequestDto req) {
        Set<String> reqTag = req.getTagList();
        List<String> tagList = reqTag == null ? new ArrayList<>() : new ArrayList<>(reqTag);
        return createChatRoom(
            req.getUserId(),
            req.getChatRoomType(),
            req.getTitle(),
            req.getPassword(),
            tagList
        );
    }

    public Long createChatRoom(Long userId, ChatRoomType chatRoomType, String title,
        String password, List<String> tagContentList) {

        User user = userService.findById(userId);

        final ChatRoom chatRoom = ChatRoom.builder()
            .type(chatRoomType)
            .title(title)
            .password(password)
            .createdBy(user)
            .ownerUser(user)
            .updatedBy(user)
            .messageList(new ArrayList<>())
            .build();

//         TODO: saveAndFlush 찾아보기
        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);

        List<Tag> tagList = createTagList(tagContentList);

        createChatRoomTagList(savedChatRoom, tagList);

        UserChatRoom userChatRoom = UserChatRoom.builder()
            .user(user)
            .chatRoom(chatRoom)
            .userType(UserType.OWNER)
            .build();

        userChatRoomRepository.save(userChatRoom);
        return savedChatRoom.getId();
    }

    private void createChatRoomTagList(ChatRoom chatRoom, List<Tag> tagList) {

        List<ChatRoomTag> chatRoomTagList = new ArrayList<>();
        int turn = 1;
        for (Tag tag : tagList) {
            ChatRoomTag chatRoomTag = ChatRoomTag.builder()
                .turn(turn++)
                .chatRoom(chatRoom)
                .tag(tag)
                .build();
            chatRoomTagList.add(chatRoomTag);
        }

        chatRoomTagRepository.saveAll(chatRoomTagList);
    }

    private List<Tag> createTagList(List<String> tagContentList) {

        // TODO tagList 사이 중복은 제거된 상태로 입력이 들어온다고 가정
        List<Tag> tagList = new LinkedList<>();
        for (String tagContent : tagContentList) {
            Tag newTag = null;
            Optional<Tag> tag = tagRepository.findByContent(tagContent);
            if (!tag.isPresent()) {
                newTag = tagRepository.saveAndFlush(Tag.builder().content(tagContent).build());
                tagList.add(newTag);
                continue;
            }
//            Tag tag = tagRepository.findByContent(tagContent)
//                    .orElseGet(() -> tagRepository.saveAndFlush(Tag.builder().content(tagContent).build()));
            tagList.add(tag.get());
        }

        return tagList;
    }

    @Transactional
    public void createChatRoomInvitation(Long hostUserId, Long guestUserId, String firstMessage) {

        User hostUser = userService.findById(hostUserId);
        User questUser = userService.findById(guestUserId);

        ChatRoomInvitation chatRoomInvitation = ChatRoomInvitation.builder()
            .hostUser(hostUser)
            .guestUser(questUser)
            .firstMessage(firstMessage)
            .state(InvitationState.WAITING)
            .build();

        chatRoomInvitationRepository.save(chatRoomInvitation);
    }


    public ChatRoom getChatRoomByTitle(String title) {
        return chatRoomRepository
            .findByTitle(title)
            .orElseThrow(() -> new IllegalArgumentException("해당 이름을 가지는 채팅방이 존재하지 않습니다."));
    }
}
