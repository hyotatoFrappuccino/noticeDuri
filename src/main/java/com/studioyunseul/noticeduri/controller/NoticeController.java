package com.studioyunseul.noticeduri.controller;

import com.studioyunseul.noticeduri.entity.Major;
import com.studioyunseul.noticeduri.entity.Notice;
import com.studioyunseul.noticeduri.entity.University;
import com.studioyunseul.noticeduri.entity.dto.UniversityDto;
import com.studioyunseul.noticeduri.repository.MajorRepository;
import com.studioyunseul.noticeduri.repository.MemberRepository;
import com.studioyunseul.noticeduri.repository.NoticeRepository;
import com.studioyunseul.noticeduri.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class NoticeController {

    private final MajorRepository majorRepository;
    private final NoticeRepository noticeRepository;
    private final UniversityRepository universityRepository;

    /**
     * 공지사항 리스트 출력 - Get
     */
    @GetMapping("/notices")
    public String notices(Model model) {
        List<Major> all = majorRepository.findAll();
        List<University> universities = universityRepository.findAllByOrderByNameAsc();
        model.addAttribute("majors", all);
        model.addAttribute("universities", universities);
        return "notices";
    }

    /**
     * 공지사항 리스트 출력 - Post
     */
    @PostMapping("/notices")
    public String notices(@ModelAttribute Major major, Model model) {
        List<Major> all = majorRepository.findAll();
        model.addAttribute("majors", all);

        List<Notice> list = noticeRepository.findByMajor(major);
        model.addAttribute("notices", list);
        return "notices";
    }
}
