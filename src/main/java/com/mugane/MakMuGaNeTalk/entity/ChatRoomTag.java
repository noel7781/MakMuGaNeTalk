package com.mugane.MakMuGaNeTalk.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class ChatRoomTag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHAT_ROOM_TAG_ID")
    private Long id;

    private Integer turn;

    @ManyToOne
    @JoinColumn(name = "TAG_ID")
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "CHAT_ROOM_ID")
    private ChatRoom chatRoom;
}
