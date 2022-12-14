package com.mugane.MakMuGaNeTalk.service;

import com.mugane.MakMuGaNeTalk.dto.UserDto;
import com.mugane.MakMuGaNeTalk.dto.request.ChatRoomListRequestDto;
import com.mugane.MakMuGaNeTalk.dto.request.CreateChatRoomRequestDto;
import com.mugane.MakMuGaNeTalk.dto.request.LikeButtonRequestDto;
import com.mugane.MakMuGaNeTalk.dto.response.ChatRoomListResponseDto;
import com.mugane.MakMuGaNeTalk.dto.response.MessageResponseDto;
import com.mugane.MakMuGaNeTalk.entity.ChatRoom;
import com.mugane.MakMuGaNeTalk.entity.ChatRoomInvitation;
import com.mugane.MakMuGaNeTalk.entity.ChatRoomLike;
import com.mugane.MakMuGaNeTalk.entity.ChatRoomTag;
import com.mugane.MakMuGaNeTalk.entity.Message;
import com.mugane.MakMuGaNeTalk.entity.Tag;
import com.mugane.MakMuGaNeTalk.entity.User;
import com.mugane.MakMuGaNeTalk.entity.UserChatRoom;
import com.mugane.MakMuGaNeTalk.enums.ChatRoomType;
import com.mugane.MakMuGaNeTalk.enums.InvitationState;
import com.mugane.MakMuGaNeTalk.enums.NotificationType;
import com.mugane.MakMuGaNeTalk.enums.UserType;
import com.mugane.MakMuGaNeTalk.repository.ChatRoomInvitationRepository;
import com.mugane.MakMuGaNeTalk.repository.ChatRoomLikeRepository;
import com.mugane.MakMuGaNeTalk.repository.ChatRoomRepository;
import com.mugane.MakMuGaNeTalk.repository.ChatRoomTagRepository;
import com.mugane.MakMuGaNeTalk.repository.TagRepository;
import com.mugane.MakMuGaNeTalk.repository.UserChatRoomRepository;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final ChatRoomLikeRepository chatRoomLikeRepository;
    private final NotificationService notificationService;

    @Transactional(readOnly = true)
    public ChatRoom getChatRoomById(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
            .orElseThrow(() -> new IllegalArgumentException("?????? ???????????? ????????? ???????????? ???????????? ????????????."));
    }

    @Transactional(readOnly = true)
    public ChatRoomListResponseDto getChatRoomList(ChatRoomListRequestDto req, UserDto user,
        Pageable pageable) {

        Page<ChatRoom> chatRoomList;
        if (req.isJoined()) {
            chatRoomList = chatRoomRepository.findAllByKeywordAndTagsAndPaging(
                user.getId(),
                req.getTagList(),
                req.getKeyword(),
                pageable
            );
        } else {
            chatRoomList = chatRoomRepository.findAllByKeywordAndTagsAndPaging(
                req.getTagList(),
                req.getKeyword(),
                pageable
            );
        }

        return ChatRoomListResponseDto
            .builder()
            .chatRoom(chatRoomList)
            .userId(user.getId())
            .currentPageNumber(chatRoomList.getPageable().getPageNumber())
            .totalPageNumber(chatRoomList.getTotalPages())
            .build();
    }

    @Transactional
    public Long createChatRoom(CreateChatRoomRequestDto req, Long userId) {
        Set<String> reqTag = req.getTagList();
        List<String> tagList = reqTag == null ? new ArrayList<>() : new ArrayList<>(reqTag);
        return createChatRoom(
            userId,
            req.getChatRoomType(),
            req.getTitle(),
            req.getPassword(),
            tagList
        ).getId();
    }

    @Transactional
    public ChatRoom createChatRoom(Long userId, ChatRoomType chatRoomType, String title,
        String password, List<String> tagContentList) {

        User user = userService.findById(userId);

        final ChatRoom chatRoom = ChatRoom.builder()
            .type(chatRoomType)
            .title(title)
            .password(password)
            .ownerUser(user)
            .messageList(new ArrayList<>())
            .chatRoomTagList(new ArrayList<>())
            .chatRoomLikeList(new ArrayList<>())
            .build();

        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);

        List<Tag> tagList = createTagList(tagContentList);

        createChatRoomTagList(savedChatRoom, tagList);

        UserChatRoom userChatRoom = UserChatRoom.builder()
            .user(user)
            .chatRoom(chatRoom)
            .userType(UserType.OWNER)
            .build();

        userChatRoomRepository.save(userChatRoom);
        return savedChatRoom;
    }

    @Transactional
    void createChatRoomTagList(ChatRoom chatRoom, List<Tag> tagList) {

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

    @Transactional
    List<Tag> createTagList(List<String> tagContentList) {

        // TODO tagList ?????? ????????? ????????? ????????? ????????? ??????????????? ??????
        List<Tag> tagList = new LinkedList<>();
        for (String tagContent : tagContentList) {
            Tag newTag = null;
            Optional<Tag> tag = tagRepository.findByContent(tagContent);
            if (tag.isEmpty()) {
                newTag = tagRepository.saveAndFlush(Tag.builder().content(tagContent).build());
                tagList.add(newTag);
                continue;
            }
            tagList.add(tag.get());
        }

        return tagList;
    }

    @Transactional
    public void createChatRoomInvitation(User user, Long guestUserId,
        String firstMessage) {

        User guestUser = userService.findById(guestUserId);

        ChatRoomInvitation chatRoomInvitation = ChatRoomInvitation.builder()
            .hostUser(user)
            .guestUser(guestUser)
            .firstMessage(firstMessage)
            .state(InvitationState.WAITING)
            .build();

        notificationService.send(guestUser, NotificationType.valueOf("INVITE"), user.getNickname(),
            firstMessage, "urls...");

        chatRoomInvitationRepository.save(chatRoomInvitation);
    }


    @Transactional(readOnly = true)
    public ChatRoom getChatRoomByTitle(String title) {
        return chatRoomRepository
            .findByTitle(title)
            .orElseThrow(() -> new IllegalArgumentException("?????? ????????? ????????? ???????????? ???????????? ????????????."));
    }

    @Transactional
    public void save(ChatRoom chatRoom) {
        chatRoomRepository.save(chatRoom);
    }

    @Transactional
    public void handleLikeButton(LikeButtonRequestDto req, User user) {
        Long chatRoomId = req.getChatRoomId();
        boolean likeState = req.getLikeState();

        ChatRoom chatRoom = getChatRoomById(chatRoomId);
        if (likeState) {
            ChatRoomLike chatRoomLike = chatRoomLikeRepository.findByUserIdAndChatRoomId(
                user.getId(), chatRoomId);
            chatRoomLikeRepository.delete(chatRoomLike);
        } else {
            ChatRoomLike chatRoomLike = ChatRoomLike
                .builder()
                .user(user)
                .chatRoom(chatRoom)
                .build();
            chatRoomLikeRepository.save(chatRoomLike);
        }
    }

    @Transactional(readOnly = true)
    public List<MessageResponseDto> getMessages(Long chatRoomId) {
        ChatRoom chatRoom = getChatRoomById(chatRoomId);
        List<Message> messageList = chatRoom.getMessageList();
        return messageList.stream().map(message -> MessageResponseDto.of(message)).collect(
            Collectors.toList());
    }
}
