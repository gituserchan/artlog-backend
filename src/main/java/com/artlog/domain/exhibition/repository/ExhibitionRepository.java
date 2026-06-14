package com.artlog.domain.exhibition.repository;

import com.artlog.domain.exhibition.entity.Exhibition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExhibitionRepository extends JpaRepository<Exhibition, Long> {

    List<Exhibition> findAllByUserIdOrderByVisitDateDescCreatedAtDesc(Long userId);

    Page<Exhibition> findAllByUserId(Long userId, Pageable pageable);

    Optional<Exhibition> findByIdAndUserId(Long exhibitionId, Long userId);

    @Query("""
        SELECT e
        FROM Exhibition e
        WHERE e.user.id = :userId
          AND (
                :keyword IS NULL
                OR e.title LIKE CONCAT('%', :keyword, '%')
                OR e.museumName LIKE CONCAT('%', :keyword, '%')
                OR e.location LIKE CONCAT('%', :keyword, '%')
                OR e.memo LIKE CONCAT('%', :keyword, '%')
              )
          AND (
                :museumName IS NULL
                OR e.museumName LIKE CONCAT('%', :museumName, '%')
              )
          AND (
                :location IS NULL
                OR e.location LIKE CONCAT('%', :location, '%')
              )
          AND (
                :visitFrom IS NULL
                OR e.visitDate >= :visitFrom
              )
          AND (
                :visitTo IS NULL
                OR e.visitDate <= :visitTo
              )
        """)
    Page<Exhibition> searchExhibitions(
            @Param("userId") Long userId,
            @Param("keyword") String keyword,
            @Param("museumName") String museumName,
            @Param("location") String location,
            @Param("visitFrom") LocalDate visitFrom,
            @Param("visitTo") LocalDate visitTo,
            Pageable pageable
    );

    long countByUserId(Long userId);

    @Query("""
        SELECT FUNCTION('DATE_FORMAT', e.visitDate, '%Y-%m'), COUNT(e.id)
        FROM Exhibition e
        WHERE e.user.id = :userId
          AND e.visitDate IS NOT NULL
        GROUP BY FUNCTION('DATE_FORMAT', e.visitDate, '%Y-%m')
        ORDER BY FUNCTION('DATE_FORMAT', e.visitDate, '%Y-%m') ASC
        """)
    List<Object[]> countMonthlyVisits(@Param("userId") Long userId);

    @Query("""
        SELECT e.museumName, COUNT(e.id)
        FROM Exhibition e
        WHERE e.user.id = :userId
          AND e.museumName IS NOT NULL
        GROUP BY e.museumName
        ORDER BY COUNT(e.id) DESC
        """)
    List<Object[]> countTopMuseums(@Param("userId") Long userId);
}