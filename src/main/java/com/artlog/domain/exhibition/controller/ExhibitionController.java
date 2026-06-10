package com.artlog.domain.exhibition.controller;

import com.artlog.domain.exhibition.dto.request.ExhibitionCreateRequest;
import com.artlog.domain.exhibition.dto.request.ExhibitionSearchRequest;
import com.artlog.domain.exhibition.dto.request.ExhibitionUpdateRequest;
import com.artlog.domain.exhibition.dto.response.ExhibitionResponse;
import com.artlog.domain.exhibition.dto.response.ExhibitionSimpleResponse;
import com.artlog.domain.exhibition.service.ExhibitionService;
import com.artlog.global.response.ApiResponse;
import com.artlog.global.response.SuccessCode;
import com.artlog.global.security.user.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Exhibition", description = "전시 기록 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exhibitions")
public class ExhibitionController {

    private final ExhibitionService exhibitionService;

    @Operation(summary = "전시 기록 등록", description = "현재 로그인한 사용자의 전시 기록을 등록합니다.")
    @PostMapping
    public ApiResponse<ExhibitionResponse> createExhibition(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody ExhibitionCreateRequest request
    ) {
        ExhibitionResponse response = exhibitionService.createExhibition(
                userDetails.getUserId(),
                request
        );

        return ApiResponse.success(SuccessCode.EXHIBITION_CREATE_SUCCESS, response);
    }

    @Operation(summary = "내 전시 기록 목록 조회", description = "현재 로그인한 사용자의 전시 기록 목록을 조회합니다.")
    @GetMapping
    public ApiResponse<List<ExhibitionSimpleResponse>> getMyExhibitions(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        List<ExhibitionSimpleResponse> response = exhibitionService.getMyExhibitions(
                userDetails.getUserId()
        );

        return ApiResponse.success(SuccessCode.EXHIBITION_LIST_SUCCESS, response);
    }

    @Operation(summary = "전시 기록 상세 조회", description = "현재 로그인한 사용자의 특정 전시 기록을 상세 조회합니다.")
    @GetMapping("/{exhibitionId}")
    public ApiResponse<ExhibitionResponse> getExhibition(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long exhibitionId
    ) {
        ExhibitionResponse response = exhibitionService.getExhibition(
                userDetails.getUserId(),
                exhibitionId
        );

        return ApiResponse.success(SuccessCode.EXHIBITION_DETAIL_SUCCESS, response);
    }

    @Operation(summary = "전시 기록 수정", description = "현재 로그인한 사용자의 전시 기록을 수정합니다.")
    @PutMapping("/{exhibitionId}")
    public ApiResponse<ExhibitionResponse> updateExhibition(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long exhibitionId,
            @Valid @RequestBody ExhibitionUpdateRequest request
    ) {
        ExhibitionResponse response = exhibitionService.updateExhibition(
                userDetails.getUserId(),
                exhibitionId,
                request
        );

        return ApiResponse.success(SuccessCode.EXHIBITION_UPDATE_SUCCESS, response);
    }

    @Operation(summary = "전시 기록 삭제", description = "현재 로그인한 사용자의 전시 기록을 삭제합니다.")
    @DeleteMapping("/{exhibitionId}")
    public ApiResponse<Void> deleteExhibition(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long exhibitionId
    ) {
        exhibitionService.deleteExhibition(
                userDetails.getUserId(),
                exhibitionId
        );

        return ApiResponse.success(SuccessCode.EXHIBITION_DELETE_SUCCESS);
    }

    @Operation(summary = "전시 기록 검색", description = "현재 로그인한 사용자의 전시 기록을 조건에 따라 검색합니다.")
    @GetMapping("/search")
    public ApiResponse<List<ExhibitionSimpleResponse>> searchExhibitions(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String museumName,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) LocalDate visitFrom,
            @RequestParam(required = false) LocalDate visitTo
    ) {
        ExhibitionSearchRequest request = new ExhibitionSearchRequest(
                keyword,
                museumName,
                location,
                visitFrom,
                visitTo
        );

        List<ExhibitionSimpleResponse> response = exhibitionService.searchExhibitions(
                userDetails.getUserId(),
                request
        );

        return ApiResponse.success(SuccessCode.EXHIBITION_LIST_SUCCESS, response);
    }
}