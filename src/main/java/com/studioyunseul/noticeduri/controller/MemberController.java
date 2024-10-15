package com.studioyunseul.noticeduri.controller;

import com.studioyunseul.noticeduri.entity.Major;
import com.studioyunseul.noticeduri.entity.Member;
import com.studioyunseul.noticeduri.entity.University;
import com.studioyunseul.noticeduri.entity.dto.MajorDto;
import com.studioyunseul.noticeduri.repository.MajorRepository;
import com.studioyunseul.noticeduri.repository.MemberRepository;
import com.studioyunseul.noticeduri.repository.UniversityRepository;
import com.studioyunseul.noticeduri.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
@RequestMapping(value = "members")
public class MemberController {

    private final UniversityRepository universityRepository;
    private final MajorRepository majorRepository;
    private final MemberService memberService;

    /**
     * 로그인
     */
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("form", new LoginForm());
        return "members/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("form") LoginForm form, BindingResult result, HttpServletResponse response) {
        if (result.hasErrors()) {
            return "members/login";
        }

        Member loginMember = memberService.login(form.getLoginId(), form.getPassword());

        if (loginMember == null) {
            result.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "members/login";
        }

        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
        response.addCookie(idCookie);

        return "redirect:/";
    }

    /**
     * 회원가입 - Get
     */
    @GetMapping("/new")
    public String createMemberForm(Model model) {
        List<University> universities = universityRepository.findAllByOrderByNameAsc();

        model.addAttribute("universities", universities);
        model.addAttribute("form", new MemberForm());

        return "members/createMemberForm";
    }

    /**
     * 회원가입 - Post
     */
    @PostMapping("/new")
    public String createMember(@Valid @ModelAttribute("form") MemberForm form, BindingResult result) {
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
