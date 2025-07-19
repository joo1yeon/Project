package com.example.project.member.repository;

import com.example.project.member.dto.MemberListResponseDto;
import com.example.project.member.dto.MemberSortType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryCustom {
    Page<MemberListResponseDto> searchList(MemberSortType sortType, Pageable pageable);
}
