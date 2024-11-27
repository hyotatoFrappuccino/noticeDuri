package com.studioyunseul.noticeduri.controller;

import com.studioyunseul.noticeduri.controller.form.LoginForm;
import com.studioyunseul.noticeduri.controller.form.MemberForm;
import com.studioyunseul.noticeduri.controller.form.MemberUpdateForm;
import com.studioyunseul.noticeduri.entity.Major;
import com.studioyunseul.noticeduri.entity.Member;
import com.studioyunseul.noticeduri.entity.University;
import com.studioyunseul.noticeduri.entity.dto.MajorDto;
import com.studioyunseul.noticeduri.entity.dto.MemberDto;
import com.studioyunseul.noticeduri.service.MajorService;
import com.studioyunseul.noticeduri.service.MemberService;
import com.studioyunseul.noticeduri.service.UniversityService;
import com.studioyunseul.noticeduri.web.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "members")
public class MemberController {

    private final MemberService memberService;
    private final MajorService majorService;
    private final UniversityService universityService;

    @Value("${kakao.client_id}")
    private String client_id;

    @Value("${kakao.redirect_uri}")
    private String redirect_uri;

    // 로그인 - Get
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("kakaoLoginLocation", "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + client_id + "&redirect_uri=" + redirect_uri);
        model.addAttribute("form", new LoginForm());
        return "members/login";
    }

  // 로그인 - Post
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("form") LoginForm form, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return "members/login";
        }

        Member loginMember = memberService.login(form.getLoginId(), form.getPassword());

        if (loginMember == null) {
            result.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "members/login";
        }


        //세션이 있으면 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        // 세션 제거
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    // 회원가입 - Get
    @GetMapping("/new")
    public String createMember(Model model) {
        List<University> universities = universityService.findAllByOrderByNameAsc();

        model.addAttribute("universities", universities);
        model.addAttribute("form", new MemberForm());

        return "members/createMemberForm";
    }

    // 회원가입 - Post
    @PostMapping(value = {"/new", "/callback"})
    public String createMember(@Valid @ModelAttribute("form") MemberForm form, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        if (form.getPassword() == null) {
            form.setPassword(UUID.randomUUID().toString());
        }

        Long memberId = memberService.join(form);

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, memberService.findById(memberId));

        return "redirect:/";
    }

    @GetMapping("/myPage")
    public String myPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member, Model model) {
        if (member == null) {
            return "redirect:/members/login";
        }

        MemberDto loginMember = memberService.findDtoById(member.getId());

        MemberUpdateForm form = new MemberUpdateForm();
        form.setId(loginMember.getId());
        form.setName(loginMember.getName());
        form.setUniversityId(loginMember.getUniversity());
        form.setMajorId(loginMember.getMajor());

        model.addAttribute("form", form);

        List<University> universities = universityService.findAllByOrderByNameAsc();
        model.addAttribute("universities", universities);

        if (loginMember.getUniversity() != null) {
            model.addAttribute("majors", majorService.findAllByUniversityId(loginMember.getUniversity(), true));
        }

        return "members/myPage";
    }

    @PostMapping("/myPage")
    public String myPage(@Valid @ModelAttribute("form") MemberUpdateForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "members/myPage";
        }

        memberService.update(form);

        return "redirect:/";
    }

    @GetMapping("/majors/{universityId}")
    @ResponseBody
    public List<MajorDto> getMajorsByUniversity(@PathVariable Long universityId) {
        List<Major> majors = majorService.findAllByUniversityId(universityId, true);
        return majors.stream().map(major -> new MajorDto(major.getId(), major.getName())).collect(Collectors.toList());
    }
}
