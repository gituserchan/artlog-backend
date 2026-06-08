package com.artlog.domain.exhibition.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ExhibitionCreateRequest(

        @NotBlank(message = "전시명은 필수입니다.")
        @Size(max = 100, message = "전시명은 100자 이하로 입력해주세요.")
        String title,

        @NotBlank(message = "미술관/전시장 이름은 필수입니다.")
        @Size(max = 100, message = "미술관/전시장 이름은 100자 이하로 입력해주세요.")
        String museumName,

        @Size(max = 255, message = "위치는 255자 이하로 입력해주세요")
        String location,

        LocalDate startDate,

        LocalDate endDate,

        LocalDate visitDate,

        @Size(max=500, message = "이미지 URL은 500자 이하로 입력해주세요.")
        String posterImageUrl,

        String memo
) {
}
