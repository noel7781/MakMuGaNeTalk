package com.mugane.MakMuGaNeTalk.entity;

import com.mugane.MakMuGaNeTalk.enums.InvitationState;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ChatRoomInvitation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHAT_ROOM_INVITATION_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "USER_ID", name = "HOST_USER_ID")
    private User hostUser;

    @ManyToOne
    @JoinColumn(referencedColumnName = "USER_ID", name = "GUEST_USER_ID")
    private User guestUser;

    private String firstMessage;

    private InvitationState state;
}
