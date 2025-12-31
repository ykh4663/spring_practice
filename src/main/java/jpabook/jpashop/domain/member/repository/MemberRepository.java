package jpabook.jpashop.domain.member.repository;

import jpabook.jpashop.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
