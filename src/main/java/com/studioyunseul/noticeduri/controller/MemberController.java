package com.studioyunseul.noticeduri.controller;

import com.studioyunseul.noticeduri.entity.Major;
import com.studioyunseul.noticeduri.entity.University;
import com.studioyunseul.noticeduri.entity.dto.MajorDto;
import com.studioyunseul.noticeduri.entity.dto.UniversityDto;
import com.studioyunseul.noticeduri.repository.MajorRepository;
import com.studioyunseul.noticeduri.repository.MemberRepository;
import com.studioyunseul.noticeduri.repository.UniversityRepository;
import com.studioyunseul.noticeduri.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final UniversityRepository universityRepository;
    private final MajorRepository majorRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    /**
     * 회원가입 - Get
     */
    @GetMapping("/members/new")
    public String createMemberForm(Model model) {
        List<University> universities = universityRepository.findAllByOrderByNameAsc();

        model.addAttribute("universities", universities);
        model.addAttribute("form", new MemberForm());

        return "members/createMemberForm";
    }

    /**
     * 회원가입 - Post
     * @ModelAttribute("form") "form" 안 적으면 오류 남!! 하지만 @ModelAttribute 자체가 생략 가능하므로 생략함.
     */
    @PostMapping("/members/new")
    public String createMember(@Valid MemberForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        memberService.join(form);

        return "redirect:/";
    }

    @GetMapping("/majors/{universityId}")
    @ResponseBody
    public List<MajorDto> getMajorByUniversity(@PathVariable Long universityId) {
        List<Major> majors = majorRepository.findByUniversityId(universityId);
        return majors.stream().map(major -> new MajorDto(major.getId(), major.getName())).collect(Collectors.toList());
    }
}
