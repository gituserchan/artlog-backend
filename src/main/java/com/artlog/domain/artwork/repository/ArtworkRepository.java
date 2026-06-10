package com.artlog.domain.artwork.repository;

import com.artlog.domain.artwork.entity.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArtworkRepository extends JpaRepository<Artwork, Long> {

    List<Artwork> findAllByExhibitionIdOrderByCreatedAtDesc(Long exhibitionId);

    Optional<Artwork> findByIdAndExhibitionId(Long artworkId, Long exhibitionId);

    @Query("""
            SELECT a
            FROM Artwork a
            JOIN a.exhibition e
            WHERE e.user.id = :userId
              AND (
                    :exhibitionId IS NULL
                    OR e.id = :exhibitionId
                  )
              AND (
                    :keyword IS NULL
                    OR a.title LIKE CONCAT('%', :keyword, '%')
                    OR a.artistName LIKE CONCAT('%', :keyword, '%')
                    OR a.productionYear LIKE CONCAT('%', :keyword, '%')
                    OR a.medium LIKE CONCAT('%', :keyword, '%')
                    OR a.memo LIKE CONCAT('%', :keyword, '%')
                  )
              AND (
                    :artistName IS NULL
                    OR a.artistName LIKE CONCAT('%', :artistName, '%')
                  )
              AND (
                    :productionYear IS NULL
                    OR a.productionYear LIKE CONCAT('%', :productionYear, '%')
                  )
              AND (
                    :medium IS NULL
                    OR a.medium LIKE CONCAT('%', :medium, '%')
                  )
            ORDER BY a.createdAt DESC
            """)
    List<Artwork> searchArtworks(
            @Param("userId") Long userId,
            @Param("keyword") String keyword,
            @Param("artistName") String artistName,
            @Param("productionYear") String productionYear,
            @Param("medium") String medium,
            @Param("exhibitionId") Long exhibitionId
    );
}