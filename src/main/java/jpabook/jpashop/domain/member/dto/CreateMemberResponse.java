package jpabook.jpashop.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원 가입 응답 DTO")
public record CreateMemberResponse(

        @Schema(description = "생성된 회원 PK", example = "1")
        Long id
) {
}