package com.example.project.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MemberListResponseDto {
    private Long id;
    private String name;
    private int profileViewCount;
    private LocalDateTime registeredDate;
}
