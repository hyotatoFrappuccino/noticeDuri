package com.studioyunseul.noticeduri.controller;

import com.studioyunseul.noticeduri.controller.form.MemberForm;
import com.studioyunseul.noticeduri.entity.Member;
import com.studioyunseul.noticeduri.entity.University;
import com.studioyunseul.noticeduri.entity.dto.KakaoUserInfoResponseDto;
import com.studioyunseul.noticeduri.service.KakaoService;
import com.studioyunseul.noticeduri.service.MemberService;
import com.studioyunseul.noticeduri.service.UniversityService;
import com.studioyunseul.noticeduri.utils.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class KakaoLoginController {

    private final KakaoService kakaoService;
    private final MemberService memberService;
    private final UniversityService universityService;

    @GetMapping("/members/callback")
    public String callback(@RequestParam("code") String code, Model model, HttpServletResponse response) {
        String accessToken = kakaoService.getAccessTokenFromKakao(code);
        KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);

        Long kakao_id = userInfo.getId();
        Member loginMember = memberService.findByKakaoId(kakao_id);

        //todo 코드 중복 (/new post)

        //회원가입
        if (loginMember == null) {
            MemberForm form = new MemberForm();
            form.setName(userInfo.getKakaoAccount().getProfile().getNickName());
            form.setKakaoId(userInfo.getId());

            List<University> universities = universityService.findAllByOrderByNameAsc();

            model.addAttribute("universities", universities);
            model.addAttribute("form", form);
            return "/members/createMemberForm";
        }

        //로그인
        CookieUtil.addMemberCookie(response, loginMember.getId());
        return "redirect:/";
    }
}
