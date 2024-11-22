package com.studioyunseul.noticeduri.controller;

import com.studioyunseul.noticeduri.entity.Member;
import com.studioyunseul.noticeduri.entity.dto.NoticeDto;
import com.studioyunseul.noticeduri.repository.NoticeSearchCondition;
import com.studioyunseul.noticeduri.service.MemberService;
import com.studioyunseul.noticeduri.service.NoticeService;
import com.studioyunseul.noticeduri.web.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;
    private final NoticeService noticeService;
    private final SessionManager sessionManager;

    // 홈 화면
    @GetMapping("/")
    public String home(HttpServletRequest request, Model model, Pageable pageable) {
//    public String home (@CookieValue(name = "memberId", required = false) Long memberId, Model model, Pageable pageable){
        Member loginMember = (Member) sessionManager.getSession(request);
        if (loginMember == null) {
            return "redirect:/members/login";
        }

        // 로그인
//        MemberDto loginMember = memberService.findDtoById(memberId);
        model.addAttribute("member", loginMember);

        // 멤버 학과 공지 반환
        NoticeSearchCondition condition = new NoticeSearchCondition();
        condition.setMajorId(loginMember.getMajor().getId());
        model.addAttribute("notices", noticeService.getNoticesByMajor(pageable, condition));

        // 타 게시판 목록 반환
        model.addAttribute("boards", memberService.findAllDistinctMajorNot(loginMember.getMajor().getId(), loginMember.getUniversity().getId()));

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