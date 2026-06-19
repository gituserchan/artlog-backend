package com.artlog.domain.review.comment.repository;

import com.artlog.domain.review.comment.entity.ReviewComment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewCommentRepository extends JpaRepository<ReviewComment, Long> {

    List<ReviewComment> findAllByReviewId(Long reviewId, Sort sort);
}