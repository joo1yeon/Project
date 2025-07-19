package com.example.project.member.service;

import com.example.project.member.entity.Member;
import com.example.project.member.dto.MemberListResponseDto;
import com.example.project.member.dto.MemberSortType;
import com.example.project.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    Long memberId;

    @BeforeEach
    void setUp() {

        memberRepository.save(Member.builder()
                .name("김주원")
                .profileViewCount(10)
                .point(1000)
                .build());

        memberRepository.save(Member.builder()
                .name("박주원")
                .profileViewCount(30)
                .point(3000)
                .build());


        Member member = memberRepository.save(Member.builder()
                .name("연주원")
                .profileViewCount(20)
                .point(2000)
                .build());

        memberId = member.getId();
    }

    @Test
    @DisplayName("회원 목록을 이름 오름차순으로 조회하면 가나다순 정렬됨")
    void getMemberList_sortedByName() {

        // when
        Page<MemberListResponseDto> page = memberService.getMemberList(MemberSortType.NAME, PageRequest.of(0, 10));
        List<MemberListResponseDto> content = page.getContent();

        // then
        assertThat(content).hasSize(3);
        assertThat(content.get(0).getName()).isEqualTo("김주원");
        assertThat(content.get(1).getName()).isEqualTo("박주원");
        assertThat(content.get(2).getName()).isEqualTo("연주원");

    }

    @Test
    @DisplayName("회원 목록을 조회수 기준으로 조회하면 조회수가 많은 순서로 정렬됨")
    void getMemberList_sortedByView() {

        // when
        Page<MemberListResponseDto> page = memberService.getMemberList(MemberSortType.VIEW, PageRequest.of(0, 10));
        List<MemberListResponseDto> content = page.getContent();

        // then
        assertThat(content).hasSize(3);
        assertThat(content.get(0).getProfileViewCount()).isEqualTo(30);
        assertThat(content.get(1).getProfileViewCount()).isEqualTo(20);
        assertThat(content.get(2).getProfileViewCount()).isEqualTo(10);

    }

    @Test
    @DisplayName("회원 목록을 등록일 기준으로 조회하면 최근 등록된 순서로 정렬됨")
    void getMemberList_sortedByRecent() {

        // when
        Page<MemberListResponseDto> page = memberService.getMemberList(MemberSortType.RECENT, PageRequest.of(0, 10));
        List<MemberListResponseDto> content = page.getContent();

        // then
        assertThat(content).hasSize(3);
        // 등록일이 가장 최근인 "박주원" 이 맨 앞
        assertThat(content.get(0).getName()).isEqualTo("박주원");
    }

    @Test
    @DisplayName("페이징 적용 테스트: size = 2면 한 페이지에 2명만 조회됨")
    void getMemberList_paging() {

        // when
        Page<MemberListResponseDto> page = memberService.getMemberList(MemberSortType.NAME, PageRequest.of(0, 2));
        List<MemberListResponseDto> content = page.getContent();

        // then
        assertThat(content).hasSize(2);
        assertThat(page.getTotalElements()).isEqualTo(3);
        assertThat(page.getTotalPages()).isEqualTo(2);
    }

    @Test
    @DisplayName("회원 프로필 조회 시 업데이트가 1 증가")
    void updateProfileViewCountTest() {

        // given
        Member member = memberRepository.findById(memberId).orElseThrow();
        int before = member.getProfileViewCount();

        // when
        int after = memberService.updateProfileViewCount(memberId);

        // then
        assertThat(after).isEqualTo(before + 1);

        // DB 반영 확인
        Member updated = memberRepository.findById(memberId).orElseThrow();
        assertThat(updated.getProfileViewCount()).isEqualTo(after);
    }
}