package com.studioyunseul.noticeduri.controller;

import com.studioyunseul.noticeduri.entity.Member;
import com.studioyunseul.noticeduri.entity.dto.NoticeDto;
import com.studioyunseul.noticeduri.repository.NoticeRepository;
import com.studioyunseul.noticeduri.repository.NoticeSearchCondition;
import com.studioyunseul.noticeduri.service.MemberService;
import com.studioyunseul.noticeduri.service.NoticeService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.studioyunseul.noticeduri.utils.CookieUtil.expireCookie;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;
    private final NoticeService noticeService;

    // 홈 화면
    @GetMapping("/")
    public String home(@CookieValue(name = "memberId", required = false) Long memberId, Model model, HttpServletResponse response, Pageable pageable) {
        if (memberId == null) {
            return "redirect:/members/login";
        }

        // 로그인
        Member loginMember = memberService.findById(memberId);

        // 쿠키의 값이 변조된 경우
        if (loginMember == null) {
            expireCookie(response, "memberId");
            return "redirect:/members/login";
        }
        model.addAttribute("member", loginMember);

        // 멤버 학과 공지 반환
        NoticeSearchCondition condition = new NoticeSearchCondition();
        condition.setMajorId(loginMember.getMajor().getId());


        model.addAttribute("notices", noticeService.getNoticesByMajor(pageable, condition));
        // 타 게시판 목록 반환
        model.addAttribute("boards", memberService.findAllDistinctMajorNot(loginMember.getUniversity().getId()));

        return "loginHome";
    }

    @GetMapping("/notices")
    @ResponseBody
    public Slice<NoticeDto> loadMoreNotices(@RequestParam int page, @RequestParam Long majorId) {
        Pageable pageable = PageRequest.of(page, 20); // 한 페이지에 20개 공지사항
        NoticeSearchCondition condition = new NoticeSearchCondition();
        condition.setMajorId(majorId);

        return noticeService.getNoticesByMajor(pageable, condition);
    }

}

/* todo
 * 공지관리?
 */