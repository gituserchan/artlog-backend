package com.artlog.domain.review.dto.response;

import com.artlog.domain.review.entity.Review;
import com.artlog.domain.review.type.ReviewType;
import com.artlog.domain.review.type.ReviewVisibility;

import java.time.LocalDateTime;

public record ReviewResponse(
        Long reviewId,
        Long exhibitionId,
        String exhibitionTitle,
        Long artworkId,
        String artworkTitle,
        ReviewType reviewType,
        ReviewVisibility visibility,
        String title,
        String content,
        Integer rating,
        String emotionTag,
        String keywords,
        Boolean wantToRevisit,
        String imageUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static ReviewResponse from(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getExhibition().getId(),
                review.getExhibition().getTitle(),
                review.getArtwork() == null ? null : review.getArtwork().getId(),
                review.getArtwork() == null ? null : review.getArtwork().getTitle(),
                review.getReviewType(),
                review.getVisibility(),
                review.getTitle(),
                review.getContent(),
                review.getRating(),
                review.getEmotionTag(),
                review.getKeywords(),
                review.getWantToRevisit(),
                review.getImageUrl(),
                review.getCreatedAt(),
                review.getUpdatedAt()
        );
    }
}