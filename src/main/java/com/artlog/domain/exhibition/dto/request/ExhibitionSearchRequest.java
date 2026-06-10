package com.artlog.domain.exhibition.dto.request;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record ExhibitionSearchRequest(

        // 전시명, 미술관명, 위치, 메모 통합 검색
        String keyword,

        // 미술관명만 따로 필터
        String museumName,

        // 위치 필터
        String location,

        // 관람일 시작 범위
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate visitFrom,

        // 관람일 종료 범위
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate visitTo
) {
}