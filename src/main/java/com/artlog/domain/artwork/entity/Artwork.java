package com.artlog.domain.artwork.entity;

import com.artlog.domain.exhibition.entity.Exhibition;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "artworks")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Artwork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 작품이 속한 전시
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exhibition_id", nullable = false)
    private Exhibition exhibition;

    // 작품명
    @Column(nullable = false, length = 150)
    private String title;

    // 작가명
    @Column(length = 100)
    private String artistName;

    // 제작연도
    @Column(length = 50)
    private String productionYear;

    // 재료/매체
    @Column(length = 150)
    private String medium;

    // 작품 이미지 URL, 실제 업로드는 나중에 붙일 예정
    @Column(length = 500)
    private String imageUrl;

    // 작품 감상 메모
    @Column(columnDefinition = "TEXT")
    private String memo;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    private Artwork(
            Exhibition exhibition,
            String title,
            String artistName,
            String productionYear,
            String medium,
            String imageUrl,
            String memo
    ) {
        this.exhibition = exhibition;
        this.title = title;
        this.artistName = artistName;
        this.productionYear = productionYear;
        this.medium = medium;
        this.imageUrl = imageUrl;
        this.memo = memo;
    }

    public void update(
            String title,
            String artistName,
            String productionYear,
            String medium,
            String imageUrl,
            String memo
    ) {
        this.title = title;
        this.artistName = artistName;
        this.productionYear = productionYear;
        this.medium = medium;
        this.imageUrl = imageUrl;
        this.memo = memo;
    }

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();

        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}