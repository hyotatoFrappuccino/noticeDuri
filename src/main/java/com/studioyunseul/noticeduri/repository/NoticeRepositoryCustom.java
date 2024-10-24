package com.studioyunseul.noticeduri.repository;

import com.studioyunseul.noticeduri.entity.Notice;
import com.studioyunseul.noticeduri.entity.dto.NoticeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface NoticeRepositoryCustom {
    Slice<NoticeDto> findByConditionSlice(Pageable pageable, NoticeSearchCondition condition);
    Page<Notice> findByConditionPage(Pageable pageable, NoticeSearchCondition condition);
}