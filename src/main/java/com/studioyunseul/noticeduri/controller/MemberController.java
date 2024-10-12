package com.studioyunseul.noticeduri.controller;

import com.studioyunseul.noticeduri.entity.Major;
import com.studioyunseul.noticeduri.entity.University;
import com.studioyunseul.noticeduri.repository.MajorRepository;
import com.studioyunseul.noticeduri.repository.MemberRepository;
import com.studioyunseul.noticeduri.repository.UniversityRepository;
import com.studioyunseul.noticeduri.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final UniversityRepository universityRepository;
    private final MajorRepository majorRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createMemberForm(Model model) {
        List<University> universities = universityRepository.findAll();

        model.addAttribute("universities", universities);
        model.addAttribute("form", new MemberForm());

        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String createMember(MemberForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }
        System.out.println("memberForm.getName() = " + form.getName());
        System.out.println("memberForm.getUniversityId() = " + form.getUniversityId());
        System.out.println("memberForm.getMajorId() = " + form.getMajorId());

        memberService.join(form);

        return "redirect:/";
    }

    @GetMapping("/majors/{universityId}")
    @ResponseBody
    public List<Major> getMajorByUniversity(@PathVariable Long universityId) {
        return majorRepository.findByUniversityId(universityId);
    }
}
