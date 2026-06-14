package com.artlog.domain.artwork.repository;

import com.artlog.domain.artwork.entity.Artwork;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ArtworkRepository extends JpaRepository<Artwork, Long> {

    Page<Artwork> findAllByExhibitionId(Long exhibitionId, Pageable pageable);

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
            """)
    Page<Artwork> searchArtworks(
            @Param("userId") Long userId,
            @Param("keyword") String keyword,
            @Param("artistName") String artistName,
            @Param("productionYear") String productionYear,
            @Param("medium") String medium,
            @Param("exhibitionId") Long exhibitionId,
            Pageable pageable
    );

    @Query("""
        SELECT COUNT(a.id)
        FROM Artwork a
        JOIN a.exhibition e
        WHERE e.user.id = :userId
        """)
    long countByUserId(@Param("userId") Long userId);
}