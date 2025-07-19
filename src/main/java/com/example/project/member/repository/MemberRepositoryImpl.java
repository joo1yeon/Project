package com.example.project.member.repository;

import com.example.project.member.entity.Member;
import com.example.project.member.dto.MemberListResponseDto;
import com.example.project.member.dto.MemberSortType;
import com.example.project.member.entity.QMember;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;


public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<MemberListResponseDto> searchList(MemberSortType sortType, Pageable pageable) {

        QMember member = QMember.member;

        // 정렬 조건
        PathBuilder<Member> path = new PathBuilder<>(Member.class, "member");
        OrderSpecifier<?> orderSpecifier = switch (sortType) {
            case NAME -> member.name.asc();
            case VIEW -> member.profileViewCount.desc();
            case RECENT -> member.registeredDate.desc();
        };

        List<MemberListResponseDto> result = queryFactory
                .select(
                        Projections.constructor(
                                MemberListResponseDto.class,
                                member.id,
                                member.name,
                                member.profileViewCount,
                                member.registeredDate
                        )
                )
                .from(member)
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 총 개수
        long total = queryFactory
                .select(member.count())
                .from(member)
                .fetchOne();

        return new PageImpl<>(result, pageable, total);
    }
}
