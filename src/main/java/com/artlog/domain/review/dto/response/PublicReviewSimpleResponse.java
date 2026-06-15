package com.artlog.domain.review.dto.response;

import com.artlog.domain.review.entity.Review;

import java.time.LocalDateTime;

public record PublicReviewSimpleResponse(
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
        LocalDateTime createdAt
) {

    public static PublicReviewSimpleResponse from(Review review) {
        return new PublicReviewSimpleResponse(
                review.getId(),
                review.getUser().getId(),
                review.getUser().getNickname(),
                review.getExhibition().getId(),
                review.getExhibition().getTitle(),
                review.getArtwork() == null ? null : review.getArtwork().getId(),
                review.getArtwork() == null ? null : review.getArtwork().getTitle(),
                review.getReviewType().name(),
                review.getTitle(),
                review.getRating(),
                review.getEmotionTag(),
                review.getKeywords(),
                review.getImageUrl(),
                review.getCreatedAt()
        );
    }
}