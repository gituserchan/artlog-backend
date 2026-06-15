package com.artlog.domain.review.repository;

import com.artlog.domain.review.entity.Review;
import com.artlog.domain.review.type.ReviewType;
import com.artlog.domain.review.type.ReviewVisibility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findAllByUserId(Long userId, Pageable pageable);

    Page<Review> findAllByUserIdAndReviewType(
            Long userId,
            ReviewType reviewType,
            Pageable pageable
    );

    Page<Review> findAllByExhibitionIdAndUserId(
            Long exhibitionId,
            Long userId,
            Pageable pageable
    );

    Page<Review> findAllByArtworkIdAndUserId(
            Long artworkId,
            Long userId,
            Pageable pageable
    );

    Optional<Review> findByIdAndUserId(Long reviewId, Long userId);

    Page<Review> findAllByVisibility(
            ReviewVisibility visibility,
            Pageable pageable
    );

    Optional<Review> findByIdAndVisibility(
            Long reviewId,
            ReviewVisibility visibility
    );

    @Query("""
            SELECT r
            FROM Review r
            WHERE r.user.id = :userId
              AND (
                    :keyword IS NULL
                    OR r.title LIKE CONCAT('%', :keyword, '%')
                    OR r.content LIKE CONCAT('%', :keyword, '%')
                    OR r.emotionTag LIKE CONCAT('%', :keyword, '%')
                    OR r.keywords LIKE CONCAT('%', :keyword, '%')
                  )
              AND (
                    :reviewType IS NULL
                    OR r.reviewType = :reviewType
                  )
              AND (
                    :visibility IS NULL
                    OR r.visibility = :visibility
                  )
              AND (
                    :minRating IS NULL
                    OR r.rating >= :minRating
                  )
              AND (
                    :maxRating IS NULL
                    OR r.rating <= :maxRating
                  )
              AND (
                    :emotionTag IS NULL
                    OR r.emotionTag LIKE CONCAT('%', :emotionTag, '%')
                  )
              AND (
                    :keywords IS NULL
                    OR r.keywords LIKE CONCAT('%', :keywords, '%')
                  )
              AND (
                    :wantToRevisit IS NULL
                    OR r.wantToRevisit = :wantToRevisit
                  )
              AND (
                    :createdFrom IS NULL
                    OR r.createdAt >= :createdFrom
                  )
              AND (
                    :createdTo IS NULL
                    OR r.createdAt <= :createdTo
                  )
            """)
    Page<Review> searchReviews(
            @Param("userId") Long userId,
            @Param("keyword") String keyword,
            @Param("reviewType") ReviewType reviewType,
            @Param("visibility") ReviewVisibility visibility,
            @Param("minRating") Integer minRating,
            @Param("maxRating") Integer maxRating,
            @Param("emotionTag") String emotionTag,
            @Param("keywords") String keywords,
            @Param("wantToRevisit") Boolean wantToRevisit,
            @Param("createdFrom") LocalDateTime createdFrom,
            @Param("createdTo") LocalDateTime createdTo,
            Pageable pageable
    );

    long countByUserId(Long userId);

    @Query("""
        SELECT AVG(r.rating)
        FROM Review r
        WHERE r.user.id = :userId
          AND r.rating IS NOT NULL
        """)
    Double averageRatingByUserId(@Param("userId") Long userId);

    @Query("""
        SELECT r.rating, COUNT(r.id)
        FROM Review r
        WHERE r.user.id = :userId
          AND r.rating IS NOT NULL
        GROUP BY r.rating
        ORDER BY r.rating DESC
        """)
    List<Object[]> countRatingDistribution(@Param("userId") Long userId);

    @Query("""
        SELECT r.emotionTag, COUNT(r.id)
        FROM Review r
        WHERE r.user.id = :userId
          AND r.emotionTag IS NOT NULL
          AND r.emotionTag <> ''
        GROUP BY r.emotionTag
        ORDER BY COUNT(r.id) DESC
        """)
    List<Object[]> countTopEmotionTags(@Param("userId") Long userId);
}