package com.studioyunseul.noticeduri.service;

import com.studioyunseul.noticeduri.controller.form.MemberForm;
import com.studioyunseul.noticeduri.controller.form.MemberUpdateForm;
import com.studioyunseul.noticeduri.entity.Major;
import com.studioyunseul.noticeduri.entity.Member;
import com.studioyunseul.noticeduri.entity.dto.MemberDto;
import com.studioyunseul.noticeduri.exception.MemberNotFound;
import com.studioyunseul.noticeduri.repository.MemberRepository;
import com.studioyunseul.noticeduri.repository.MemberSearchCondition;
import com.studioyunseul.noticeduri.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final UniversityRepository universityRepository;
    private final MajorService majorService;

    @Transactional
    public Long join(MemberForm memberForm) {
        String name = memberForm.getName();
        String password = memberForm.getPassword();
        Major major = majorService.findById(memberForm.getMajorId());

        Member member = new Member(name, major, password);
        return memberRepository.save(member).getId();
    }

    public Member login(String name, String password) {
        return memberRepository.findByNameAndPassword(name, password);
    }

    public MemberDto findByIdDto(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(MemberNotFound::new);
        return convertToDto(member);
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(MemberNotFound::new);
    }

    @Transactional
    public void update(MemberUpdateForm form) {
        Member member = memberRepository.findById(form.getId()).orElseThrow(MemberNotFound::new);
        member.changeName(form.getName());
        if (form.getPassword() != null) {
            member.changePassword(form.getPassword());
        }
        member.changeUniversity(universityRepository.findById(form.getUniversityId()).orElseThrow());
        member.changeMajor(majorService.findById(form.getMajorId()));
    }

    public List<Major> findAllDistinctMajorNot(Long majorId, Long universityId) {
        List<Major> list = new ArrayList<>();
        list.add(majorService.findById(majorId));
        list.addAll(majorService.findAllByUniversityId(universityId, false));
        return list;
    }

    public Page<Member> findAll(Pageable pageable, MemberSearchCondition condition) {
        return memberRepository.findByCondition(pageable, condition);
    }

    private MemberDto convertToDto(Member member) {
        return new MemberDto(member.getId(), member.getName(), member.getUniversity().getId(), member.getMajor().getId());
    }

    @Transactional
    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }
}
