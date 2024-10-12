package com.studioyunseul.noticeduri.entity;

import com.studioyunseul.noticeduri.repository.MemberRepository;
import com.studioyunseul.noticeduri.repository.NoticeRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class MemberTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    NoticeRepository noticeRepository;

    @Test
    @Commit
    public void createMember() {
        University university1 = new University("강원대학교", "https://wwwk.kangwon.ac.kr/www/index.do");
        University university2 = new University("강원대학교 (삼척)", "https://wwwk.kangwon.ac.kr/www/index.do");
        University university3 = new University("강원대학교 (도계)", "https://wwwk.kangwon.ac.kr/www/index.do");
        em.persist(university1);
        em.persist(university2);
        em.persist(university3);

        Major major1 = new Major("컴퓨터공학과", university1);
        Major major2 = new Major("전자공학과", university1);
        Major major3 = new Major("중어중문학과", university2);
        em.persist(major1);
        em.persist(major2);
        em.persist(major3);

        Member member1 = new Member("hyojae", major1);
        Member member2 = new Member("dahui", major3);
        memberRepository.save(member1);
        memberRepository.save(member2);

        assertThat(memberRepository.findById(member1.getId()).get()).isEqualTo(member1);
        assertThat(memberRepository.findById(member2.getId()).get()).isEqualTo(member2);
        assertThat(memberRepository.findById(member2.getId()).get().getName()).isEqualTo("dahui");
        assertThat(memberRepository.findById(member2.getId()).get().getMajor()).isEqualTo(major3);
        assertThat(memberRepository.findById(member2.getId()).get().getUniversity()).isEqualTo(university2);
        assertThat(memberRepository.findById(member1.getId()).get().getUniversity().getName()).isEqualTo("강원대학교");

        Notice notice = new Notice(major1, "2024년도 10월 교내 학생지원 프로그램 안내", "https://cse.kangwon.ac.kr/cse/community/undergraduate-notice.do?mode=view&articleNo=457851&article.offset=0&articleLimit=10#!/list", LocalDateTime.now());
        noticeRepository.save(notice);

        em.flush();
        em.clear();
        log.info("============ 조회 ==========");
        List<Notice> list = noticeRepository.findByMajor(major1);
        assertThat(list.get(0).getTitle()).isEqualTo(notice.getTitle());

    }
}