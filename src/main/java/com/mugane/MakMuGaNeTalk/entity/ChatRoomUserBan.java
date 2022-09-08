package com.mugane.MakMuGaNeTalk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class ChatRoomUserBan extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "CHAT_ROOM_USER_BAN_ID")
    private Long id;
    @OneToOne
    @JoinColumn(referencedColumnName = "USER_ID", name = "BAN_USER_ID")
    private User banUser;
    @OneToOne
    @JoinColumn(referencedColumnName = "USER_ID", name = "BANNED_USER_ID")
    private User bannedUser;
}