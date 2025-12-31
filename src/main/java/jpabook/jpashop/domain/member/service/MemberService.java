package jpabook.jpashop.domain.member.service;

import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.domain.member.repository.MemberRepository;
import jpabook.jpashop.global.error.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static jpabook.jpashop.global.error.MemberErrorCode.DUPLICATE_MEMBER;
import static jpabook.jpashop.global.error.MemberErrorCode.NOT_FOUND_MEMBER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();

    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new ApplicationException(DUPLICATE_MEMBER);
                });
    }

    public List<Member>findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ApplicationException(NOT_FOUND_MEMBER));
    }

}
