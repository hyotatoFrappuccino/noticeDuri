package com.studioyunseul.noticeduri.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Major extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "major_id")
    private Long id;

    private String name;

    // T = 개별학과(컴공, 국문 ...)
    // F = 학교 사이트 (학사공지, 기숙사 ...)
    private Boolean isDistinctMajor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id")
    private University university;

    @OneToMany(mappedBy = "major")
    private List<Member> members = new ArrayList<>();

    public Major(String name, University university, Boolean isDistinctMajor) {
        this.name = name;
        this.university = university;
        this.isDistinctMajor = isDistinctMajor;
    }
}
