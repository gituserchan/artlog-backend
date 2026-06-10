package com.artlog.domain.statistics.dto.response;

public record RatingDistributionResponse(
        int rating,
        long count
) {
}