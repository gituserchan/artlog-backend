package com.artlog.domain.exhibition.dto.response;

import com.artlog.domain.exhibition.entity.Exhibition;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ExhibitionResponse(
        Long exhibitionId,
        String title,
        String museumName,
        String location,
        LocalDate startDate,
        LocalDate endDate,
        LocalDate visitDate,
        String posterImageUrl,
        String memo,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static ExhibitionResponse from(Exhibition exhibition) {
        return new ExhibitionResponse(
                exhibition.getId(),
                exhibition.getTitle(),
                exhibition.getMuseumName(),
                exhibition.getLocation(),
                exhibition.getStartDate(),
                exhibition.getEndDate(),
                exhibition.getVisitDate(),
                exhibition.getPosterImageUrl(),
                exhibition.getMemo(),
                exhibition.getCreatedAt(),
                exhibition.getUpdatedAt()
        );
    }
}