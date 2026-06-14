package com.artlog.domain.exhibition.service;

import com.artlog.domain.exhibition.dto.request.ExhibitionCreateRequest;
import com.artlog.domain.exhibition.dto.request.ExhibitionSearchRequest;
import com.artlog.domain.exhibition.dto.request.ExhibitionUpdateRequest;
import com.artlog.domain.exhibition.dto.response.ExhibitionResponse;
import com.artlog.domain.exhibition.dto.response.ExhibitionSimpleResponse;
import com.artlog.domain.exhibition.entity.Exhibition;
import com.artlog.domain.exhibition.repository.ExhibitionRepository;
import com.artlog.domain.user.entity.User;
import com.artlog.domain.user.repository.UserRepository;
import com.artlog.global.exception.BusinessException;
import com.artlog.global.exception.ErrorCode;
import com.artlog.global.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExhibitionService {

    private final ExhibitionRepository exhibitionRepository;
    private final UserRepository userRepository;

    @Transactional
    public ExhibitionResponse createExhibition(
            Long userId,
            ExhibitionCreateRequest request
    ) {
        User user = getUser(userId);

        Exhibition exhibition = Exhibition.builder()
                .user(user)
                .title(request.title())
                .museumName(request.museumName())
                .location(request.location())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .visitDate(request.visitDate())
                .posterImageUrl(request.posterImageUrl())
                .memo(request.memo())
                .build();

        Exhibition savedExhibition = exhibitionRepository.save(exhibition);

        return ExhibitionResponse.from(savedExhibition);
    }

    public PageResponse<ExhibitionSimpleResponse> getMyExhibitions(
            Long userId,
            Pageable pageable
    ) {
        Page<ExhibitionSimpleResponse> page = exhibitionRepository.findAllByUserId(userId, pageable)
                .map(ExhibitionSimpleResponse::from);

        return PageResponse.from(page);
    }

    public ExhibitionResponse getExhibition(
            Long userId,
            Long exhibitionId
    ) {
        Exhibition exhibition = getMyExhibition(userId, exhibitionId);

        return ExhibitionResponse.from(exhibition);
    }

    @Transactional
    public ExhibitionResponse updateExhibition(
            Long userId,
            Long exhibitionId,
            ExhibitionUpdateRequest request
    ) {
        Exhibition exhibition = getMyExhibition(userId, exhibitionId);

        exhibition.update(
                request.title(),
                request.museumName(),
                request.location(),
                request.startDate(),
                request.endDate(),
                request.visitDate(),
                request.posterImageUrl(),
                request.memo()
        );

        return ExhibitionResponse.from(exhibition);
    }

    @Transactional
    public void deleteExhibition(
            Long userId,
            Long exhibitionId
    ) {
        Exhibition exhibition = getMyExhibition(userId, exhibitionId);

        exhibitionRepository.delete(exhibition);
    }

    public PageResponse<ExhibitionSimpleResponse> searchExhibitions(
            Long userId,
            ExhibitionSearchRequest request,
            Pageable pageable
    ) {
        Page<ExhibitionSimpleResponse> page = exhibitionRepository.searchExhibitions(
                        userId,
                        normalize(request.keyword()),
                        normalize(request.museumName()),
                        normalize(request.location()),
                        request.visitFrom(),
                        request.visitTo(),
                        pageable
                )
                .map(ExhibitionSimpleResponse::from);

        return PageResponse.from(page);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    private Exhibition getMyExhibition(
            Long userId,
            Long exhibitionId
    ) {
        return exhibitionRepository.findByIdAndUserId(exhibitionId, userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.EXHIBITION_NOT_FOUND));
    }

    private String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return value.trim();
    }
}