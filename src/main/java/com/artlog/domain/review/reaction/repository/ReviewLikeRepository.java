package com.artlog.domain.review.reaction.repository;

import com.artlog.domain.review.reaction.entity.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {

    boolean existsByUserIdAndReviewId(Long userId, Long reviewId);

    Optional<ReviewLike> findByUserIdAndReviewId(Long userId, Long reviewId);

    long countByReviewId(Long reviewId);
}