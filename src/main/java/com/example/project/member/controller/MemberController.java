package com.example.project.member.controller;

import com.example.project.member.dto.MemberListResponseDto;
import com.example.project.member.dto.MemberRegisterRequest;
import com.example.project.member.dto.MemberRegisterResponse;
import com.example.project.member.dto.MemberSortType;
import com.example.project.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@Tag(name = "Member", description = "회원 API")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원 프로필 목록 조회", description = "sorting, pagination 적용")
    @GetMapping("/list")
    public Page<MemberListResponseDto> getMemberList(@RequestParam(defaultValue = "NAME")MemberSortType sortType, Pageable pageable) {
        return memberService.getMemberList(sortType, pageable);
    }

    @Operation(summary = "회원 프로필 상세 조회수 업데이트", description = "조회수 업데이트")
    @PutMapping("/{id}/view")
    public ResponseEntity<Integer> updateProfileViewCount(@PathVariable("id") Long memberId) {
        int updatedViewCount = memberService.updateProfileViewCount(memberId);
        return ResponseEntity.ok(updatedViewCount);
    }

    @Operation(summary = "회원 등록", description = "회원 등록")
    @PostMapping("/register")
    public ResponseEntity<MemberRegisterResponse> register(@RequestBody MemberRegisterRequest request) {
        MemberRegisterResponse response = memberService.register(request);
        return ResponseEntity.ok(response);
    }
}
