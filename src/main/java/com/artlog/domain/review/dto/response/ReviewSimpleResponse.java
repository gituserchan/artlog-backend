package com.artlog.domain.review.dto.response;

import com.artlog.domain.review.entity.Review;
import com.artlog.domain.review.type.ReviewType;

import java.time.LocalDateTime;

public record ReviewSimpleResponse(
        Long reviewId,
        ReviewType reviewType,
        Long exhibitionId,
        String exhibitionTitle,
        Long artworkId,
        String artworkTitle,
        String title,
        Integer rating,
        String emotionTag,
        Boolean wantToRevisit,
        LocalDateTime createdAt
) {

    public static ReviewSimpleResponse from(Review review) {
        return new ReviewSimpleResponse(
                review.getId(),
                review.getReviewType(),
                review.getExhibition().getId(),
                review.getExhibition().getTitle(),
                review.getArtwork() == null ? null : review.getArtwork().getId(),
                review.getArtwork() == null ? null : review.getArtwork().getTitle(),
                review.getTitle(),
                review.getRating(),
                review.getEmotionTag(),
                review.getWantToRevisit(),
                review.getCreatedAt()
        );
    }
}