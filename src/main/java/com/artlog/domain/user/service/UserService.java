package com.artlog.domain.user.service;

import com.artlog.domain.user.dto.request.PasswordUpdateRequest;
import com.artlog.domain.user.dto.request.UserUpdateRequest;
import com.artlog.domain.user.dto.response.UserInfoResponse;
import com.artlog.domain.user.entity.User;
import com.artlog.domain.user.repository.UserRepository;
import com.artlog.global.exception.BusinessException;
import com.artlog.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserInfoResponse getMyInfo(Long userId) {
        User user = getUser(userId);

        return UserInfoResponse.from(user);
    }

    @Transactional
    public UserInfoResponse updateMyInfo(
            Long userId,
            UserUpdateRequest request
    ) {
        User user = getUser(userId);

        String nickname = request.nickname().trim();

        if (!user.getNickname().equals(nickname)
                && userRepository.existsByNickname(nickname)) {
            throw new BusinessException(ErrorCode.DUPLICATE_NICKNAME);
        }

        user.updateNickname(nickname);

        return UserInfoResponse.from(user);
    }

    @Transactional
    public void updatePassword(
            Long userId,
            PasswordUpdateRequest request
    ) {
        User user = getUser(userId);

        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_PASSWORD);
        }

        if (passwordEncoder.matches(request.newPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.SAME_AS_OLD_PASSWORD);
        }

        String encodedNewPassword = passwordEncoder.encode(request.newPassword());

        user.updatePassword(encodedNewPassword);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }
}