package com.artlog.domain.review.controller;

import com.artlog.domain.review.dto.response.PublicReviewResponse;
import com.artlog.domain.review.dto.response.PublicReviewSimpleResponse;
import com.artlog.domain.review.service.PublicReviewService;
import com.artlog.global.response.ApiResponse;
import com.artlog.global.response.PageResponse;
import com.artlog.global.response.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Public Review", description = "공개 감상 기록 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/reviews")
public class PublicReviewController {

    private final PublicReviewService publicReviewService;

    @Operation(summary = "공개 감상 기록 목록 조회", description = "공개 상태로 설정된 감상 기록 목록을 페이징하여 조회합니다.")
    @GetMapping
    public ApiResponse<PageResponse<PublicReviewSimpleResponse>> getPublicReviews(
            @ParameterObject
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        PageResponse<PublicReviewSimpleResponse> response = publicReviewService.getPublicReviews(pageable);

        return ApiResponse.success(SuccessCode.PUBLIC_REVIEW_LIST_SUCCESS, response);
    }

    @Operation(summary = "공개 감상 기록 상세 조회", description = "공개 상태로 설정된 특정 감상 기록을 상세 조회합니다.")
    @GetMapping("/{reviewId}")
    public ApiResponse<PublicReviewResponse> getPublicReview(
            @PathVariable Long reviewId
    ) {
        PublicReviewResponse response = publicReviewService.getPublicReview(reviewId);

        return ApiResponse.success(SuccessCode.PUBLIC_REVIEW_DETAIL_SUCCESS, response);
    }
}