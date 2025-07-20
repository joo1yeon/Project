package com.example.project.point.service;

import com.example.project.point.dto.TossConfirmResult;

public interface TossPayments {

    TossConfirmResult confirmPayment(String paymentKey, String orderId, int amount);
}
