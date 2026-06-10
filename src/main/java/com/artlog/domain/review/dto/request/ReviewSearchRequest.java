package com.artlog.domain.review.dto.request;

import com.artlog.domain.review.type.ReviewType;
import com.artlog.domain.review.type.ReviewVisibility;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record ReviewSearchRequest(

        // 제목, 내용, 감정태그, 키워드 통합 검색
        String keyword,

        ReviewType reviewType,

        ReviewVisibility visibility,

        Integer minRating,

        Integer maxRating,

        String emotionTag,

        String keywords,

        Boolean wantToRevisit,

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate createdFrom,

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate createdTo
) {
}