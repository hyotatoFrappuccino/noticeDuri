package com.studioyunseul.noticeduri.entity.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoticeDto {
    private final String title;
    private final LocalDateTime noticeDate;
    private final String url;

    @QueryProjection
    public NoticeDto(String title, LocalDateTime noticeDate, String url) {
        this.title = title;
        this.noticeDate = noticeDate;
        this.url = url;
    }
}
