package com.artlog.domain.review.comment.controller;

import com.artlog.domain.review.comment.dto.request.ReviewCommentCreateRequest;
import com.artlog.domain.review.comment.dto.response.ReviewCommentResponse;
import com.artlog.domain.review.comment.service.ReviewCommentService;
import com.artlog.global.response.ApiResponse;
import com.artlog.global.response.SuccessCode;
import com.artlog.global.security.user.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Public Review Comment", description = "공개 감상 댓글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/reviews/{reviewId}/comments")
public class ReviewCommentController {

    private final ReviewCommentService reviewCommentService;

    @Operation(summary = "공개 감상 댓글 목록 조회", description = "공개 감상에 작성된 댓글 목록을 조회합니다.")
    @GetMapping
    public ApiResponse<List<ReviewCommentResponse>> getComments(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long reviewId
    ) {
        Long currentUserId = userDetails == null ? null : userDetails.getUserId();

        List<ReviewCommentResponse> response = reviewCommentService.getComments(
                reviewId,
                currentUserId
        );

        return ApiResponse.success(SuccessCode.OK, response);
    }

    @Operation(summary = "공개 감상 댓글 작성", description = "로그인 사용자가 공개 감상에 댓글을 작성합니다.")
    @PostMapping
    public ApiResponse<ReviewCommentResponse> createComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long reviewId,
            @Valid @RequestBody ReviewCommentCreateRequest request
    ) {
        Long userId = userDetails == null ? null : userDetails.getUserId();

        ReviewCommentResponse response = reviewCommentService.createComment(
                reviewId,
                userId,
                request
        );

        return ApiResponse.success(SuccessCode.OK, response);
    }

    @Operation(summary = "공개 감상 댓글 삭제", description = "댓글 작성자 본인이 댓글을 삭제합니다.")
    @DeleteMapping("/{commentId}")
    public ApiResponse<Void> deleteComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long reviewId,
            @PathVariable Long commentId
    ) {
        Long userId = userDetails == null ? null : userDetails.getUserId();

        reviewCommentService.deleteComment(
                reviewId,
                commentId,
                userId
        );

        return ApiResponse.success(SuccessCode.OK);
    }
}