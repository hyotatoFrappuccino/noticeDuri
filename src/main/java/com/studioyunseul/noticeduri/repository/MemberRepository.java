package com.studioyunseul.noticeduri.repository;

import com.studioyunseul.noticeduri.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
