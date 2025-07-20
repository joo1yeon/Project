package com.example.project.point.controller;

import com.example.project.point.dto.PointChargeRequest;
import com.example.project.point.dto.PointChargeResponse;
import com.example.project.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/point")
public class PointController {

    private final PointService pointService;

    @PostMapping("/charge/confirm")
    public ResponseEntity<PointChargeResponse> chargePoint(@RequestBody PointChargeRequest request) {
        PointChargeResponse response = pointService.confirmAndChargePoint(request);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}
