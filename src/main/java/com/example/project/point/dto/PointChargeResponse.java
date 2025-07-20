package com.example.project.point.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PointChargeResponse {

    private int chargedPoint;   // 충전된 포인트
    private boolean success;   // 결제 결과(성공/실패)
    private String message;

}
