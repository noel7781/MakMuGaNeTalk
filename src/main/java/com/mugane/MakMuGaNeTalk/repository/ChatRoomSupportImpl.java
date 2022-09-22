package com.mugane.MakMuGaNeTalk.repository;

import com.mugane.MakMuGaNeTalk.entity.QChatRoom;
import com.mugane.MakMuGaNeTalk.model.entity.ChatRoom;
import com.querydsl.core.types.Predicate;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.function.Function;

public class ChatRoomSupportImpl extends QuerydslRepositorySupport implements ChatRoomSupport {

    private final QChatRoom qChatRoom = QChatRoom.chatRoom;

    public ChatRoomSupportImpl() {
        super(ChatRoom.class);
    }

    public List<ChatRoom> findAll(
//            TODO Add Long userSeq,
            List<Long> tagList,
            String keyword,
            Integer pageSize,
            Long prevLastPostSeq
    ) {
        final Predicate[] predicates = new Predicate[]{
                predicateOptional(qChatRoom.id::lt, prevLastPostSeq),
//                predicateOptional(qChatRoom.tagList::in, tagList),
                keyword != null ? predicateOptional(qChatRoom.title::like, '%' + keyword + '%') : null
        };

        return from(qChatRoom, )
                .where(predicates)
                .orderBy(qChatRoom.)


    }

    private <T> Predicate predicateOptional(final Function<T, Predicate> whereFunc, final T value) {
        return value != null ? whereFunc.apply(value) : null;
    }
}
