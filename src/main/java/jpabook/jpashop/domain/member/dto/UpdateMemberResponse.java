package jpabook.jpashop.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jpabook.jpashop.domain.common.Address;
import jpabook.jpashop.domain.member.Member;

@Schema(description = "회원 정보 수정 응답 DTO")
public record UpdateMemberResponse(

        @Schema(description = "회원 PK", example = "1")
        Long id,

        @Schema(description = "변경된 이름", example = "김철수")
        String name,

        @Schema(description = "변경할 도시", example = "부산")
                String city,

        @Schema(description = "변경할 거리", example = "해운대로 100")
        String street,

        @Schema(description = "변경할 우편번호", example = "48000")
        String zipcode
) {
    public static UpdateMemberResponse from(Member member) {
        Address address = member.getAddress();
        return new UpdateMemberResponse(
                member.getId(),
                member.getName(),
                address != null ? address.getCity() : null,
                address != null ? address.getStreet() : null,
                address != null ? address.getZipcode() : null
        );
    }
}