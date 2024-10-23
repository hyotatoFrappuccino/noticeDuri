package com.studioyunseul.noticeduri.controller;

import com.studioyunseul.noticeduri.entity.Major;
import com.studioyunseul.noticeduri.entity.Member;
import com.studioyunseul.noticeduri.entity.Notice;
import com.studioyunseul.noticeduri.entity.University;
import com.studioyunseul.noticeduri.repository.MajorRepository;
import com.studioyunseul.noticeduri.service.MajorService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Profile("local")
@Component
@RequiredArgsConstructor
public class Init {

    private final InitMemberService initMemberService;
    private final MajorService majorService;

    @PostConstruct
    public void init() {
        initMemberService.init();
    }

    @Component
    @RequiredArgsConstructor
    static class InitMemberService {
        private final EntityManager em;
        private final MajorService majorService;

        @Transactional
        public void init() {
            University univ_knu = new University("강원대학교", "https://wwwk.kangwon.ac.kr/www/index.do");
            University university2 = new University("강원대학교 (삼척)", "https://wwwk.kangwon.ac.kr/www/index.do");
            University university3 = new University("강원대학교 (도계)", "https://wwwk.kangwon.ac.kr/www/index.do");
            University university4 = new University("전북대학교", "https://wwwk.kangwon.ac.kr/www/index.do");
            University university5 = new University("한림대학교", "https://wwwk.kangwon.ac.kr/www/index.do");
            University university6 = new University("전남대학교", "https://wwwk.kangwon.ac.kr/www/index.do");
            em.persist(univ_knu);
            em.persist(university2);
            em.persist(university3);
            em.persist(university4);
            em.persist(university5);
            em.persist(university6);

            String[] majors_knu = new String[]{"간호학과", "경영학과", "회계학과", "경제학과", "정보통계학과", "관광경영학과", "국제무역학과", "스마트팜농산업학과", "바이오시스템기계공학", "컴퓨터공학과"};
            for (String major : majors_knu) {
                em.persist(new Major(major, univ_knu, true));
            }
            Major major3 = new Major("중어중문학과", university2, true);
            em.persist(major3);

            Member member1 = new Member("whale", major3, "1234");
            Member member2 = new Member("windows", major3, "1234");
            em.persist(member1);
            em.persist(member2);

            Major major = majorService.findById(1L);
            Notice notice = new Notice(major, "2024년도 10월 교내 학생지원 프로그램 안내", "https://cse.kangwon.ac.kr/cse/community/undergraduate-notice.do?mode=view&articleNo=457851&article.offset=0&articleLimit=10#!/list", LocalDateTime.now());
            em.persist(notice);

            Notice notice2 = new Notice(major, "공지사항 테스트요 ㅋㅋ", "https://cse.kangwon.ac.kr/cse/community/undergraduate-notice.do?mode=view&articleNo=457851&article.offset=0&articleLimit=10#!/list", LocalDateTime.now());
            em.persist(notice2);

            em.persist(new Major("학사공지", university2, false));
            em.persist(new Major("기숙사", university2, false));

            List<Notice> notices = new ArrayList<>();

            notices.add(new Notice(major3, "2024학년도 진로탐색 프로그램 「진로지도」참여 학생 모집 안내","https://cse.kangwon.ac.kr/cse/community/undergraduate-notice.do?mode=view&articleNo=458620&article.offset=0&articleLimit=10", LocalDateTime.of(2024, 1, 1, 0, 0)));
            notices.add(new Notice(major3,"[LINC3.0사업단]한국수력원자력㈜과 함께하는 지역사회 문제해결 수력 아이디어톤 모집 안내",  "https://cse.kangwon.ac.kr/cse/community/undergraduate-notice.do?mode=view&articleNo=458562&article.offset=0&articleLimit=10#!/list", LocalDateTime.of(2024, 1, 2, 0, 0)));
            notices.add(new Notice(major3,"2024학년도 전기 졸업대상자 예비졸업사정 상담 실시 안내", "https://cse.kangwon.ac.kr/cse/community/undergraduate-notice.do?mode=view&articleNo=458419&article.offset=0&articleLimit=10#!/list", LocalDateTime.of(2024, 1, 3, 0, 0)));
            notices.add(new Notice(major3,"2024년도 10월 교내 학생지원 프로그램 안내", "https://cse.kangwon.ac.kr/cse/community/undergraduate-notice.do?mode=view&articleNo=457851&article.offset=0&articleLimit=10#!/list", LocalDateTime.of(2024, 1, 4, 0, 0)));
            notices.add(new Notice(major3,"2024-2학기 전공 수업 활동 보조 TA 모집 안내(추가)", "https://cse.kangwon.ac.kr/cse/community/undergraduate-notice.do?mode=view&articleNo=457700&article.offset=0&articleLimit=10#!/list", LocalDateTime.of(2024, 1, 5, 0, 0)));
            notices.add(new Notice(major3,"제29회 지식재산능력시험 대비 특강 안내", "https://cse.kangwon.ac.kr/cse/community/undergraduate-notice.do?mode=view&articleNo=457552&article.offset=0&articleLimit=10#!/list", LocalDateTime.of(2024, 1, 6, 0, 0)));
            notices.add(new Notice(major3,"LINC 3.0 취업 성공 동아리 모집 안내", "https://cse.kangwon.ac.kr/cse/community/undergraduate-notice.do?mode=view&articleNo=457456&article.offset=0&articleLimit=10#!/list", LocalDateTime.of(2024, 1, 7, 0, 0)));
            notices.add(new Notice(major3,"2024학년도 Study Buddy 프로그램 안내", "https://cse.kangwon.ac.kr/cse/community/undergraduate-notice.do?mode=view&articleNo=457156&article.offset=0&articleLimit=10#!/list", LocalDateTime.of(2024, 1, 8, 0, 0)));
            notices.add(new Notice(major3,"2024-2학기 전공 수업 활동 보조 TA 모집 안내",  "https://cse.kangwon.ac.kr/cse/community/undergraduate-notice.do?mode=view&articleNo=456658&article.offset=0&articleLimit=10#!/list", LocalDateTime.of(2024, 1, 9, 0, 0)));

            LocalDateTime startDate = LocalDateTime.of(2023, 5, 1, 0, 0);
            for (int i = 0; i < 100; i++) {
                String title = "임의 공지사항 제목 " + (i + 1);
                LocalDateTime date = startDate.plusDays(i);
                String url = "https://example.com/notice/" + (i + 1);
                notices.add(new Notice(major3, title, url, date));
            }

            for (Notice notice1 : notices) {
                em.persist(notice1);
            }

            for (int i = 0; i < 1000; i++) {
                Random random = new Random(System.currentTimeMillis());
                Member member = new Member("test " + i, majorService.findById((long) random.nextInt(1, 14)), "Test");
                em.persist(member);
            }
        }
    }
}
