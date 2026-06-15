package com.artlog.domain.user.controller;

import com.artlog.domain.user.dto.request.PasswordUpdateRequest;
import com.artlog.domain.user.dto.request.UserUpdateRequest;
import com.artlog.domain.user.dto.response.UserInfoResponse;
import com.artlog.domain.user.service.UserService;
import com.artlog.global.response.ApiResponse;
import com.artlog.global.response.SuccessCode;
import com.artlog.global.security.user.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "회원 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "내 회원 정보 조회", description = "현재 로그인한 사용자의 회원 정보를 조회합니다.")
    @GetMapping("/me")
    public ApiResponse<UserInfoResponse> getMyInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        UserInfoResponse response = userService.getMyInfo(userDetails.getUserId());

        return ApiResponse.success(SuccessCode.USER_INFO_SUCCESS, response);
    }

    @Operation(summary = "내 회원 정보 수정", description = "현재 로그인한 사용자의 닉네임을 수정합니다.")
    @PutMapping("/me")
    public ApiResponse<UserInfoResponse> updateMyInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody UserUpdateRequest request
    ) {
        UserInfoResponse response = userService.updateMyInfo(
                userDetails.getUserId(),
                request
        );

        return ApiResponse.success(SuccessCode.USER_UPDATE_SUCCESS, response);
    }

    @Operation(summary = "비밀번호 변경", description = "현재 로그인한 사용자의 비밀번호를 변경합니다.")
    @PutMapping("/me/password")
    public ApiResponse<Void> updatePassword(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody PasswordUpdateRequest request
    ) {
        userService.updatePassword(
                userDetails.getUserId(),
                request
        );

        return ApiResponse.success(SuccessCode.PASSWORD_UPDATE_SUCCESS);
    }
}