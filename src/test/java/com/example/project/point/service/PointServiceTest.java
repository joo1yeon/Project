package com.example.project.point.service;

import com.example.project.member.entity.Member;
import com.example.project.member.repository.MemberRepository;
import com.example.project.point.dto.PointChargeRequest;
import com.example.project.point.dto.PointChargeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PointServiceTest {

    @Autowired
    PointService pointService;

    @Autowired
    MemberRepository memberRepository;

    Long memberId;

    @BeforeEach
    void setUp() {

        Member member = memberRepository.save(Member.builder()
                .name("연주원")
                .profileViewCount(20)
                .point(2000)
                .build());

        memberId = member.getId();
    }

    @Test
    @DisplayName("포인트 충전이 정상적으로 이루어짐")
    void chargePointTest() {

        // given
        int chargeAmount = 5000;
        PointChargeRequest request = new PointChargeRequest(
                memberId,
                chargeAmount,
                "paymentKey",
                "orderId"
        );

        // when
        PointChargeResponse response = pointService.chargePoint(request);

        // then
        assertThat(response.getChargedPoint()).isEqualTo(chargeAmount);
        assertThat(response.getPaymentStatus()).isEqualTo("SUCCESS");

        // DB 반영 확인
        Member updatedMember = memberRepository.findById(memberId).orElseThrow();
        assertThat(updatedMember.getPoint()).isEqualTo(2000 + chargeAmount);
        assertThat(response.getTotalPoint()).isEqualTo(updatedMember.getPoint());
    }

}