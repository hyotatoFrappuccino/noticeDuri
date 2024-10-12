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
public class University {
    @Id
    @GeneratedValue
    @Column(name = "university_id")
    private Long id;

    private String name;
    private String url;

    @OneToMany(mappedBy = "university")
    private List<Major> majors = new ArrayList<>();

    public University(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
