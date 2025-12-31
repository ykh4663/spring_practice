package jpabook.jpashop.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jpabook.jpashop.domain.common.Address;
import jpabook.jpashop.domain.member.Member;

@Schema(description = "회원 상세 정보 DTO")
public record MemberDto(

        @Schema(description = "회원 PK", example = "1")
        Long id,

        @Schema(description = "회원 이름", example = "홍길동")
        String name,

        @Schema(description = "변경할 도시", example = "부산")
                String city,

        @Schema(description = "변경할 거리", example = "해운대로 100")
        String street,

        @Schema(description = "변경할 우편번호", example = "48000")
        String zipcode
) {
        public static MemberDto from(Member member) {
                Address address = member.getAddress(); // 1. 주소를 먼저 꺼냄
                return new MemberDto(
                        member.getId(),
                        member.getName(),
                        // 2. 주소가 null이면 null을, 아니면 값을 반환 (NPE 방지)
                        address != null ? address.getCity() : null,
                        address != null ? address.getStreet() : null,
                        address != null ? address.getZipcode() : null
                );
        }
}

