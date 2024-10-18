package com.studioyunseul.noticeduri.entity.dto;

import lombok.Data;

@Data
public class MajorDto {
    private Long id;
    private String name;

    public MajorDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
