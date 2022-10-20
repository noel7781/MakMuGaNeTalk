package com.mugane.MakMuGaNeTalk.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Tag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TAG_ID")
    private Long id;

    private String content;

    @ManyToOne()
    @JoinColumn(referencedColumnName = "TAG_ID")
    private ChatRoomTag chatRoomTag;
}
