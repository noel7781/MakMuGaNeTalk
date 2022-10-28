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
    private User ownerUser;

    @Builder.Default
    private String password = "";

    @OneToOne
    @JoinColumn(referencedColumnName = "USER_ID", name = "CREATED_BY")
    private User createdBy;

    @OneToOne
    @JoinColumn(referencedColumnName = "USER_ID", name = "UPDATED_BY")
    private User updatedBy;

    @OneToMany(mappedBy = "chatRoom")
    private List<UserChatRoom> userChatRoomList;

    @OneToMany
    @JoinColumn(name = "MESSAGE_ID")
    private List<Message> messageList;

    @OneToMany
    @JoinColumn(name="CHAT_ROOM_ID")
    private List<ChatRoomTag> chatRoomTagList;
}