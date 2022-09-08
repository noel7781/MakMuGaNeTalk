package com.mugane.MakMuGaNeTalk.entity;

import com.mugane.MakMuGaNeTalk.enums.ChatRoomType;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
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

    private Long createdBy;
    private Long updatedBy;

}