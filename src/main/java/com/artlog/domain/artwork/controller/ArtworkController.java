package com.artlog.domain.artwork.controller;

import com.artlog.domain.artwork.dto.request.ArtworkCreateRequest;
import com.artlog.domain.artwork.dto.request.ArtworkUpdateRequest;
import com.artlog.domain.artwork.dto.response.ArtworkResponse;
import com.artlog.domain.artwork.dto.response.ArtworkSimpleResponse;
import com.artlog.domain.artwork.service.ArtworkService;
import com.artlog.global.response.ApiResponse;
import com.artlog.global.response.SuccessCode;
import com.artlog.global.security.user.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Artwork", description = "작품 기록 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exhibitions/{exhibitionId}/artworks")
public class ArtworkController {

    private final ArtworkService artworkService;

    @Operation(summary = "작품 기록 등록", description = "현재 로그인한 사용자의 특정 전시에 작품 기록을 등록합니다.")
    @PostMapping
    public ApiResponse<ArtworkResponse> createArtwork(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long exhibitionId,
            @Valid @RequestBody ArtworkCreateRequest request
    ) {
        ArtworkResponse response = artworkService.createArtwork(
                userDetails.getUserId(),
                exhibitionId,
                request
        );

        return ApiResponse.success(SuccessCode.ARTWORK_CREATE_SUCCESS, response);
    }

    @Operation(summary = "작품 기록 목록 조회", description = "현재 로그인한 사용자의 특정 전시에 등록된 작품 목록을 조회합니다.")
    @GetMapping
    public ApiResponse<List<ArtworkSimpleResponse>> getArtworks(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long exhibitionId
    ) {
        List<ArtworkSimpleResponse> response = artworkService.getArtworks(
                userDetails.getUserId(),
                exhibitionId
        );

        return ApiResponse.success(SuccessCode.ARTWORK_LIST_SUCCESS, response);
    }

    @Operation(summary = "작품 기록 상세 조회", description = "현재 로그인한 사용자의 특정 전시에 등록된 작품을 상세 조회합니다.")
    @GetMapping("/{artworkId}")
    public ApiResponse<ArtworkResponse> getArtwork(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long exhibitionId,
            @PathVariable Long artworkId
    ) {
        ArtworkResponse response = artworkService.getArtwork(
                userDetails.getUserId(),
                exhibitionId,
                artworkId
        );

        return ApiResponse.success(SuccessCode.ARTWORK_DETAIL_SUCCESS, response);
    }

    @Operation(summary = "작품 기록 수정", description = "현재 로그인한 사용자의 특정 전시에 등록된 작품 기록을 수정합니다.")
    @PutMapping("/{artworkId}")
    public ApiResponse<ArtworkResponse> updateArtwork(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long exhibitionId,
            @PathVariable Long artworkId,
            @Valid @RequestBody ArtworkUpdateRequest request
    ) {
        ArtworkResponse response = artworkService.updateArtwork(
                userDetails.getUserId(),
                exhibitionId,
                artworkId,
                request
        );

        return ApiResponse.success(SuccessCode.ARTWORK_UPDATE_SUCCESS, response);
    }

    @Operation(summary = "작품 기록 삭제", description = "현재 로그인한 사용자의 특정 전시에 등록된 작품 기록을 삭제합니다.")
    @DeleteMapping("/{artworkId}")
    public ApiResponse<Void> deleteArtwork(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long exhibitionId,
            @PathVariable Long artworkId
    ) {
        artworkService.deleteArtwork(
                userDetails.getUserId(),
                exhibitionId,
                artworkId
        );

        return ApiResponse.success(SuccessCode.ARTWORK_DELETE_SUCCESS);
    }
}