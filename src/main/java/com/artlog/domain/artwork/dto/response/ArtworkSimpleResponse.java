package com.artlog.domain.artwork.dto.response;

import com.artlog.domain.artwork.entity.Artwork;

public record ArtworkSimpleResponse(
        Long artworkId,
        Long exhibitionId,
        String title,
        String artistName,
        String productionYear,
        String imageUrl
) {

    public static ArtworkSimpleResponse from(Artwork artwork) {
        return new ArtworkSimpleResponse(
                artwork.getId(),
                artwork.getExhibition().getId(),
                artwork.getTitle(),
                artwork.getArtistName(),
                artwork.getProductionYear(),
                artwork.getImageUrl()
        );
    }
}