package com.artlog.domain.statistics.controller;

import com.artlog.domain.statistics.dto.response.MonthlyVisitResponse;
import com.artlog.domain.statistics.dto.response.RatingDistributionResponse;
import com.artlog.domain.statistics.dto.response.StatisticsSummaryResponse;
import com.artlog.domain.statistics.dto.response.TopEmotionTagResponse;
import com.artlog.domain.statistics.dto.response.TopMuseumResponse;
import com.artlog.domain.statistics.service.StatisticsService;
import com.artlog.global.response.ApiResponse;
import com.artlog.global.response.SuccessCode;
import com.artlog.global.security.user.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Statistics", description = "통계 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Operation(summary = "통계 요약 조회", description = "현재 로그인한 사용자의 기록 개수와 평균 평점을 조회합니다.")
    @GetMapping("/summary")
    public ApiResponse<StatisticsSummaryResponse> getSummary(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        StatisticsSummaryResponse response = statisticsService.getSummary(userDetails.getUserId());

        return ApiResponse.success(SuccessCode.STATISTICS_SUMMARY_SUCCESS, response);
    }

    @Operation(summary = "월별 관람 통계 조회", description = "현재 로그인한 사용자의 월별 전시 관람 횟수를 조회합니다.")
    @GetMapping("/monthly-visits")
    public ApiResponse<List<MonthlyVisitResponse>> getMonthlyVisits(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        List<MonthlyVisitResponse> response = statisticsService.getMonthlyVisits(userDetails.getUserId());

        return ApiResponse.success(SuccessCode.MONTHLY_VISITS_SUCCESS, response);
    }

    @Operation(summary = "자주 방문한 미술관 통계 조회", description = "현재 로그인한 사용자가 자주 방문한 미술관을 조회합니다.")
    @GetMapping("/top-museums")
    public ApiResponse<List<TopMuseumResponse>> getTopMuseums(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        List<TopMuseumResponse> response = statisticsService.getTopMuseums(userDetails.getUserId());

        return ApiResponse.success(SuccessCode.TOP_MUSEUMS_SUCCESS, response);
    }

    @Operation(summary = "평점 분포 통계 조회", description = "현재 로그인한 사용자의 감상 기록 평점 분포를 조회합니다.")
    @GetMapping("/rating-distribution")
    public ApiResponse<List<RatingDistributionResponse>> getRatingDistribution(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        List<RatingDistributionResponse> response = statisticsService.getRatingDistribution(userDetails.getUserId());

        return ApiResponse.success(SuccessCode.RATING_DISTRIBUTION_SUCCESS, response);
    }

    @Operation(summary = "감정 태그 통계 조회", description = "현재 로그인한 사용자가 자주 사용한 감정 태그를 조회합니다.")
    @GetMapping("/top-emotion-tags")
    public ApiResponse<List<TopEmotionTagResponse>> getTopEmotionTags(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        List<TopEmotionTagResponse> response = statisticsService.getTopEmotionTags(userDetails.getUserId());

        return ApiResponse.success(SuccessCode.TOP_EMOTION_TAGS_SUCCESS, response);
    }
}