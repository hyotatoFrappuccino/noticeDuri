package com.studioyunseul.noticeduri.controller;

import com.studioyunseul.noticeduri.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private MemberService memberService;

    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }
}

/**todo
 * 로그인 요구사항
 *
 * 홈 화면 - 로그인 전
 * - 회원가입 / 로그인
 *
 * 홈 화면 - 로그인 후
 * - 이름, 공지 조회, 로그아웃
 *
 * 보안 요구사항
 * - 로그인 사용자만 공지 조회를 할 수 있음. 로그인되어 있지 않은데 접근 시 로그인 화면으로 이동
 *
 * 회원가입
 * 공지관리
 */