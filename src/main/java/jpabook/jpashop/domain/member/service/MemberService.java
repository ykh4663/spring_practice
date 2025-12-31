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
import java.util.Optional;


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
    public void update(Long id, UpdateMemberRequest dto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(NOT_FOUND_MEMBER));

        // 1. 이름 변경
        // "값이 있고(Nullable) && 공백이 아니면(Filter) -> 실행(ifPresent)"
        Optional.ofNullable(dto.name())
                .filter(StringUtils::hasText)
                .ifPresent(member::changeName);

        // 2. 주소 변경 (불변 객체 교체 로직)
        Address current = member.getAddress();

        // "새 값이 유효하면 그거 쓰고, 아니면 기존 거 써라(orElse)"
        String newCity = Optional.ofNullable(dto.city())
                .filter(StringUtils::hasText)
                .orElse(current.getCity());

        String newStreet = Optional.ofNullable(dto.street())
                .filter(StringUtils::hasText)
                .orElse(current.getStreet());

        String newZipcode = Optional.ofNullable(dto.zipcode())
                .filter(StringUtils::hasText)
                .orElse(current.getZipcode());

        // 3. 변경 감지 (새 주소 객체로 갈아끼우기)
        member.changeAddress(Address.builder()
                .city(newCity)
                .street(newStreet)
                .zipcode(newZipcode)
                .build());
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
