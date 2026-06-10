package com.artlog.domain.statistics.dto.response;

public record TopEmotionTagResponse(
        String emotionTag,
        long count
) {
}