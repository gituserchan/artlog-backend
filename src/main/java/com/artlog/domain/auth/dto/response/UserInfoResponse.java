package com.artlog.domain.auth.dto.response;

import com.artlog.domain.user.entity.User;

public record UserInfoResponse(
        Long userId,
        String email,
        String nickname,
        String role
) {

    public static UserInfoResponse from(User user){
        return new UserInfoResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getRole().name()
        );
    }
}
