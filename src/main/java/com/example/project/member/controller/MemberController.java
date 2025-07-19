package com.example.project.member.controller;

import com.example.project.member.dto.MemberListResponseDto;
import com.example.project.member.dto.MemberRegisterRequest;
import com.example.project.member.dto.MemberRegisterResponse;
import com.example.project.member.dto.MemberSortType;
import com.example.project.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/list")
    public Page<MemberListResponseDto> getMemberList(@RequestParam(defaultValue = "NAME")MemberSortType sortType, Pageable pageable) {
        return memberService.getMemberList(sortType, pageable);
    }

    @PutMapping("/{id}/view")
    public ResponseEntity<Integer> updateProfileViewCount(@PathVariable("id") Long memberId) {
        int updatedViewCount = memberService.updateProfileViewCount(memberId);
        return ResponseEntity.ok(updatedViewCount);
    }

    @PostMapping("/register")
    public ResponseEntity<MemberRegisterResponse> register(@RequestBody MemberRegisterRequest request) {
        MemberRegisterResponse response = memberService.register(request);
        return ResponseEntity.ok(response);
    }
}
