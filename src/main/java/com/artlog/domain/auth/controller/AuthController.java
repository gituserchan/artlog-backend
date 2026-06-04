package com.artlog.domain.auth.controller;

import com.artlog.domain.auth.dto.request.LoginRequest;
import com.artlog.domain.auth.dto.request.SignupRequest;
import com.artlog.domain.auth.dto.response.LoginResponse;
import com.artlog.domain.auth.dto.response.SignupResponse;
import com.artlog.domain.auth.service.AuthService;
import com.artlog.global.response.ApiResponse;
import com.artlog.global.response.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "인증 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입", description = "이메일, 비밀번호, 닉네임을 입력받아 회원가입을 진행합니다.")
    @PostMapping("/signup")
    public ApiResponse<SignupResponse> signup(
            @Valid @RequestBody SignupRequest request
    ) {
        SignupResponse response = authService.signup(request);

        return ApiResponse.success(SuccessCode.SIGNUP_SUCCESS, response);
    }

    @Operation(summary = "로그인", description = "이메일과 비밀번호를 입력받아 로그인하고 Access Token을 발급합니다.")
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
    ){
        LoginResponse response = authService.login(request);

        return ApiResponse.success(SuccessCode.LOGIN_SUCCESS, response);
    }
}