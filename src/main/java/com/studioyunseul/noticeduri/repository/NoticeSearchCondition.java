package com.studioyunseul.noticeduri.repository;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NoticeSearchCondition {
    private Long universityId;
    private Long majorId;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
}
