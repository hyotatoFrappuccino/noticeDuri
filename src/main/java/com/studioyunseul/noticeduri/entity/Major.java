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
public class Major {
    @Id
    @GeneratedValue
    @Column(name = "major_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id")
    private University university;

    @OneToMany(mappedBy = "major")
    private List<Member> members = new ArrayList<>();

    public Major(String name, University university) {
        this.name = name;
        this.university = university;
    }
}
