package com.artlog.domain.artwork.controller;

import com.artlog.domain.artwork.dto.request.ArtworkSearchRequest;
import com.artlog.domain.artwork.dto.response.ArtworkSimpleResponse;
import com.artlog.domain.artwork.service.ArtworkService;
import com.artlog.global.response.ApiResponse;
import com.artlog.global.response.SuccessCode;
import com.artlog.global.security.user.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Artwork Search", description = "작품 기록 검색 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/artworks")
public class ArtworkSearchController {

    private final ArtworkService artworkService;

    @Operation(summary = "작품 기록 검색", description = "현재 로그인한 사용자의 작품 기록을 조건에 따라 검색합니다.")
    @GetMapping("/search")
    public ApiResponse<List<ArtworkSimpleResponse>> searchArtworks(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ModelAttribute ArtworkSearchRequest request
    ) {
        List<ArtworkSimpleResponse> response = artworkService.searchArtworks(
                userDetails.getUserId(),
                request
        );

        return ApiResponse.success(SuccessCode.ARTWORK_LIST_SUCCESS, response);
    }
}