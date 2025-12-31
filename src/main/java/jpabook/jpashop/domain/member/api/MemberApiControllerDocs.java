package jpabook.jpashop.domain.member.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jpabook.jpashop.domain.member.dto.*;
import jpabook.jpashop.global.common.dto.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Member API", description = "회원 가입, 수정, 조회 관련 API")
public interface MemberApiControllerDocs {

    @Operation(summary = "회원 가입", description = "이름과 주소 정보를 받아 신규 회원을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원 가입 성공"),
            @ApiResponse(responseCode = "400", description = "이미 존재하는 회원 이름입니다.", content = @Content)
    })
    ResponseEntity<CommonResponse<CreateMemberResponse>> saveMember(
            @RequestBody CreateMemberRequest request
    );

    @Operation(summary = "회원 정보 수정", description = "회원 ID와 변경할 정보를 받아 수정합니다. (이름은 공백 불가)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 정보 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터 (예: 이름 공백)", content = @Content),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 회원 ID입니다.", content = @Content)
    })
    ResponseEntity<CommonResponse<UpdateMemberResponse>> updateMember(
            @Parameter(description = "수정할 회원의 ID", example = "1") @PathVariable Long id,
            @RequestBody UpdateMemberRequest request
    );

    @Operation(summary = "회원 전체 조회", description = "등록된 모든 회원의 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    ResponseEntity<CommonResponse<List<MemberDto>>> findMembers();
}