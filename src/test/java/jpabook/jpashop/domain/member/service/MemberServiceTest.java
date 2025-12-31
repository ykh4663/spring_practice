package jpabook.jpashop.domain.member.service;

import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.domain.member.repository.MemberRepository;

import jpabook.jpashop.global.error.ApplicationException;
import jpabook.jpashop.global.error.MemberErrorCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static jpabook.jpashop.global.error.MemberErrorCode.DUPLICATE_MEMBER;
import static jpabook.jpashop.global.error.MemberErrorCode.NOT_FOUND_MEMBER;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional

class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception{
        //given
        Member member = Member.builder().name("철수").build();

        //when
        Long saveId = memberService.join(member);
        Member getMember = memberRepository.findById(saveId).orElseThrow(() -> new ApplicationException(NOT_FOUND_MEMBER));

        //then
        assertThat(saveId).isEqualTo(getMember.getId());


    }

    @Test
    public void 중복_회원_예외() throws Exception{
        //given
        Member member1 = Member.builder().name("철수").build();
        Member member2 = Member.builder().name("철수").build();

        //when
        memberService.join(member1);

        //then
        assertThatThrownBy(() -> memberService.join(member2))
                .isInstanceOf(ApplicationException.class)
                .extracting("errorCode")
                .isEqualTo(DUPLICATE_MEMBER);

    }



}