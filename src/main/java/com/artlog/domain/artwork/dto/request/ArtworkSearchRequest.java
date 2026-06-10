package com.artlog.domain.artwork.dto.request;

public record ArtworkSearchRequest(

        // 작품명, 작가명, 제작연도, 매체, 메모 통합 검색
        String keyword,

        // 작가명 필터
        String artistName,

        // 제작연도 필터
        String productionYear,

        // 매체 필터
        String medium,

        // 특정 전시 안에서만 검색하고 싶을 때
        Long exhibitionId
) {
}