package com.studioyunseul.noticeduri.service;

import com.studioyunseul.noticeduri.entity.Major;
import com.studioyunseul.noticeduri.repository.MajorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MajorService {

    private final MajorRepository majorRepository;

    public List<Major> findAllByUniversityId(Long universityId, Boolean isDistinctMajor) {
        if (isDistinctMajor) {
            return majorRepository.findByIsDistinctMajorTrueAndUniversityId(universityId);
        } else {
            return majorRepository.findByIsDistinctMajorFalseAndUniversityId(universityId);
        }
    }

    public Major findById(Long majorId) {
        return majorRepository.findById(majorId).orElse(null);
    }
}
