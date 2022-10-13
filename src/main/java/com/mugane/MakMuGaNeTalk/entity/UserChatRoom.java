package com.mugane.MakMuGaNeTalk.entity;

import com.mugane.MakMuGaNeTalk.enums.UserType;

import javax.persistence.*;

@Entity
public class UserChatRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "CHAT_ROOM_ID")
    private ChatRoom chatRoom;
}