package com.artlog.domain.review.repository;

import com.artlog.domain.review.entity.Review;
import com.artlog.domain.review.type.ReviewType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByUserIdOrderByCreatedAtDesc(Long userId);

    List<Review> findAllByUserIdAndReviewTypeOrderByCreatedAtDesc(
            Long userId,
            ReviewType reviewType
    );

    List<Review> findAllByExhibitionIdAndUserIdOrderByCreatedAtDesc(
            Long exhibitionId,
            Long userId
    );

    List<Review> findAllByArtworkIdAndUserIdOrderByCreatedAtDesc(
            Long artworkId,
            Long userId
    );

    Optional<Review> findByIdAndUserId(Long reviewId, Long userId);
}