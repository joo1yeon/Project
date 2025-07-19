package com.example.project.point.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PointChargeRequest {

    private Long memberId;      // 충전할 회원 id
    private int amount;         // 충전 금액
    private String paymentKey;  // 토스페이먼츠 결제 키
    private String orderId;     // 결제 주문 번호

}
