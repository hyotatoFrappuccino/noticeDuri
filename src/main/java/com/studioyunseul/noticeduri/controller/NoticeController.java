package com.studioyunseul.noticeduri.controller;

import com.studioyunseul.noticeduri.entity.Major;
import com.studioyunseul.noticeduri.entity.Notice;
import com.studioyunseul.noticeduri.repository.MajorRepository;
import com.studioyunseul.noticeduri.repository.MemberRepository;
import com.studioyunseul.noticeduri.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class NoticeController {

    private final MajorRepository majorRepository;
    private final NoticeRepository noticeRepository;

    @GetMapping("/notices")
    public String notices(Model model) {
        List<Major> all = majorRepository.findAll();
        model.addAttribute("majors", all);
        return "notices";
    }

    @PostMapping("/notices")
    public String notices(@ModelAttribute Major major, Model model) {
        List<Major> all = majorRepository.findAll();
        model.addAttribute("majors", all);

        List<Notice> list = noticeRepository.findByMajor(major);
        model.addAttribute("notices", list);
        return "notices";
    }
}
