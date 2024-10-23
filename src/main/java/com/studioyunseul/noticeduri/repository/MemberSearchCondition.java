package com.studioyunseul.noticeduri.repository;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberSearchCondition {
    private String name;
    private Long universityId;
    private Long majorId;
    private LocalDate startDate;
    private LocalDate endDate;
}
