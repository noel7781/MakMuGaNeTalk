package com.mugane.MakMuGaNeTalk.entity;

import javax.persistence.*;

@Entity
public class ChatRoomTag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHAT_ROOM_TAG_ID")
    private Long id;

    private Integer turn;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "CHAT_ROOM_ID")
    private ChatRoom chatRoom;
}
