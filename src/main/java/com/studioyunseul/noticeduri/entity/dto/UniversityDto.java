package com.studioyunseul.noticeduri.entity.dto;

import lombok.Data;

@Data
public class UniversityDto {
    private Long id;
    private String name;

    public UniversityDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
