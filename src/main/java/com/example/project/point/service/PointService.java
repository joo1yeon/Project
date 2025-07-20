package com.example.project.point.service;

import com.example.project.common.exception.MemberNotFoundException;
import com.example.project.member.entity.Member;
import com.example.project.member.repository.MemberRepository;
import com.example.project.point.dto.PointChargeRequest;
import com.example.project.point.dto.PointChargeResponse;
import com.example.project.point.dto.TossConfirmResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PointService {

    private final TossPayments tossPayments;
    private final MemberRepository memberRepository;

    @Transactional
    public PointChargeResponse confirmAndChargePoint(PointChargeRequest request) {

        // 1. 토스페이먼츠 결제 승인
        TossConfirmResult result = tossPayments.confirmPayment(request.getPaymentKey(), request.getOrderId(), request.getAmount());

        if (!result.isSuccess()) {
            return new PointChargeResponse(0, false, "결제 승인 실패");
        }

        // 2. 결제 성공 시, 해당 회원의 포인트 충전
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new MemberNotFoundException(request.getMemberId()));
        member.addPoint(request.getAmount());

        return new PointChargeResponse(request.getAmount(), true, "포인트 충전 완료");
    }

}
