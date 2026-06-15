package com.artlog.domain.review.service;

import com.artlog.domain.review.dto.response.PublicReviewResponse;
import com.artlog.domain.review.dto.response.PublicReviewSimpleResponse;
import com.artlog.domain.review.entity.Review;
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

    public PageResponse<PublicReviewSimpleResponse> getPublicReviews(Pageable pageable) {
        Page<PublicReviewSimpleResponse> page = reviewRepository.findAllByVisibility(
                        ReviewVisibility.PUBLIC,
                        pageable
                )
                .map(PublicReviewSimpleResponse::from);

        return PageResponse.from(page);
    }

    public PublicReviewResponse getPublicReview(Long reviewId) {
        Review review = reviewRepository.findByIdAndVisibility(
                        reviewId,
                        ReviewVisibility.PUBLIC
                )
                .orElseThrow(() -> new BusinessException(ErrorCode.REVIEW_NOT_FOUND));

        return PublicReviewResponse.from(review);
    }
}