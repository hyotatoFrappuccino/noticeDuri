package com.studioyunseul.noticeduri.repository;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoticeSearchCondition {
    private Long majorId;
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
