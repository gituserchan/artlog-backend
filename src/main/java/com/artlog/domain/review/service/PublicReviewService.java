package com.artlog.domain.review.service;

import com.artlog.domain.review.dto.response.PublicReviewResponse;
import com.artlog.domain.review.dto.response.PublicReviewSimpleResponse;
import com.artlog.domain.review.entity.Review;
import com.artlog.domain.review.reaction.repository.ReviewBookmarkRepository;
import com.artlog.domain.review.reaction.repository.ReviewLikeRepository;
import com.artlog.domain.review.repository.ReviewRepository;
import com.artlog.domain.review.type.ReviewVisibility;
import com.artlog.global.exception.BusinessException;
import com.artlog.global.exception.ErrorCode;
import com.artlog.global.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublicReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewLikeRepository reviewLikeRepository;
    private final ReviewBookmarkRepository reviewBookmarkRepository;

    public PageResponse<PublicReviewSimpleResponse> getPublicReviews(Pageable pageable) {
        Page<PublicReviewSimpleResponse> page = reviewRepository.findAllByVisibility(
                        ReviewVisibility.PUBLIC,
                        pageable
                )
                .map(review -> PublicReviewSimpleResponse.from(
                        review,
                        reviewLikeRepository.countByReviewId(review.getId()),
                        reviewBookmarkRepository.countByReviewId(review.getId())
                ));

        return PageResponse.from(page);
    }

    public PublicReviewResponse getPublicReview(Long reviewId) {
        Review review = reviewRepository.findByIdAndVisibility(
                        reviewId,
                        ReviewVisibility.PUBLIC
                )
                .orElseThrow(() -> new BusinessException(ErrorCode.REVIEW_NOT_FOUND));

        long likeCount = reviewLikeRepository.countByReviewId(review.getId());
        long bookmarkCount = reviewBookmarkRepository.countByReviewId(review.getId());

        return PublicReviewResponse.from(
                review,
                likeCount,
                bookmarkCount
        );
    }
}