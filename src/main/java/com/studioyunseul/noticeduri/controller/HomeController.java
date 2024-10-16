package com.studioyunseul.noticeduri.controller;

import com.studioyunseul.noticeduri.entity.Member;
import com.studioyunseul.noticeduri.repository.NoticeRepository;
import com.studioyunseul.noticeduri.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;
    private final NoticeRepository noticeRepository;

    // 홈 화면
    @GetMapping("/")
    public String home(@CookieValue(name = "memberId", required = false) Long memberId, Model model, HttpServletResponse response) {
        if (memberId == null) {
            return "home";
        }

        // 로그인
        Member loginMember = memberService.findById(memberId);

        // 쿠키의 값이 변조된 경우
        if (loginMember == null) {
            expireCookie(response, "memberId");
            return "redirect:/";
        }
        model.addAttribute("member", loginMember);
        model.addAttribute("notices", noticeRepository.findByMajor(loginMember.getMajor()));

        return "loginHome";
    }

    private static void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}

/* todo
 * 홈 화면 - 로그인 후
 * - 로그아웃 기능 추가
 * 공지관리?
 */