package com.artlog.domain.artwork.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ArtworkUpdateRequest(

        @NotBlank(message = "작품명은 필수입니다.")
        @Size(max = 150, message = "작품명은 150자 이하로 입력해주세요.")
        String title,

        @Size(max = 100, message = "작가명은 100자 이하로 입력해주세요.")
        String artistName,

        @Size(max = 50, message = "제작연도는 50자 이하로 입력해주세요.")
        String productionYear,

        @Size(max = 150, message = "재료/매체는 150자 이하로 입력해주세요.")
        String medium,

        @Size(max = 500, message = "이미지 URL은 500자 이하로 입력해주세요.")
        String imageUrl,

        String memo
) {
}