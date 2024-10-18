package com.studioyunseul.noticeduri.entity.dto;

import lombok.Data;

@Data
public class MemberDto {
    private Long id;
    private String name;
    private Long university;
    private Long major;

    public MemberDto(Long id, String name, Long university, Long major) {
        this.id = id;
        this.name = name;
        this.university = university;
        this.major = major;
    }
}
