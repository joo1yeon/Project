package com.example.project.point.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PointChargeResponse {

    private int chargedPoint;   // 충전된 포인트
    private int totalPoint;     // 총 보유 포인트
    private String paymentStatus;   // 결제 결과(성공/실패)

}
