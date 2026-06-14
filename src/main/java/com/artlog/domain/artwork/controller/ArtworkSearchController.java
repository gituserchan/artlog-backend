package com.artlog.domain.artwork.controller;

import com.artlog.domain.artwork.dto.request.ArtworkSearchRequest;
import com.artlog.domain.artwork.dto.response.ArtworkSimpleResponse;
import com.artlog.domain.artwork.service.ArtworkService;
import com.artlog.global.response.ApiResponse;
import com.artlog.global.response.PageResponse;
import com.artlog.global.response.SuccessCode;
import com.artlog.global.security.user.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Artwork Search", description = "작품 기록 검색 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/artworks")
public class ArtworkSearchController {

    private final ArtworkService artworkService;

    @Operation(summary = "작품 기록 검색", description = "현재 로그인한 사용자의 작품 기록을 조건에 따라 페이징하여 검색합니다.")
    @GetMapping("/search")
    public ApiResponse<PageResponse<ArtworkSimpleResponse>> searchArtworks(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String artistName,
            @RequestParam(required = false) String productionYear,
            @RequestParam(required = false) String medium,
            @RequestParam(required = false) Long exhibitionId,
            @ParameterObject
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        ArtworkSearchRequest request = new ArtworkSearchRequest(
                keyword,
                artistName,
                productionYear,
                medium,
                exhibitionId
        );

        PageResponse<ArtworkSimpleResponse> response = artworkService.searchArtworks(
                userDetails.getUserId(),
                request,
                pageable
        );

        return ApiResponse.success(SuccessCode.ARTWORK_LIST_SUCCESS, response);
    }
}