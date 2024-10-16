package com.studioyunseul.noticeduri.controller;

import com.studioyunseul.noticeduri.controller.form.LoginForm;
import com.studioyunseul.noticeduri.controller.form.MemberForm;
import com.studioyunseul.noticeduri.entity.Major;
import com.studioyunseul.noticeduri.entity.Member;
import com.studioyunseul.noticeduri.entity.University;
import com.studioyunseul.noticeduri.entity.dto.MajorDto;
import com.studioyunseul.noticeduri.repository.MajorRepository;
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

import static com.studioyunseul.noticeduri.utils.CookieUtil.addMemberCookie;
import static com.studioyunseul.noticeduri.utils.CookieUtil.expireCookie;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "members")
public class MemberController {

    private final UniversityRepository universityRepository;
    private final MajorRepository majorRepository;
    private final MemberService memberService;

    // 로그인 - Get
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("form", new LoginForm());
        return "members/login";
    }

    // 로그인 - Post
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

        addMemberCookie(response, loginMember.getId());

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        expireCookie(response, "memberId");
        return "redirect:/";
    }

    // 회원가입 - Get
    @GetMapping("/new")
    public String createMember(Model model) {
        List<University> universities = universityRepository.findAllByOrderByNameAsc();

        model.addAttribute("universities", universities);
        model.addAttribute("form", new MemberForm());

        return "members/createMemberForm";
    }

    // 회원가입 - Post
    @PostMapping("/new")
    public String createMember(@Valid @ModelAttribute("form") MemberForm form, BindingResult result, HttpServletResponse response) {
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Long memberId = memberService.join(form);

        addMemberCookie(response, memberId);

        return "redirect:/";
    }

    @GetMapping("/majors/{universityId}")
    @ResponseBody
    public List<MajorDto> getMajorsByUniversity(@PathVariable Long universityId) {
        List<Major> majors = majorRepository.findByUniversityId(universityId);
        return majors.stream().map(major -> new MajorDto(major.getId(), major.getName())).collect(Collectors.toList());
    }
}
