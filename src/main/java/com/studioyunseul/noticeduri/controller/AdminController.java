package com.studioyunseul.noticeduri.controller;

import com.studioyunseul.noticeduri.controller.form.MemberUpdateForm;
import com.studioyunseul.noticeduri.entity.Member;
import com.studioyunseul.noticeduri.entity.University;
import com.studioyunseul.noticeduri.repository.MemberSearchCondition;
import com.studioyunseul.noticeduri.service.MajorService;
import com.studioyunseul.noticeduri.service.MemberService;
import com.studioyunseul.noticeduri.service.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;
    private final MajorService majorService;
    private final UniversityService universityService;

    @GetMapping("/home")
    public String home() {
        return "admin/home";
    }

    @GetMapping("/members")
    public String members(MemberSearchCondition condition, Pageable pageable, Model model) {
        model.addAttribute("members", memberService.findAll(pageable, condition));
        model.addAttribute("condition", condition);
        if (condition.getUniversityId() != null) {
            model.addAttribute("majors", majorService.findAllByUniversityId(condition.getUniversityId(), true));
        }
        model.addAttribute("universities", universityService.findAll());
        model.addAttribute("pageable", pageable);
        return "admin/members";
    }

    @GetMapping("/members/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Member member = memberService.findById(id);
        MemberUpdateForm form = new MemberUpdateForm();
        form.setId(member.getId());
        form.setName(member.getName());
        form.setUniversityId(member.getUniversity().getId());
        form.setMajorId(member.getMajor().getId());

        model.addAttribute("form", form);
        List<University> universities = universityService.findAllByOrderByNameAsc();
        model.addAttribute("universities", universities);

        if (member.getUniversity() != null) {
            model.addAttribute("majors", majorService.findAllByUniversityId(member.getUniversity().getId(), true));
        }
        return "admin/members/edit";
    }

    @PostMapping("/members/edit")
    public String editMember(MemberUpdateForm form) {
        memberService.update(form);
        return "redirect:/admin/members";
    }

    @PostMapping("/members/delete/{id}")
    public String delete(@PathVariable Long id) {
        memberService.deleteById(id);
        return "redirect:/admin/members";
    }

}
