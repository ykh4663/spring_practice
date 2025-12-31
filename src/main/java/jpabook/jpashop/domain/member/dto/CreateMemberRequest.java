package jpabook.jpashop.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jpabook.jpashop.domain.common.Address;
import jpabook.jpashop.domain.member.Member;

@Schema(description = "회원 가입 요청 DTO")
public record CreateMemberRequest(

        @Schema(description = "회원 이름 (필수)", example = "홍길동")
        @NotEmpty
        String name,

        @Schema(description = "도시", example = "서울")
        String city,

        @Schema(description = "거리(도로명)", example = "테헤란로 123")
        String street,

        @Schema(description = "우편번호", example = "06234")
        String zipcode
){
    public Member toEntity(){
        return Member.builder()
                .name(name)
                .address(Address.builder()
                        .city(city)
                        .street(street)
                        .zipcode(zipcode)
                        .build())
                .build();
    }
}