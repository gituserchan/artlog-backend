package com.artlog.domain.review.reaction.service;

import com.artlog.domain.review.entity.Review;
import com.artlog.domain.review.reaction.dto.response.ReviewBookmarkResponse;
import com.artlog.domain.review.reaction.entity.ReviewBookmark;
import com.artlog.domain.review.reaction.entity.ReviewLike;
import com.artlog.domain.review.reaction.repository.ReviewBookmarkRepository;
import com.artlog.domain.review.reaction.repository.ReviewLikeRepository;
import com.artlog.domain.review.repository.ReviewRepository;
import com.artlog.domain.review.type.ReviewVisibility;
import com.artlog.domain.user.entity.User;
import com.artlog.domain.user.repository.UserRepository;
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
public class ReviewReactionService {

    private final ReviewLikeRepository reviewLikeRepository;
    private final ReviewBookmarkRepository reviewBookmarkRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Transactional
    public void likeReview(
            Long userId,
            Long reviewId
    ) {
        User user = getUser(userId);
        Review review = getPublicReview(reviewId);

        if (reviewLikeRepository.existsByUserIdAndReviewId(userId, reviewId)) {
            throw new BusinessException(ErrorCode.DUPLICATE_REVIEW_LIKE);
        }

        ReviewLike reviewLike = ReviewLike.builder()
                .user(user)
                .review(review)
                .build();

        reviewLikeRepository.save(reviewLike);
    }

    @Transactional
    public void unlikeReview(
            Long userId,
            Long reviewId
    ) {
        ReviewLike reviewLike = reviewLikeRepository.findByUserIdAndReviewId(userId, reviewId)
                .orElseThrow(() -> new BusinessException(ErrorCode.REVIEW_LIKE_NOT_FOUND));

        reviewLikeRepository.delete(reviewLike);
    }

    @Transactional
    public void bookmarkReview(
            Long userId,
            Long reviewId
    ) {
        User user = getUser(userId);
        Review review = getPublicReview(reviewId);

        if (reviewBookmarkRepository.existsByUserIdAndReviewId(userId, reviewId)) {
            throw new BusinessException(ErrorCode.DUPLICATE_REVIEW_BOOKMARK);
        }

        ReviewBookmark bookmark = ReviewBookmark.builder()
                .user(user)
                .review(review)
                .build();

        reviewBookmarkRepository.save(bookmark);
    }

    @Transactional
    public void unbookmarkReview(
            Long userId,
            Long reviewId
    ) {
        ReviewBookmark bookmark = reviewBookmarkRepository.findByUserIdAndReviewId(userId, reviewId)
                .orElseThrow(() -> new BusinessException(ErrorCode.REVIEW_BOOKMARK_NOT_FOUND));

        reviewBookmarkRepository.delete(bookmark);
    }

    public PageResponse<ReviewBookmarkResponse> getMyBookmarks(
            Long userId,
            Pageable pageable
    ) {
        Page<ReviewBookmarkResponse> page = reviewBookmarkRepository.findAllByUserId(userId, pageable)
                .map(ReviewBookmarkResponse::from);

        return PageResponse.from(page);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    private Review getPublicReview(Long reviewId) {
        return reviewRepository.findByIdAndVisibility(reviewId, ReviewVisibility.PUBLIC)
                .orElseThrow(() -> new BusinessException(ErrorCode.REVIEW_NOT_FOUND));
    }
}