package com.studioyunseul.noticeduri.repository;

import com.studioyunseul.noticeduri.entity.University;
import com.studioyunseul.noticeduri.entity.dto.UniversityDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UniversityRepository extends JpaRepository<University, Long> {
    @Query("select new com.studioyunseul.noticeduri.entity.dto.UniversityDto(u.id, u.name) from University u order by u.name asc")
    List<UniversityDto> findAllByOrderByNameAsc();
}
