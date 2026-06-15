package com.artlog.domain.user.dto.response;

import com.artlog.domain.user.entity.User;

import java.time.LocalDateTime;

public record UserInfoResponse(
        Long userId,
        String email,
        String nickname,
        String role,
        LocalDateTime createdAt
) {

    public static UserInfoResponse from(User user) {
        return new UserInfoResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getRole().name(),
                user.getCreatedAt()
        );
    }
}