package com.mugane.MakMuGaNeTalk.repository;

import com.mugane.MakMuGaNeTalk.entity.ChatRoom;
import com.mugane.MakMuGaNeTalk.entity.QChatRoom;
import com.mugane.MakMuGaNeTalk.entity.QChatRoomTag;
import com.querydsl.core.types.Predicate;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.function.Function;

public class ChatRoomSupportImpl extends QuerydslRepositorySupport implements ChatRoomSupport {

    private final QChatRoom qChatRoom = QChatRoom.chatRoom;
    private final QChatRoomTag qChatRoomTag = QChatRoomTag.chatRoomTag;

    public ChatRoomSupportImpl() {
        super(ChatRoom.class);
    }

    @Override
    public List<ChatRoom> findAllByKeywordAndTagsAndPaging(
            List<String> tagList,
            String keyword,
            Integer pageSize,
            Integer pageNumber
    ) {
        final Predicate[] predicates = new Predicate[]{
                predicateOptional(qChatRoomTag.tag.content::in, tagList),
                keyword != null ? predicateOptional(qChatRoom.title::like, '%' + keyword + '%') : null
        };

        return from(qChatRoom)
                .leftJoin(qChatRoom.chatRoomTagList, qChatRoomTag).fetchJoin()
                .orderBy(qChatRoom.id.desc()) // 챗룸 생성기준 최신순 정렬
                .where(predicates)
                .limit(pageSize)
                .offset(pageNumber)
                .fetch();
    }

    private <T> Predicate predicateOptional(final Function<T, Predicate> whereFunc, final T value) {
        return value != null ? whereFunc.apply(value) : null;
    }
}
