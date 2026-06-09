package com.artlog.domain.artwork.dto.response;

import com.artlog.domain.artwork.entity.Artwork;

import java.time.LocalDateTime;

public record ArtworkResponse(
        Long artworkId,
        Long exhibitionId,
        String title,
        String artistName,
        String productionYear,
        String medium,
        String imageUrl,
        String memo,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static ArtworkResponse from(Artwork artwork) {
        return new ArtworkResponse(
                artwork.getId(),
                artwork.getExhibition().getId(),
                artwork.getTitle(),
                artwork.getArtistName(),
                artwork.getProductionYear(),
                artwork.getMedium(),
                artwork.getImageUrl(),
                artwork.getMemo(),
                artwork.getCreatedAt(),
                artwork.getUpdatedAt()
        );
    }
}