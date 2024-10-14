package com.studioyunseul.noticeduri.repository;

import com.studioyunseul.noticeduri.entity.University;
import com.studioyunseul.noticeduri.entity.dto.UniversityDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UniversityRepository extends JpaRepository<University, Long> {
    List<University> findAllByOrderByNameAsc();
}
