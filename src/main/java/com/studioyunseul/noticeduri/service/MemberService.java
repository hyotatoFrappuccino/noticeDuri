package com.studioyunseul.noticeduri.service;

import com.studioyunseul.noticeduri.controller.MemberForm;
import com.studioyunseul.noticeduri.entity.Major;
import com.studioyunseul.noticeduri.entity.Member;
import com.studioyunseul.noticeduri.repository.MajorRepository;
import com.studioyunseul.noticeduri.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MajorRepository majorRepository;

    @Transactional
    public Long join(MemberForm memberForm) {
        String name = memberForm.getName();
        Major major = majorRepository.findById(memberForm.getMajorId()).get();
        return memberRepository.save(new Member(name, major)).getId();
    }
}
