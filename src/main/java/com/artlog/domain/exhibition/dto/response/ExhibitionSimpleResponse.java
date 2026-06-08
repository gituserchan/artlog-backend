package com.artlog.domain.exhibition.dto.response;

import com.artlog.domain.exhibition.entity.Exhibition;

import java.time.LocalDate;

public record ExhibitionSimpleResponse(
        Long exhibitionId,
        String title,
        String museumName,
        String location,
        LocalDate visitDate,
        String posterImageUrl
) {

    public static ExhibitionSimpleResponse from(Exhibition exhibition) {
        return new ExhibitionSimpleResponse(
                exhibition.getId(),
                exhibition.getTitle(),
                exhibition.getMuseumName(),
                exhibition.getLocation(),
                exhibition.getVisitDate(),
                exhibition.getPosterImageUrl()
        );
    }
}