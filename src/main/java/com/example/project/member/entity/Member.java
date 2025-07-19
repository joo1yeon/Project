package com.example.project.member.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, updatable = false)
    private LocalDateTime registeredDate;

    @Column(nullable = false)
    private int profileViewCount; // 프로필 상세 조회수

    @Column(nullable = false)
    private int point; // 포인트(원화와 1:1)

    @Builder
    public Member(String name, LocalDateTime registeredDate, int profileViewCount, int point) {
        this.name = name;
        this.registeredDate = registeredDate;
        this.profileViewCount = profileViewCount;
        this.point = point;
    }

    // 프로필 조회수 증가 메서드
    public void increaseProfileViewCount() {
        this.profileViewCount += 1;
    }

    // 포인트 충전 메서드
    public void addPoint(int amount) {
        this.point += amount;
    }

    // 엔티티 생성시 자동 등록일자 세팅
    @PrePersist
    protected void onCreate() {
        this.registeredDate = LocalDateTime.now();
    }

}
