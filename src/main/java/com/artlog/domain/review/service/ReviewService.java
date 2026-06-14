package com.artlog.domain.review.service;

import com.artlog.domain.artwork.entity.Artwork;
import com.artlog.domain.artwork.repository.ArtworkRepository;
import com.artlog.domain.exhibition.entity.Exhibition;
import com.artlog.domain.exhibition.repository.ExhibitionRepository;
import com.artlog.domain.review.dto.request.ArtworkReviewCreateRequest;
import com.artlog.domain.review.dto.request.ExhibitionReviewCreateRequest;
import com.artlog.domain.review.dto.request.ReviewSearchRequest;
import com.artlog.domain.review.dto.request.ReviewUpdateRequest;
import com.artlog.domain.review.dto.response.ReviewResponse;
import com.artlog.domain.review.dto.response.ReviewSimpleResponse;
import com.artlog.domain.review.entity.Review;
import com.artlog.domain.review.repository.ReviewRepository;
import com.artlog.domain.review.type.ReviewType;
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

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ExhibitionRepository exhibitionRepository;
    private final ArtworkRepository artworkRepository;

    @Transactional
    public ReviewResponse createExhibitionReview(
            Long userId,
            Long exhibitionId,
            ExhibitionReviewCreateRequest request
    ) {
        User user = getUser(userId);
        Exhibition exhibition = getMyExhibition(userId, exhibitionId);

        Review review = Review.builder()
                .user(user)
                .exhibition(exhibition)
                .artwork(null)
                .reviewType(ReviewType.EXHIBITION)
                .visibility(getVisibilityOrDefault(request.visibility()))
                .title(request.title())
                .content(request.content())
                .rating(request.rating())
                .emotionTag(request.emotionTag())
                .keywords(request.keywords())
                .wantToRevisit(getWantToRevisitOrDefault(request.wantToRevisit()))
                .imageUrl(request.imageUrl())
                .build();

        Review savedReview = reviewRepository.save(review);

        return ReviewResponse.from(savedReview);
    }

    @Transactional
    public ReviewResponse createArtworkReview(
            Long userId,
            Long exhibitionId,
            Long artworkId,
            ArtworkReviewCreateRequest request
    ) {
        User user = getUser(userId);
        Exhibition exhibition = getMyExhibition(userId, exhibitionId);
        Artwork artwork = getArtworkInExhibition(exhibitionId, artworkId);

        Review review = Review.builder()
                .user(user)
                .exhibition(exhibition)
                .artwork(artwork)
                .reviewType(ReviewType.ARTWORK)
                .visibility(getVisibilityOrDefault(request.visibility()))
                .title(request.title())
                .content(request.content())
                .rating(request.rating())
                .emotionTag(request.emotionTag())
                .keywords(request.keywords())
                .wantToRevisit(getWantToRevisitOrDefault(request.wantToRevisit()))
                .imageUrl(request.imageUrl())
                .build();

        Review savedReview = reviewRepository.save(review);

        return ReviewResponse.from(savedReview);
    }

    public PageResponse<ReviewSimpleResponse> getMyReviews(
            Long userId,
            ReviewType reviewType,
            Pageable pageable
    ) {
        Page<Review> reviews;

        if (reviewType == null) {
            reviews = reviewRepository.findAllByUserId(userId, pageable);
        } else {
            reviews = reviewRepository.findAllByUserIdAndReviewType(
                    userId,
                    reviewType,
                    pageable
            );
        }

        Page<ReviewSimpleResponse> page = reviews.map(ReviewSimpleResponse::from);

        return PageResponse.from(page);
    }

    public PageResponse<ReviewSimpleResponse> getReviewsByExhibition(
            Long userId,
            Long exhibitionId,
            Pageable pageable
    ) {
        getMyExhibition(userId, exhibitionId);

        Page<ReviewSimpleResponse> page = reviewRepository.findAllByExhibitionIdAndUserId(
                        exhibitionId,
                        userId,
                        pageable
                )
                .map(ReviewSimpleResponse::from);

        return PageResponse.from(page);
    }

    public PageResponse<ReviewSimpleResponse> getReviewsByArtwork(
            Long userId,
            Long exhibitionId,
            Long artworkId,
            Pageable pageable
    ) {
        getMyExhibition(userId, exhibitionId);
        getArtworkInExhibition(exhibitionId, artworkId);

        Page<ReviewSimpleResponse> page = reviewRepository.findAllByArtworkIdAndUserId(
                        artworkId,
                        userId,
                        pageable
                )
                .map(ReviewSimpleResponse::from);

        return PageResponse.from(page);
    }

    public ReviewResponse getReview(
            Long userId,
            Long reviewId
    ) {
        Review review = getMyReview(userId, reviewId);

        return ReviewResponse.from(review);
    }

    @Transactional
    public ReviewResponse updateReview(
            Long userId,
            Long reviewId,
            ReviewUpdateRequest request
    ) {
        Review review = getMyReview(userId, reviewId);

        review.update(
                getVisibilityOrDefault(request.visibility()),
                request.title(),
                request.content(),
                request.rating(),
                request.emotionTag(),
                request.keywords(),
                getWantToRevisitOrDefault(request.wantToRevisit()),
                request.imageUrl()
        );

        return ReviewResponse.from(review);
    }

    @Transactional
    public void deleteReview(
            Long userId,
            Long reviewId
    ) {
        Review review = getMyReview(userId, reviewId);

        reviewRepository.delete(review);
    }

    public PageResponse<ReviewSimpleResponse> searchReviews(
            Long userId,
            ReviewSearchRequest request,
            Pageable pageable
    ) {
        Page<ReviewSimpleResponse> page = reviewRepository.searchReviews(
                        userId,
                        normalize(request.keyword()),
                        request.reviewType(),
                        request.visibility(),
                        request.minRating(),
                        request.maxRating(),
                        normalize(request.emotionTag()),
                        normalize(request.keywords()),
                        request.wantToRevisit(),
                        request.createdFrom() == null ? null : request.createdFrom().atStartOfDay(),
                        request.createdTo() == null ? null : request.createdTo().atTime(LocalTime.MAX),
                        pageable
                )
                .map(ReviewSimpleResponse::from);

        return PageResponse.from(page);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    private Exhibition getMyExhibition(
            Long userId,
            Long exhibitionId
    ) {
        return exhibitionRepository.findByIdAndUserId(exhibitionId, userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.EXHIBITION_NOT_FOUND));
    }

    private Artwork getArtworkInExhibition(
            Long exhibitionId,
            Long artworkId
    ) {
        return artworkRepository.findByIdAndExhibitionId(artworkId, exhibitionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ARTWORK_NOT_FOUND));
    }

    private Review getMyReview(
            Long userId,
            Long reviewId
    ) {
        return reviewRepository.findByIdAndUserId(reviewId, userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.REVIEW_NOT_FOUND));
    }

    private ReviewVisibility getVisibilityOrDefault(ReviewVisibility visibility) {
        if (visibility == null) {
            return ReviewVisibility.PRIVATE;
        }

        return visibility;
    }

    private Boolean getWantToRevisitOrDefault(Boolean wantToRevisit) {
        if (wantToRevisit == null) {
            return false;
        }

        return wantToRevisit;
    }

    private String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return value.trim();
    }
}