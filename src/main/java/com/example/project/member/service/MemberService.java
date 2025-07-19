package com.example.project.member.service;

import com.example.project.common.exception.MemberNotFoundException;
import com.example.project.member.dto.MemberRegisterRequest;
import com.example.project.member.dto.MemberRegisterResponse;
import com.example.project.member.entity.Member;
import com.example.project.member.dto.MemberListResponseDto;
import com.example.project.member.dto.MemberSortType;
import com.example.project.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Page<MemberListResponseDto> getMemberList(MemberSortType sortType, Pageable pageable) {
        return memberRepository.searchList(sortType, pageable);
    }

    @Transactional
    public int updateProfileViewCount(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
        member.increaseProfileViewCount();
        return member.getProfileViewCount();
    }

    @Transactional
    public MemberRegisterResponse register(MemberRegisterRequest request) {

        Member member = Member.builder()
                .name(request.getName())
                .profileViewCount(0)
                .point(0)
                .build();
        Member saved = memberRepository.save(member);
        return new MemberRegisterResponse(saved.getId(), saved.getName(), saved.getRegisteredDate(), saved.getProfileViewCount(), saved.getPoint());
    }
}
