package com.artlog.domain.exhibition.entity;

import com.artlog.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "exhibitions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Exhibition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 전시 기록을 작성한 사용자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 전시명
    @Column(nullable = false, length = 100)
    private String title;

    // 미술관/전시장 이름
    @Column(nullable = false, length = 100)
    private String museumName;

    // 전시장 위치
    @Column(length = 255)
    private String location;

    // 전시 시작일
    private LocalDate startDate;

    // 전시 종료일
    private LocalDate endDate;

    // 실제 관람일
    private LocalDate visitDate;

    // 전시 포스터 이미지 URL
    @Column(length = 500)
    private String posterImageUrl;

    // 전시 메모
    @Column(columnDefinition = "TEXT")
    private String memo;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    private Exhibition(
            User user,
            String title,
            String museumName,
            String location,
            LocalDate startDate,
            LocalDate endDate,
            LocalDate visitDate,
            String posterImageUrl,
            String memo
    ) {
        this.user = user;
        this.title = title;
        this.museumName = museumName;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.visitDate = visitDate;
        this.posterImageUrl = posterImageUrl;
        this.memo = memo;
    }

    public void update(
            String title,
            String museumName,
            String location,
            LocalDate startDate,
            LocalDate endDate,
            LocalDate visitDate,
            String posterImageUrl,
            String memo
    ) {
        this.title = title;
        this.museumName = museumName;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.visitDate = visitDate;
        this.posterImageUrl = posterImageUrl;
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