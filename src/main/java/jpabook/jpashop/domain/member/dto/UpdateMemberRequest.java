package jpabook.jpashop.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

@Schema(description = "회원 정보 수정 요청 DTO")
public record UpdateMemberRequest(

        @Schema(description = "변경할 이름 (null: 변경안함, 빈문자열 불가)", example = "김철수")
        @Pattern(regexp = "^$|.*\\S.*", message = "이름은 공백일 수 없습니다.")
        String name,

        @Schema(description = "변경할 도시", example = "부산")
        String city,

        @Schema(description = "변경할 거리", example = "해운대로 100")
        String street,

        @Schema(description = "변경할 우편번호", example = "48000")
        String zipcode
) {
}