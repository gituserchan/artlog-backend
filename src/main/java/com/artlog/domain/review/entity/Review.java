package com.artlog.domain.review.entity;

import com.artlog.domain.artwork.entity.Artwork;
import com.artlog.domain.exhibition.entity.Exhibition;
import com.artlog.domain.review.type.ReviewType;
import com.artlog.domain.review.type.ReviewVisibility;
import com.artlog.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "reviews")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 감상 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 감상이 속한 전시
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exhibition_id", nullable = false)
    private Exhibition exhibition;

    // 작품 감상일 경우에만 값 존재
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artwork_id")
    private Artwork artwork;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReviewType reviewType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReviewVisibility visibility;

    // 감상 제목
    @Column(nullable = false, length = 100)
    private String title;

    // 감상 본문
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    // 평점, 1~5
    @Column(nullable = false)
    private Integer rating;

    // 감정 태그, 예: 강렬함, 낯섦, 따뜻함, 불편함
    @Column(length = 100)
    private String emotionTag;

    // 키워드, 예: 레디메이드, 설치미술, 관람동선
    @Column(length = 255)
    private String keywords;

    // 다시 보고 싶은지 여부
    @Column(nullable = false)
    private Boolean wantToRevisit;

    // 대표 이미지 URL, 나중에 파일 업로드 붙일 예정
    @Column(length = 500)
    private String imageUrl;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    private Review(
            User user,
            Exhibition exhibition,
            Artwork artwork,
            ReviewType reviewType,
            ReviewVisibility visibility,
            String title,
            String content,
            Integer rating,
            String emotionTag,
            String keywords,
            Boolean wantToRevisit,
            String imageUrl
    ) {
        this.user = user;
        this.exhibition = exhibition;
        this.artwork = artwork;
        this.reviewType = reviewType;
        this.visibility = visibility;
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.emotionTag = emotionTag;
        this.keywords = keywords;
        this.wantToRevisit = wantToRevisit;
        this.imageUrl = imageUrl;
    }

    public void update(
            ReviewVisibility visibility,
            String title,
            String content,
            Integer rating,
            String emotionTag,
            String keywords,
            Boolean wantToRevisit,
            String imageUrl
    ) {
        this.visibility = visibility;
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.emotionTag = emotionTag;
        this.keywords = keywords;
        this.wantToRevisit = wantToRevisit;
        this.imageUrl = imageUrl;
    }

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();

        this.createdAt = now;
        this.updatedAt = now;

        if (this.visibility == null) {
            this.visibility = ReviewVisibility.PRIVATE;
        }

        if (this.wantToRevisit == null) {
            this.wantToRevisit = false;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}