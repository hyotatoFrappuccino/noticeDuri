package com.studioyunseul.noticeduri.repository;

import com.studioyunseul.noticeduri.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MajorRepository extends JpaRepository<Major, Long> {
    List<Major> findByUniversityId(Long universityId);
}
