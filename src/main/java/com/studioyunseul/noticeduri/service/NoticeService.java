package com.studioyunseul.noticeduri.service;

import com.studioyunseul.noticeduri.entity.Notice;
import com.studioyunseul.noticeduri.entity.dto.NoticeDto;
import com.studioyunseul.noticeduri.repository.NoticeRepository;
import com.studioyunseul.noticeduri.repository.NoticeSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public Slice<NoticeDto> getNoticesByMajor(Pageable pageable, NoticeSearchCondition condition) {
        return noticeRepository.findByConditionSlice(pageable, condition);
    }

    public Page<Notice> getAllNotices(Pageable pageable, NoticeSearchCondition condition) {
        return noticeRepository.findByConditionPage(pageable, condition);
    }

}
