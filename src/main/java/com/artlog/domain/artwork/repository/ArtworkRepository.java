package com.artlog.domain.artwork.repository;

import com.artlog.domain.artwork.entity.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArtworkRepository extends JpaRepository<Artwork, Long> {

    List<Artwork> findAllByExhibitionIdOrderByCreatedAtDesc(Long exhibitionId);

    Optional<Artwork> findByIdAndExhibitionId(Long artworkId, Long exhibitionId);
}