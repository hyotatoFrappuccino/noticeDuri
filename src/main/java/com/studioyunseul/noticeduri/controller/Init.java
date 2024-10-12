package com.studioyunseul.noticeduri.controller;

import com.studioyunseul.noticeduri.entity.Major;
import com.studioyunseul.noticeduri.entity.Member;
import com.studioyunseul.noticeduri.entity.Notice;
import com.studioyunseul.noticeduri.entity.University;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Profile("local")
@Component
@RequiredArgsConstructor
public class Init {

    private final InitMemberService initMemberService;

    @PostConstruct
    public void init() {
        initMemberService.init();
    }

    @Component
    static class InitMemberService {
        @PersistenceContext
        private EntityManager em;

        @Transactional
        public void init() {
            University university1 = new University("강원대학교", "https://wwwk.kangwon.ac.kr/www/index.do");
            University university2 = new University("강원대학교 (삼척)", "https://wwwk.kangwon.ac.kr/www/index.do");
            University university3 = new University("강원대학교 (도계)", "https://wwwk.kangwon.ac.kr/www/index.do");
            University university4 = new University("전북대학교", "https://wwwk.kangwon.ac.kr/www/index.do");
            University university5 = new University("한림대학교", "https://wwwk.kangwon.ac.kr/www/index.do");
            University university6 = new University("전남대학교", "https://wwwk.kangwon.ac.kr/www/index.do");
            em.persist(university1);
            em.persist(university2);
            em.persist(university3);
            em.persist(university4);
            em.persist(university5);
            em.persist(university6);

            Major major1 = new Major("컴퓨터공학과", university1);
            Major major2 = new Major("전자공학과", university1);
            Major major3 = new Major("중어중문학과", university2);
            em.persist(major1);
            em.persist(major2);
            em.persist(major3);

            Member member1 = new Member("hyojae", major1);
            Member member2 = new Member("dahui", major3);
            em.persist(member1);
            em.persist(member2);

            Notice notice = new Notice(major1, "2024년도 10월 교내 학생지원 프로그램 안내", "https://cse.kangwon.ac.kr/cse/community/undergraduate-notice.do?mode=view&articleNo=457851&article.offset=0&articleLimit=10#!/list", LocalDateTime.now());
            em.persist(notice);

            Notice notice2 = new Notice(major2, "공지사항 테스트요 ㅋㅋ", "https://cse.kangwon.ac.kr/cse/community/undergraduate-notice.do?mode=view&articleNo=457851&article.offset=0&articleLimit=10#!/list", LocalDateTime.now());
            em.persist(notice2);
        }
    }
}
