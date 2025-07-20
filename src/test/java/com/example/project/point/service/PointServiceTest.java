package com.example.project.point.service;

import com.example.project.member.entity.Member;
import com.example.project.member.repository.MemberRepository;
import com.example.project.point.dto.PointChargeRequest;
import com.example.project.point.dto.PointChargeResponse;
import com.example.project.point.dto.TossConfirmResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PointServiceTest {

    @Autowired
    PointService pointService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    private TossPayments tossPayments;

    Long memberId;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public TossPayments tossPayments() {
            return Mockito.mock(TossPayments.class);
        }
    }

    @BeforeEach
    void setUp() {

        Member member = memberRepository.save(Member.builder()
                .name("연주원")
                .profileViewCount(20)
                .point(2000)
                .build());

        memberId = member.getId();

        Mockito.when(tossPayments.confirmPayment("P_1234", "O_1234", 1000))
                .thenReturn(new TossConfirmResult(true, "DONE"));
    }

    @Test
    @DisplayName("포인트 충전이 정상적으로 이루어짐")
    void chargePointTest() {

        // given
        PointChargeRequest request = new PointChargeRequest(
                memberId,
                1000,
                "P_1234",
                "O_1234"
        );

        // when
        PointChargeResponse response = pointService.confirmAndChargePoint(request);

        // then
        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getChargedPoint()).isEqualTo(1000);
        assertThat(response.getMessage()).contains("포인트 충전 완료");

        // DB의 포인트 확인
        Member updated = memberRepository.findById(memberId).get();
        assertThat(updated.getPoint()).isEqualTo(3000);

    }

}