package com.mugane.MakMuGaNeTalk.repository;

import com.mugane.MakMuGaNeTalk.entity.ChatRoom;
import com.mugane.MakMuGaNeTalk.entity.QChatRoom;
import com.mugane.MakMuGaNeTalk.entity.QChatRoomTag;
import com.mugane.MakMuGaNeTalk.entity.QTag;
import com.querydsl.core.types.Predicate;
import java.util.List;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

@Slf4j
public class ChatRoomSupportImpl extends QuerydslRepositorySupport implements ChatRoomSupport {

    private final QChatRoom qChatRoom = QChatRoom.chatRoom;
    private final QChatRoomTag qChatRoomTag = QChatRoomTag.chatRoomTag;
    private final QTag qTag = QTag.tag;

    public ChatRoomSupportImpl() {
        super(ChatRoom.class);
    }

    @Override
    public Page<ChatRoom> findAllByKeywordAndTagsAndPaging(
        List<String> tagList,
        String keyword,
        Pageable pageable
    ) {
        final Predicate[] predicates = new Predicate[]{
            predicateOptional(qChatRoomTag.tag.content::in, tagList),
            keyword != null ? predicateOptional(qChatRoom.title::like, '%' + keyword + '%') : null
        };

        List<ChatRoom> chatRoomList = from(qChatRoom)
            .leftJoin(qChatRoomTag).on(qChatRoom.id.eq(qChatRoomTag.chatRoom.id)).fetchJoin()
            .where(predicates)
            .orderBy(qChatRoom.id.desc()) // 챗룸 생성기준 최신순 정렬
//            .limit(pageSize)
            .limit(pageable.getPageSize())
//            .offset(pageNumber)
            .offset(pageable.getPageNumber())
            .distinct()
            .fetch();
        return new PageImpl<>(chatRoomList, pageable, chatRoomList.size());
    }

    private <T> Predicate predicateOptional(final Function<T, Predicate> whereFunc, final T value) {
        return value != null ? whereFunc.apply(value) : null;
    }
}
