package com.example.project.point.service;

import com.example.project.common.exception.MemberNotFoundException;
import com.example.project.member.entity.Member;
import com.example.project.member.repository.MemberRepository;
import com.example.project.point.dto.PointChargeRequest;
import com.example.project.point.dto.PointChargeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointService {

    private final MemberRepository memberRepository;
    // TossPaymentsClient

    @Transactional
    public PointChargeResponse chargePoint(PointChargeRequest request) {

        // 1. 결제 승인 확인 (토스페이먼츠 연동)
        // 연동 성공 가정

        // 2. 회원 조회
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new MemberNotFoundException(request.getMemberId()));

        // 3. 결제 금액만큼 포인트 적립 (1:1)
        member.addPoint(request.getAmount());

        return new PointChargeResponse(request.getAmount(), member.getPoint(), "SUCCESS");

    }
}
