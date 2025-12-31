package jpabook.jpashop.domain.member.api;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.member.dto.*;
import jpabook.jpashop.domain.member.service.MemberService;
import jpabook.jpashop.global.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberApiController implements MemberApiControllerDocs {
    private final MemberService memberService;

    @PostMapping("/api/members")
    public ResponseEntity<CommonResponse<CreateMemberResponse>> saveMember(@RequestBody @Valid CreateMemberRequest request) {
        Long id = memberService.join(request.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.createSuccess(new CreateMemberResponse(id)));
    }

    @PutMapping("/api/members/{id}")
    public ResponseEntity<CommonResponse<UpdateMemberResponse>> updateMember(@PathVariable Long id, @RequestBody @Valid UpdateMemberRequest request) {
        memberService.update(id, request);
        return ResponseEntity.ok(
                CommonResponse.createSuccess(UpdateMemberResponse.from(memberService.findOne(id))
        ));


    }

    @GetMapping("/api/members")
    public ResponseEntity<CommonResponse<List<MemberDto>>> findMembers() {
        List<MemberDto> result = memberService.findMembers().stream()
                .map(MemberDto::from)
                .toList();
        return ResponseEntity.ok(CommonResponse.createSuccess(result));
    }


}
