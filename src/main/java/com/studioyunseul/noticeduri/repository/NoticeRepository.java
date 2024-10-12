package com.studioyunseul.noticeduri.repository;

import com.studioyunseul.noticeduri.entity.Major;
import com.studioyunseul.noticeduri.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findByMajor(Major major);
}
