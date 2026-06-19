package com.artlog.domain.review.comment.dto.response;

import com.artlog.domain.review.comment.entity.ReviewComment;

import java.time.LocalDateTime;

public record ReviewCommentResponse(
        Long commentId,
        Long reviewId,
        Long userId,
        String nickname,
        String content,
        boolean writtenByMe,
        LocalDateTime createdAt
) {

    public static ReviewCommentResponse from(
            ReviewComment comment,
            Long currentUserId
    ) {
        Long writerId = comment.getUser().getId();

        return new ReviewCommentResponse(
                comment.getId(),
                comment.getReview().getId(),
                writerId,
                comment.getUser().getNickname(),
                comment.getContent(),
                currentUserId != null && writerId.equals(currentUserId),
                comment.getCreatedAt()
        );
    }
}