package jpabook.jpashop.domain.member.service;

import jpabook.jpashop.domain.common.Address;
import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.domain.member.dto.UpdateMemberRequest;

import jpabook.jpashop.domain.member.repository.MemberRepository;
import jpabook.jpashop.global.error.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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

    @Transactional
    public void update(Long id, UpdateMemberRequest dto){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(NOT_FOUND_MEMBER));

        // 1. 이름: 값이 있을 때만(null, "", " " 제외) 변경
        if (StringUtils.hasText(dto.name())) {
            member.changeName(dto.name());
        }

        Address currentAddress = member.getAddress();

        // 2. 주소: 값이 있을 때만(null, "", " " 제외) 새 값 사용, 아니면 기존 값 유지
        String newCity = StringUtils.hasText(dto.city()) ? dto.city() : currentAddress.getCity();
        String newStreet = StringUtils.hasText(dto.street()) ? dto.street() : currentAddress.getStreet();
        String newZipcode = StringUtils.hasText(dto.zipcode()) ? dto.zipcode() : currentAddress.getZipcode();

        Address newAddress = Address.builder()
                .city(newCity)
                .street(newStreet)
                .zipcode(newZipcode)
                .build();

        member.changeAddress(newAddress);
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
