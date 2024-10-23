package com.studioyunseul.noticeduri.repository;

import com.studioyunseul.noticeduri.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface MemberRepositoryCustom {
    Page<Member> findByCondition(Pageable pageable, MemberSearchCondition condition);
}
