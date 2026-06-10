package com.artlog.domain.statistics.dto.response;

public record StatisticsSummaryResponse(
        long exhibitionCount,
        long artworkCount,
        long reviewCount,
        Double averageRating
) {
}