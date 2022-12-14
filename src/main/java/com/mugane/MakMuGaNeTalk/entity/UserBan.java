package com.mugane.MakMuGaNeTalk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UserBan extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_BAN_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "USER_ID", name = "BAN_USER_ID")
    private User banUser;

    @ManyToOne
    @JoinColumn(referencedColumnName = "USER_ID", name = "BANNED_USER_ID")
    private User bannedUser;
}
