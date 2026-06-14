package com.artlog.domain.review.controller;

import com.artlog.domain.review.dto.request.ArtworkReviewCreateRequest;
import com.artlog.domain.review.dto.request.ExhibitionReviewCreateRequest;
import com.artlog.domain.review.dto.request.ReviewSearchRequest;
import com.artlog.domain.review.dto.request.ReviewUpdateRequest;
import com.artlog.domain.review.dto.response.ReviewResponse;
import com.artlog.domain.review.dto.response.ReviewSimpleResponse;
import com.artlog.domain.review.service.ReviewService;
import com.artlog.domain.review.type.ReviewType;
import com.artlog.domain.review.type.ReviewVisibility;
import com.artlog.global.response.ApiResponse;
import com.artlog.global.response.PageResponse;
import com.artlog.global.response.SuccessCode;
import com.artlog.global.security.user.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "Review", description = "감상 기록 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "전시 감상 등록", description = "현재 로그인한 사용자의 특정 전시에 대한 감상 기록을 등록합니다.")
    @PostMapping("/exhibitions/{exhibitionId}/reviews")
    public ApiResponse<ReviewResponse> createExhibitionReview(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long exhibitionId,
            @Valid @RequestBody ExhibitionReviewCreateRequest request
    ) {
        ReviewResponse response = reviewService.createExhibitionReview(
                userDetails.getUserId(),
                exhibitionId,
                request
        );

        return ApiResponse.success(SuccessCode.REVIEW_CREATE_SUCCESS, response);
    }

    @Operation(summary = "작품 감상 등록", description = "현재 로그인한 사용자의 특정 전시 속 작품에 대한 감상 기록을 등록합니다.")
    @PostMapping("/exhibitions/{exhibitionId}/artworks/{artworkId}/reviews")
    public ApiResponse<ReviewResponse> createArtworkReview(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long exhibitionId,
            @PathVariable Long artworkId,
            @Valid @RequestBody ArtworkReviewCreateRequest request
    ) {
        ReviewResponse response = reviewService.createArtworkReview(
                userDetails.getUserId(),
                exhibitionId,
                artworkId,
                request
        );

        return ApiResponse.success(SuccessCode.REVIEW_CREATE_SUCCESS, response);
    }

    @Operation(summary = "내 감상 기록 전체 조회", description = "현재 로그인한 사용자의 모든 감상 기록을 페이징하여 조회합니다. reviewType을 넘기면 전시/작품 감상만 필터링할 수 있습니다.")
    @GetMapping("/reviews")
    public ApiResponse<PageResponse<ReviewSimpleResponse>> getMyReviews(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Parameter(description = "감상 유형, EXHIBITION 또는 ARTWORK")
            @RequestParam(required = false) ReviewType reviewType,
            @ParameterObject
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        PageResponse<ReviewSimpleResponse> response = reviewService.getMyReviews(
                userDetails.getUserId(),
                reviewType,
                pageable
        );

        return ApiResponse.success(SuccessCode.REVIEW_LIST_SUCCESS, response);
    }

    @Operation(summary = "전시별 감상 기록 조회", description = "현재 로그인한 사용자의 특정 전시에 연결된 감상 기록을 페이징하여 조회합니다.")
    @GetMapping("/exhibitions/{exhibitionId}/reviews")
    public ApiResponse<PageResponse<ReviewSimpleResponse>> getReviewsByExhibition(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long exhibitionId,
            @ParameterObject
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        PageResponse<ReviewSimpleResponse> response = reviewService.getReviewsByExhibition(
                userDetails.getUserId(),
                exhibitionId,
                pageable
        );

        return ApiResponse.success(SuccessCode.REVIEW_LIST_SUCCESS, response);
    }

    @Operation(summary = "작품별 감상 기록 조회", description = "현재 로그인한 사용자의 특정 작품에 연결된 감상 기록을 페이징하여 조회합니다.")
    @GetMapping("/exhibitions/{exhibitionId}/artworks/{artworkId}/reviews")
    public ApiResponse<PageResponse<ReviewSimpleResponse>> getReviewsByArtwork(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long exhibitionId,
            @PathVariable Long artworkId,
            @ParameterObject
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        PageResponse<ReviewSimpleResponse> response = reviewService.getReviewsByArtwork(
                userDetails.getUserId(),
                exhibitionId,
                artworkId,
                pageable
        );

        return ApiResponse.success(SuccessCode.REVIEW_LIST_SUCCESS, response);
    }

    @Operation(summary = "감상 기록 검색", description = "현재 로그인한 사용자의 감상 기록을 조건에 따라 페이징하여 검색합니다.")
    @GetMapping("/reviews/search")
    public ApiResponse<PageResponse<ReviewSimpleResponse>> searchReviews(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) ReviewType reviewType,
            @RequestParam(required = false) ReviewVisibility visibility,
            @RequestParam(required = false) Integer minRating,
            @RequestParam(required = false) Integer maxRating,
            @RequestParam(required = false) String emotionTag,
            @RequestParam(required = false) String keywords,
            @RequestParam(required = false) Boolean wantToRevisit,
            @RequestParam(required = false) LocalDate createdFrom,
            @RequestParam(required = false) LocalDate createdTo,
            @ParameterObject
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        ReviewSearchRequest request = new ReviewSearchRequest(
                keyword,
                reviewType,
                visibility,
                minRating,
                maxRating,
                emotionTag,
                keywords,
                wantToRevisit,
                createdFrom,
                createdTo
        );

        PageResponse<ReviewSimpleResponse> response = reviewService.searchReviews(
                userDetails.getUserId(),
                request,
                pageable
        );

        return ApiResponse.success(SuccessCode.REVIEW_LIST_SUCCESS, response);
    }

    @Operation(summary = "감상 기록 상세 조회", description = "현재 로그인한 사용자의 특정 감상 기록을 상세 조회합니다.")
    @GetMapping("/reviews/{reviewId}")
    public ApiResponse<ReviewResponse> getReview(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long reviewId
    ) {
        ReviewResponse response = reviewService.getReview(
                userDetails.getUserId(),
                reviewId
        );

        return ApiResponse.success(SuccessCode.REVIEW_DETAIL_SUCCESS, response);
    }

    @Operation(summary = "감상 기록 수정", description = "현재 로그인한 사용자의 특정 감상 기록을 수정합니다.")
    @PutMapping("/reviews/{reviewId}")
    public ApiResponse<ReviewResponse> updateReview(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long reviewId,
            @Valid @RequestBody ReviewUpdateRequest request
    ) {
        ReviewResponse response = reviewService.updateReview(
                userDetails.getUserId(),
                reviewId,
                request
        );

        return ApiResponse.success(SuccessCode.REVIEW_UPDATE_SUCCESS, response);
    }

    @Operation(summary = "감상 기록 삭제", description = "현재 로그인한 사용자의 특정 감상 기록을 삭제합니다.")
    @DeleteMapping("/reviews/{reviewId}")
    public ApiResponse<Void> deleteReview(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long reviewId
    ) {
        reviewService.deleteReview(
                userDetails.getUserId(),
                reviewId
        );

        return ApiResponse.success(SuccessCode.REVIEW_DELETE_SUCCESS);
    }
}