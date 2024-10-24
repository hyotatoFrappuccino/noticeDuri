package com.studioyunseul.noticeduri.repository;

import com.studioyunseul.noticeduri.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long>, NoticeRepositoryCustom {
}
