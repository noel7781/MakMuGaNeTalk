package com.mugane.MakMuGaNeTalk.model.entity;

import com.mugane.MakMuGaNeTalk.enums.ChatRoomType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import javax.persistence.*;

@Entity
@Getter
public class ChatRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "CHAT_ROOM_ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ChatRoomType type;

    private String title;
    @OneToOne
    @JoinColumn(referencedColumnName = "USER_ID", name = "OWNER_USER_ID")
    private User ownerUserId;
    private String password;


    @OneToMany(mappedBy = "chatRoom")
    private List<UserChatRoom> userChatRoomList;

    @OneToMany
    @JoinColumn(name = "MESSAGE_ID")
    private List<Message> messageList;

    @Builder.Default
    private Integer likeCnt = 0;

    private Long createdBy;
    private Long updatedBy;

    @Transient
    boolean isLiked;

    @Transient
    boolean isLBoss;
}