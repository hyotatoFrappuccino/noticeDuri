package com.studioyunseul.noticeduri.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoticeDto {
    private final String title;
    private final LocalDateTime noticeDate;
    private final String url;
}
