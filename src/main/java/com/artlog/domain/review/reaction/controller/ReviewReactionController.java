package com.artlog.domain.review.reaction.controller;

import com.artlog.domain.review.reaction.dto.response.ReviewBookmarkResponse;
import com.artlog.domain.review.reaction.service.ReviewReactionService;
import com.artlog.global.response.ApiResponse;
import com.artlog.global.response.PageResponse;
import com.artlog.global.response.SuccessCode;
import com.artlog.global.security.user.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Review Reaction", description = "감상 기록 좋아요/북마크 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewReactionController {

    private final ReviewReactionService reviewReactionService;

    @Operation(summary = "감상 기록 좋아요", description = "공개 감상 기록에 좋아요를 추가합니다.")
    @PostMapping("/reviews/{reviewId}/likes")
    public ApiResponse<Void> likeReview(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long reviewId
    ) {
        reviewReactionService.likeReview(
                userDetails.getUserId(),
                reviewId
        );

        return ApiResponse.success(SuccessCode.REVIEW_LIKE_SUCCESS);
    }

    @Operation(summary = "감상 기록 좋아요 취소", description = "감상 기록에 누른 좋아요를 취소합니다.")
    @DeleteMapping("/reviews/{reviewId}/likes")
    public ApiResponse<Void> unlikeReview(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long reviewId
    ) {
        reviewReactionService.unlikeReview(
                userDetails.getUserId(),
                reviewId
        );

        return ApiResponse.success(SuccessCode.REVIEW_UNLIKE_SUCCESS);
    }

    @Operation(summary = "감상 기록 북마크", description = "공개 감상 기록을 북마크합니다.")
    @PostMapping("/reviews/{reviewId}/bookmarks")
    public ApiResponse<Void> bookmarkReview(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long reviewId
    ) {
        reviewReactionService.bookmarkReview(
                userDetails.getUserId(),
                reviewId
        );

        return ApiResponse.success(SuccessCode.REVIEW_BOOKMARK_SUCCESS);
    }

    @Operation(summary = "감상 기록 북마크 취소", description = "감상 기록에 추가한 북마크를 취소합니다.")
    @DeleteMapping("/reviews/{reviewId}/bookmarks")
    public ApiResponse<Void> unbookmarkReview(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long reviewId
    ) {
        reviewReactionService.unbookmarkReview(
                userDetails.getUserId(),
                reviewId
        );

        return ApiResponse.success(SuccessCode.REVIEW_UNBOOKMARK_SUCCESS);
    }

    @Operation(summary = "내 북마크 목록 조회", description = "현재 로그인한 사용자가 북마크한 감상 기록 목록을 페이징하여 조회합니다.")
    @GetMapping("/users/me/bookmarks")
    public ApiResponse<PageResponse<ReviewBookmarkResponse>> getMyBookmarks(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ParameterObject
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        PageResponse<ReviewBookmarkResponse> response = reviewReactionService.getMyBookmarks(
                userDetails.getUserId(),
                pageable
        );

        return ApiResponse.success(SuccessCode.REVIEW_BOOKMARK_LIST_SUCCESS, response);
    }
}