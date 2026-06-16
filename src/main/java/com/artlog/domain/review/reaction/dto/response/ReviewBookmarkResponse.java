package com.artlog.domain.review.reaction.dto.response;

import com.artlog.domain.review.reaction.entity.ReviewBookmark;

import java.time.LocalDateTime;

public record ReviewBookmarkResponse(
        Long bookmarkId,
        Long reviewId,
        Long userId,
        String nickname,
        Long exhibitionId,
        String exhibitionTitle,
        Long artworkId,
        String artworkTitle,
        String reviewType,
        String title,
        Integer rating,
        String emotionTag,
        String keywords,
        String imageUrl,
        LocalDateTime bookmarkedAt
) {

    public static ReviewBookmarkResponse from(ReviewBookmark bookmark) {
        return new ReviewBookmarkResponse(
                bookmark.getId(),
                bookmark.getReview().getId(),
                bookmark.getReview().getUser().getId(),
                bookmark.getReview().getUser().getNickname(),
                bookmark.getReview().getExhibition().getId(),
                bookmark.getReview().getExhibition().getTitle(),
                bookmark.getReview().getArtwork() == null ? null : bookmark.getReview().getArtwork().getId(),
                bookmark.getReview().getArtwork() == null ? null : bookmark.getReview().getArtwork().getTitle(),
                bookmark.getReview().getReviewType().name(),
                bookmark.getReview().getTitle(),
                bookmark.getReview().getRating(),
                bookmark.getReview().getEmotionTag(),
                bookmark.getReview().getKeywords(),
                bookmark.getReview().getImageUrl(),
                bookmark.getCreatedAt()
        );
    }
}