package com.artlog.domain.statistics.dto.response;

public record TopMuseumResponse(
        String museumName,
        long count
) {
}