package com.mugane.MakMuGaNeTalk.entity;

import com.mugane.MakMuGaNeTalk.enums.ChatRoomType;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User ownerUser;

    @Builder.Default
    private String password = "";

    @OneToMany(mappedBy = "chatRoom")
    private List<UserChatRoom> userChatRoomList;

    @OneToMany(mappedBy = "chatRoom")
    private List<Message> messageList;

    @OneToMany(mappedBy = "chatRoom")
    private List<ChatRoomTag> chatRoomTagList;

    @OneToMany(mappedBy = "chatRoom")
    private List<ChatRoomLike> chatRoomLikeList;

}
