package com.artlog.domain.review.dto.request;

import com.artlog.domain.review.type.ReviewVisibility;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ReviewUpdateRequest(

        @NotBlank(message = "감상 제목은 필수입니다.")
        @Size(max = 100, message = "감상 제목은 100자 이하로 입력해주세요.")
        String title,

        @NotBlank(message = "감상 내용은 필수입니다.")
        String content,

        @Min(value = 1, message = "평점은 1점 이상이어야 합니다.")
        @Max(value = 5, message = "평점은 5점 이하이어야 합니다.")
        Integer rating,

        @Size(max = 100, message = "감정 태그는 100자 이하로 입력해주세요.")
        String emotionTag,

        @Size(max = 255, message = "키워드는 255자 이하로 입력해주세요.")
        String keywords,

        Boolean wantToRevisit,

        @Size(max = 10, message = "이미지는 최대 10장까지 등록할 수 있습니다.")
        List<@Size(max = 500, message = "이미지 URL은 500자 이하로 입력해주세요.") String> imageUrls,

        ReviewVisibility visibility
) {
}