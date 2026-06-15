package com.artlog.domain.review.dto.response;

import com.artlog.domain.review.entity.Review;

import java.time.LocalDateTime;

public record PublicReviewResponse(
        Long reviewId,
        Long userId,
        String nickname,
        Long exhibitionId,
        String exhibitionTitle,
        String museumName,
        Long artworkId,
        String artworkTitle,
        String artistName,
        String reviewType,
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

    public static PublicReviewResponse from(Review review) {
        return new PublicReviewResponse(
                review.getId(),
                review.getUser().getId(),
                review.getUser().getNickname(),
                review.getExhibition().getId(),
                review.getExhibition().getTitle(),
                review.getExhibition().getMuseumName(),
                review.getArtwork() == null ? null : review.getArtwork().getId(),
                review.getArtwork() == null ? null : review.getArtwork().getTitle(),
                review.getArtwork() == null ? null : review.getArtwork().getArtistName(),
                review.getReviewType().name(),
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