package com.artlog.global.security.user;

import com.artlog.domain.user.entity.User;
import com.artlog.domain.user.type.UserRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails {
    private final Long userId;
    private final String email;
    private final String nickname;
    private final UserRole role;

    private CustomUserDetails(final Long userId, final String email, final String nickname, final UserRole role) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
    }

    public static CustomUserDetails from(User user) {
        return new CustomUserDetails(user.getId(), user.getEmail(), user.getNickname(), user.getRole());
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }
}
