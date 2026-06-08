package com.artlog.domain.exhibition.repository;

import com.artlog.domain.exhibition.entity.Exhibition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExhibitionRepository extends JpaRepository<Exhibition, Long> {

    List<Exhibition> findAllByUserIdOrderByVisitDateDescCreatedAtDesc(Long userId);

    Optional<Exhibition> findByIdAndUserId(Long exhibitionId, Long userId);
}
