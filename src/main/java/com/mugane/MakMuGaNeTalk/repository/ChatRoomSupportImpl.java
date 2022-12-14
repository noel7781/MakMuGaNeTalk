package com.mugane.MakMuGaNeTalk.repository;

import com.mugane.MakMuGaNeTalk.entity.ChatRoom;
import com.mugane.MakMuGaNeTalk.entity.QChatRoom;
import com.mugane.MakMuGaNeTalk.entity.QChatRoomLike;
import com.mugane.MakMuGaNeTalk.entity.QChatRoomTag;
import com.mugane.MakMuGaNeTalk.entity.QUserChatRoom;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import java.util.Objects;
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
    private final QUserChatRoom qUserChatRoom = QUserChatRoom.userChatRoom;
    private final QChatRoomLike qChatRoomLike = QChatRoomLike.chatRoomLike;

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

        JPQLQuery<ChatRoom> query = from(qChatRoom)
            .leftJoin(qChatRoom.chatRoomTagList, qChatRoomTag)
            .leftJoin(qChatRoom.chatRoomLikeList, qChatRoomLike)
            .where(predicates)
            .orderBy(qChatRoom.id.desc())
            .distinct();

        long totalCount = query.fetchCount();
        List<ChatRoom> chatRoomList = Objects.requireNonNull(getQuerydsl())
            .applyPagination(pageable, query).fetch();
        return new PageImpl<>(chatRoomList, pageable, totalCount);
    }

    @Override
    public Page<ChatRoom> findAllByKeywordAndTagsAndPaging(
        Long userId,
        List<String> tagList,
        String keyword,
        Pageable pageable
    ) {
        final Predicate[] predicates = new Predicate[]{
            predicateOptional(qUserChatRoom.user.id::eq, userId),
            predicateOptional(qChatRoomTag.tag.content::in, tagList),
            keyword != null ? predicateOptional(qChatRoom.title::like, '%' + keyword + '%') : null
        };

        JPQLQuery<ChatRoom> query = from(qChatRoom)
            .leftJoin(qChatRoom.chatRoomTagList, qChatRoomTag)
            .leftJoin(qChatRoom.userChatRoomList, qUserChatRoom)
            .leftJoin(qChatRoom.chatRoomLikeList, qChatRoomLike)
            .where(predicates)
            .orderBy(qChatRoom.id.desc())
            .distinct();
        long totalCount = query.fetchCount();
        List<ChatRoom> chatRoomList = Objects.requireNonNull(getQuerydsl())
            .applyPagination(pageable, query).fetch();
        return new PageImpl<>(chatRoomList, pageable, totalCount);
    }

    private <T> Predicate predicateOptional(final Function<T, Predicate> whereFunc, final T value) {
        return value != null ? whereFunc.apply(value) : null;
    }
}
