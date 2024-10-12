package com.studioyunseul.noticeduri.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice {
    @Id
    @GeneratedValue
    @Column(name = "notice_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_id")
    private Major major;

    private String title;
    private String url;
    private LocalDateTime uploadedDate;

    public Notice(Major major, String title, String url, LocalDateTime uploadedDate) {
        this.major = major;
        this.title = title;
        this.url = url;
        this.uploadedDate = uploadedDate;
    }
}
