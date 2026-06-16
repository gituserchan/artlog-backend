package com.artlog.domain.review.reaction.repository;

import com.artlog.domain.review.reaction.entity.ReviewBookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewBookmarkRepository extends JpaRepository<ReviewBookmark, Long> {

    boolean existsByUserIdAndReviewId(Long userId, Long reviewId);

    Optional<ReviewBookmark> findByUserIdAndReviewId(Long userId, Long reviewId);

    Page<ReviewBookmark> findAllByUserId(Long userId, Pageable pageable);

    long countByReviewId(Long reviewId);
}