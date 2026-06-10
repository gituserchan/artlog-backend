package com.artlog.domain.statistics.dto.response;

public record MonthlyVisitResponse(
        String month,
        long count
) {
}