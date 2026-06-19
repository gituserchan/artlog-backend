package com.artlog.domain.review.comment.entity;

import com.artlog.domain.review.entity.Review;
import com.artlog.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "review_comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 댓글이 달린 공개 감상
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    // 댓글 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Builder
    private ReviewComment(
            Review review,
            User user,
            String content
    ) {
        this.review = review;
        this.user = user;
        this.content = content;
    }

    public boolean isWrittenBy(Long userId) {
        return this.user.getId().equals(userId);
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}