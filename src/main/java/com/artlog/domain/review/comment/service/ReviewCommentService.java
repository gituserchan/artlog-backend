package com.artlog.domain.review.comment.service;

import com.artlog.domain.review.comment.dto.request.ReviewCommentCreateRequest;
import com.artlog.domain.review.comment.dto.response.ReviewCommentResponse;
import com.artlog.domain.review.comment.entity.ReviewComment;
import com.artlog.domain.review.comment.repository.ReviewCommentRepository;
import com.artlog.domain.review.entity.Review;
import com.artlog.domain.review.repository.ReviewRepository;
import com.artlog.domain.review.type.ReviewVisibility;
import com.artlog.domain.user.entity.User;
import com.artlog.domain.user.repository.UserRepository;
import com.artlog.global.exception.BusinessException;
import com.artlog.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewCommentService {

    private final ReviewRepository reviewRepository;
    private final ReviewCommentRepository reviewCommentRepository;
    private final UserRepository userRepository;

    public List<ReviewCommentResponse> getComments(
            Long reviewId,
            Long currentUserId
    ) {
        validatePublicReview(reviewId);

        return reviewCommentRepository.findAllByReviewId(
                        reviewId,
                        Sort.by(Sort.Direction.ASC, "createdAt")
                )
                .stream()
                .map(comment -> ReviewCommentResponse.from(comment, currentUserId))
                .toList();
    }

    @Transactional
    public ReviewCommentResponse createComment(
            Long reviewId,
            Long userId,
            ReviewCommentCreateRequest request
    ) {
        if (userId == null) {
            throw new AccessDeniedException("로그인이 필요합니다.");
        }

        Review review = reviewRepository.findByIdAndVisibility(
                        reviewId,
                        ReviewVisibility.PUBLIC
                )
                .orElseThrow(() -> new BusinessException(ErrorCode.REVIEW_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AccessDeniedException("로그인이 필요합니다."));

        ReviewComment comment = ReviewComment.builder()
                .review(review)
                .user(user)
                .content(request.content().trim())
                .build();

        ReviewComment savedComment = reviewCommentRepository.save(comment);

        return ReviewCommentResponse.from(savedComment, userId);
    }

    @Transactional
    public void deleteComment(
            Long reviewId,
            Long commentId,
            Long userId
    ) {
        if (userId == null) {
            throw new AccessDeniedException("로그인이 필요합니다.");
        }

        validatePublicReview(reviewId);

        ReviewComment comment = reviewCommentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.REVIEW_NOT_FOUND));

        if (!comment.getReview().getId().equals(reviewId)) {
            throw new BusinessException(ErrorCode.REVIEW_NOT_FOUND);
        }

        if (!comment.isWrittenBy(userId)) {
            throw new AccessDeniedException("댓글 작성자만 삭제할 수 있습니다.");
        }

        reviewCommentRepository.delete(comment);
    }

    private void validatePublicReview(Long reviewId) {
        reviewRepository.findByIdAndVisibility(
                        reviewId,
                        ReviewVisibility.PUBLIC
                )
                .orElseThrow(() -> new BusinessException(ErrorCode.REVIEW_NOT_FOUND));
    }
}