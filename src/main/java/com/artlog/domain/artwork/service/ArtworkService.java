package com.artlog.domain.artwork.service;

import com.artlog.domain.artwork.dto.request.ArtworkCreateRequest;
import com.artlog.domain.artwork.dto.request.ArtworkSearchRequest;
import com.artlog.domain.artwork.dto.request.ArtworkUpdateRequest;
import com.artlog.domain.artwork.dto.response.ArtworkResponse;
import com.artlog.domain.artwork.dto.response.ArtworkSimpleResponse;
import com.artlog.domain.artwork.entity.Artwork;
import com.artlog.domain.artwork.repository.ArtworkRepository;
import com.artlog.domain.exhibition.entity.Exhibition;
import com.artlog.domain.exhibition.repository.ExhibitionRepository;
import com.artlog.global.exception.BusinessException;
import com.artlog.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArtworkService {

    private final ArtworkRepository artworkRepository;
    private final ExhibitionRepository exhibitionRepository;

    @Transactional
    public ArtworkResponse createArtwork(
            Long userId,
            Long exhibitionId,
            ArtworkCreateRequest request
    ) {
        Exhibition exhibition = getMyExhibition(userId, exhibitionId);

        Artwork artwork = Artwork.builder()
                .exhibition(exhibition)
                .title(request.title())
                .artistName(request.artistName())
                .productionYear(request.productionYear())
                .medium(request.medium())
                .imageUrl(request.imageUrl())
                .memo(request.memo())
                .build();

        Artwork savedArtwork = artworkRepository.save(artwork);

        return ArtworkResponse.from(savedArtwork);
    }

    public List<ArtworkSimpleResponse> getArtworks(
            Long userId,
            Long exhibitionId
    ) {
        getMyExhibition(userId, exhibitionId);

        return artworkRepository.findAllByExhibitionIdOrderByCreatedAtDesc(exhibitionId)
                .stream()
                .map(ArtworkSimpleResponse::from)
                .toList();
    }

    public ArtworkResponse getArtwork(
            Long userId,
            Long exhibitionId,
            Long artworkId
    ) {
        getMyExhibition(userId, exhibitionId);

        Artwork artwork = getArtworkInExhibition(exhibitionId, artworkId);

        return ArtworkResponse.from(artwork);
    }

    @Transactional
    public ArtworkResponse updateArtwork(
            Long userId,
            Long exhibitionId,
            Long artworkId,
            ArtworkUpdateRequest request
    ) {
        getMyExhibition(userId, exhibitionId);

        Artwork artwork = getArtworkInExhibition(exhibitionId, artworkId);

        artwork.update(
                request.title(),
                request.artistName(),
                request.productionYear(),
                request.medium(),
                request.imageUrl(),
                request.memo()
        );

        return ArtworkResponse.from(artwork);
    }

    @Transactional
    public void deleteArtwork(
            Long userId,
            Long exhibitionId,
            Long artworkId
    ) {
        getMyExhibition(userId, exhibitionId);

        Artwork artwork = getArtworkInExhibition(exhibitionId, artworkId);

        artworkRepository.delete(artwork);
    }

    private Exhibition getMyExhibition(
            Long userId,
            Long exhibitionId
    ) {
        return exhibitionRepository.findByIdAndUserId(exhibitionId, userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.EXHIBITION_NOT_FOUND));
    }

    private Artwork getArtworkInExhibition(
            Long exhibitionId,
            Long artworkId
    ) {
        return artworkRepository.findByIdAndExhibitionId(artworkId, exhibitionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ARTWORK_NOT_FOUND));
    }

    public List<ArtworkSimpleResponse> searchArtworks(
            Long userId,
            ArtworkSearchRequest request
    ) {
        if (request.exhibitionId() != null) {
            getMyExhibition(userId, request.exhibitionId());
        }

        return artworkRepository.searchArtworks(
                        userId,
                        normalize(request.keyword()),
                        normalize(request.artistName()),
                        normalize(request.productionYear()),
                        normalize(request.medium()),
                        request.exhibitionId()
                )
                .stream()
                .map(ArtworkSimpleResponse::from)
                .toList();
    }

    private String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return value.trim();
    }
}