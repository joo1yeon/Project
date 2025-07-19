package com.example.project.common.exception;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException(Long memberId) {
        super("해당 회원(" + memberId + ")을 찾을 수 없습니다.");
    }
}
