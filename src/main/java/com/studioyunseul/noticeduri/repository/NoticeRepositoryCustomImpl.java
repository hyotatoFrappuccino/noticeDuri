package com.studioyunseul.noticeduri.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.studioyunseul.noticeduri.entity.Notice;
import com.studioyunseul.noticeduri.entity.dto.NoticeDto;
import com.studioyunseul.noticeduri.entity.dto.QNoticeDto;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.studioyunseul.noticeduri.entity.QMember.member;
import static com.studioyunseul.noticeduri.entity.QNotice.*;
import static org.springframework.util.StringUtils.*;

@Transactional(readOnly = true)
public class NoticeRepositoryCustomImpl implements NoticeRepositoryCustom {

    private final JPAQueryFactory query;

    public NoticeRepositoryCustomImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Slice<NoticeDto> findByConditionSlice(Pageable pageable, NoticeSearchCondition condition) {
        List<NoticeDto> content = query
                .select(new QNoticeDto(notice.title, notice.uploadedDate, notice.url))
                .from(notice)
                .where(majorEq(condition.getMajorId()),
                        titleContains(condition.getTitle()),
                        dateBetween(notice.uploadedDate, condition.getStartDate(), condition.getEndDate())
                )
                .orderBy(notice.uploadedDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = content.size() > pageable.getPageSize();

        if (hasNext) {
            content.remove(content.size() - 1); // 추가로 가져온 1개는 제거
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public Page<Notice> findByConditionPage(Pageable pageable, NoticeSearchCondition condition) {
        List<Notice> content = query
                .selectFrom(notice)
                .where(universityEq(condition.getUniversityId()),
                        majorEq(condition.getMajorId()),
                        titleContains(condition.getTitle()),
                        dateBetween(notice.uploadedDate, condition.getStartDate(), condition.getEndDate())
                )
                .orderBy(notice.uploadedDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = query
                .select(notice.count())
                .from(notice)
                .where(universityEq(condition.getUniversityId()),
                        majorEq(condition.getMajorId()),
                        titleContains(condition.getTitle()),
                        dateBetween(notice.uploadedDate, condition.getStartDate(), condition.getEndDate())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression universityEq(Long universityId) {
        return universityId != null ? notice.university.id.eq(universityId) : null;
    }

    private BooleanExpression majorEq(Long majorId) {
        return majorId != null ? notice.major.id.eq(majorId) : null;
    }

    private BooleanExpression titleContains(String title) {
        return hasText(title) ? notice.title.contains(title) : null;
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
