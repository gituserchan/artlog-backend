package com.artlog.domain.auth.dto.response;


import com.artlog.domain.user.entity.User;

public record LoginResponse(
        Long userId,
        String email,
        String nickname,
        String role,
        String accessToken
){
    public static LoginResponse of(User user, String accessToken) {
        return new LoginResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getRole().name(),
                accessToken
        );
    }
}
