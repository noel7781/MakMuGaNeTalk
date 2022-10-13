package com.mugane.MakMuGaNeTalk.entity;

import com.mugane.MakMuGaNeTalk.enums.InvitationState;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class ChatRoomInvitation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHAT_ROOM_INVITATION_ID")
    private Long id;

    @OneToOne
    @JoinColumn(referencedColumnName = "USER_ID", name = "HOST_USER_ID")
    private User hostUser;

    @OneToOne
    @JoinColumn(referencedColumnName = "USER_ID", name = "GUEST_USER_ID")
    private User guestUser;
    private InvitationState state;
}