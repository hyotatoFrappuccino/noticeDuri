package com.studioyunseul.noticeduri.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id")
    private University university;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_id")
    private Major major;

    public Member(String name, Major major, String password) {
        this.name = name;
        this.university = major.getUniversity();
        this.major = major;
        this.password = password;
    }

    private void changeMajor(Major major) {
        this.major.getMembers().remove(this);
        this.major = major;
        this.major.getMembers().add(this);

        this.university = major.getUniversity();
    }

    private void changePassword(String password) {
        this.password = password;
    }
}
