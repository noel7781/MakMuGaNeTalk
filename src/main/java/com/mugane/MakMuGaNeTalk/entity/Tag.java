package com.mugane.MakMuGaNeTalk.entity;

import javax.persistence.*;

@Entity
public class Tag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TAG_ID")
    private Long id;

    private String content;
}
