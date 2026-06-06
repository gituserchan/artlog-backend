package com.artlog.domain.auth.controller;

import com.artlog.domain.auth.dto.response.UserInfoResponse;
import com.artlog.domain.auth.service.UserService;
import com.artlog.global.response.ApiResponse;
import com.artlog.global.response.SuccessCode;
import com.artlog.global.security.user.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "회원 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserController {

    private final UserService userService;

    @Operation(summary = "내 정보 조회", description = "현재 로그인한 사용자의 정보를 조회합니다.")
    @GetMapping("/me")
    public ApiResponse<UserInfoResponse> getMyInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        UserInfoResponse response = userService.getMyInfo(userDetails.getUserId());

        return ApiResponse.success(SuccessCode.USER_INFO_SUCCESS, response);
    }
}
