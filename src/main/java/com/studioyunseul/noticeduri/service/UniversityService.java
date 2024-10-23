package com.studioyunseul.noticeduri.service;

import com.studioyunseul.noticeduri.entity.University;
import com.studioyunseul.noticeduri.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UniversityService {

    private final UniversityRepository universityRepository;

    public List<University> findAllByOrderByNameAsc() {
        return universityRepository.findAllByOrderByNameAsc();
    }

    public List<University> findAll() {
        return universityRepository.findAll();
    }

    public University findById(Long universityId) {
        return universityRepository.findById(universityId).orElse(null);
    }
}
