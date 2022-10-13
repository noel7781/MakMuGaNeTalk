package com.mugane.MakMuGaNeTalk.entity;

import com.mugane.MakMuGaNeTalk.enums.ChatRoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHAT_ROOM_ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ChatRoomType type;

    private String title;
    @OneToOne
    @JoinColumn(referencedColumnName = "USER_ID", name = "OWNER_USER_ID")
    private User ownerUserId;
    private String password;


    @OneToMany(mappedBy = "chatRoom")
    private List<UserChatRoom> userChatRoomList;

    @OneToMany
    @JoinColumn(name = "MESSAGE_ID")
    private List<Message> messageList;

    private Long createdBy;
    private Long updatedBy;

    @OneToMany
    @JoinColumn(name="CHAT_ROOM_ID")
    private List<ChatRoomTag> chatRoomTagList;
}