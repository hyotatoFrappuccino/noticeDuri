package com.studioyunseul.noticeduri.repository;

import com.studioyunseul.noticeduri.entity.dto.NoticeDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface NoticeRepositoryCustom {
    Slice<NoticeDto> findByCondition(Pageable pageable, NoticeSearchCondition condition);
}