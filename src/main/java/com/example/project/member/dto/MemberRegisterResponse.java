package com.example.project.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MemberRegisterResponse {

    private Long id;
    private String name;
    private LocalDateTime registeredDate;
    private int profileViewCount;
    private int point;

}
