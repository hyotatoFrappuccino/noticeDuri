package com.studioyunseul.noticeduri.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.studioyunseul.noticeduri.entity.Member;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.studioyunseul.noticeduri.entity.QMember.member;

@Transactional(readOnly = true)
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory query;

    public MemberRepositoryCustomImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Page<Member> findByCondition(Pageable pageable, MemberSearchCondition condition) {
        List<Member> content = query
                .selectFrom(member)
                .where(nameContains(condition.getName()),
                        universityEq(condition.getUniversityId()),
                        majorEq(condition.getMajorId()),
                        dateBetween(member.createdDate, condition.getStartDate(), condition.getEndDate())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = query
                .select(member.count())
                .from(member)
                .where(nameContains(condition.getName()),
                        universityEq(condition.getUniversityId()),
                        majorEq(condition.getMajorId()),
                        dateBetween(member.createdDate, condition.getStartDate(), condition.getEndDate())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression nameContains(String name) {
        return name != null ? member.name.contains(name) : null;
    }

    private BooleanExpression universityEq(Long universityId) {
        return universityId != null ? member.university.id.eq(universityId) : null;
    }

    private BooleanExpression majorEq(Long majorId) {
        return majorId != null ? member.major.id.eq(majorId) : null;
    }

    private BooleanExpression dateBetween(DateTimePath<LocalDateTime> date, LocalDate startDate, LocalDate endDate) {
        if (startDate == null && endDate == null) {
            return null;
        }

        if (endDate == null) {
            endDate = LocalDate.now();
        }

        if (startDate == null) {
            startDate = LocalDate.of(2000, 1, 1);
        }

        return date.between(startDate.atStartOfDay(), endDate.atStartOfDay().plusDays(1));
    }
}
