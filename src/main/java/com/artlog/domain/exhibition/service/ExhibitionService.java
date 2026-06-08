package com.artlog.domain.exhibition.service;

import com.artlog.domain.exhibition.dto.request.ExhibitionCreateRequest;
import com.artlog.domain.exhibition.dto.request.ExhibitionUpdateRequest;
import com.artlog.domain.exhibition.dto.response.ExhibitionResponse;
import com.artlog.domain.exhibition.dto.response.ExhibitionSimpleResponse;
import com.artlog.domain.exhibition.entity.Exhibition;
import com.artlog.domain.exhibition.repository.ExhibitionRepository;
import com.artlog.domain.user.entity.User;
import com.artlog.domain.user.repository.UserRepository;
import com.artlog.global.exception.BusinessException;
import com.artlog.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExhibitionService {
    private final ExhibitionRepository exhibitionRepository;
    private final UserRepository userRepository;

    @Transactional
    public ExhibitionResponse createExhibition(
            Long userId,
            ExhibitionCreateRequest request)
    {
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

    public List<ExhibitionSimpleResponse> getMyExhibitions(Long userId) {
        return exhibitionRepository.findAllByUserIdOrderByVisitDateDescCreatedAtDesc(userId)
                .stream()
                .map(ExhibitionSimpleResponse::from)
                .toList();
    }

    public ExhibitionResponse getExhibition(
            Long userId,
            Long exhibitionId
    ) {
        Exhibition exhibition = getMyExhibitions(userId, exhibitionId);

        return ExhibitionResponse.from(exhibition);
    }

    @Transactional
    public ExhibitionResponse updateExhibition(
            Long userId,
            Long exhibitionId,
            ExhibitionUpdateRequest request
    ){
        Exhibition exhibition = getMyExhibitions(userId, exhibitionId);

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
    ){
        Exhibition exhibition = getMyExhibitions(userId, exhibitionId);
        exhibitionRepository.delete(exhibition);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    private Exhibition getMyExhibitions(
            Long userId,
            Long exhibitionId
    ){
        return exhibitionRepository.findByIdAndUserId(exhibitionId, userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.EXHIBITION_NOT_FOUND));
    }
}
