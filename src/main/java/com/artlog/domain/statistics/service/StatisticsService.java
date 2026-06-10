package com.artlog.domain.statistics.service;

import com.artlog.domain.artwork.repository.ArtworkRepository;
import com.artlog.domain.exhibition.repository.ExhibitionRepository;
import com.artlog.domain.review.repository.ReviewRepository;
import com.artlog.domain.statistics.dto.response.MonthlyVisitResponse;
import com.artlog.domain.statistics.dto.response.RatingDistributionResponse;
import com.artlog.domain.statistics.dto.response.StatisticsSummaryResponse;
import com.artlog.domain.statistics.dto.response.TopEmotionTagResponse;
import com.artlog.domain.statistics.dto.response.TopMuseumResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatisticsService {

    private final ExhibitionRepository exhibitionRepository;
    private final ArtworkRepository artworkRepository;
    private final ReviewRepository reviewRepository;

    public StatisticsSummaryResponse getSummary(Long userId) {
        long exhibitionCount = exhibitionRepository.countByUserId(userId);
        long artworkCount = artworkRepository.countByUserId(userId);
        long reviewCount = reviewRepository.countByUserId(userId);
        Double averageRating = reviewRepository.averageRatingByUserId(userId);

        return new StatisticsSummaryResponse(
                exhibitionCount,
                artworkCount,
                reviewCount,
                averageRating
        );
    }

    public List<MonthlyVisitResponse> getMonthlyVisits(Long userId) {
        return exhibitionRepository.countMonthlyVisits(userId)
                .stream()
                .map(row -> new MonthlyVisitResponse(
                        String.valueOf(row[0]),
                        ((Number) row[1]).longValue()
                ))
                .toList();
    }

    public List<TopMuseumResponse> getTopMuseums(Long userId) {
        return exhibitionRepository.countTopMuseums(userId)
                .stream()
                .map(row -> new TopMuseumResponse(
                        String.valueOf(row[0]),
                        ((Number) row[1]).longValue()
                ))
                .toList();
    }

    public List<RatingDistributionResponse> getRatingDistribution(Long userId) {
        return reviewRepository.countRatingDistribution(userId)
                .stream()
                .map(row -> new RatingDistributionResponse(
                        ((Number) row[0]).intValue(),
                        ((Number) row[1]).longValue()
                ))
                .toList();
    }

    public List<TopEmotionTagResponse> getTopEmotionTags(Long userId) {
        return reviewRepository.countTopEmotionTags(userId)
                .stream()
                .map(row -> new TopEmotionTagResponse(
                        String.valueOf(row[0]),
                        ((Number) row[1]).longValue()
                ))
                .toList();
    }
}