package com.mugane.MakMuGaNeTalk.entity;

import javax.persistence.*;

@Entity
public class ChatRoomTag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHAT_ROOM_TAG_ID")
    private Long id;

    private Integer turn;

    private String content; // TODO tag를 따로 엔티티로 분리할지 논의하기 -> tag의 Id를 가리키는 방식

    @ManyToOne
    @JoinColumn(name = "CHAT_ROOM_ID")
    private ChatRoom chatRoom;
}
