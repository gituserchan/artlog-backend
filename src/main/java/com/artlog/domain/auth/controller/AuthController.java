package com.artlog.domain.auth.controller;

import com.artlog.domain.auth.dto.request.SignupRequest;
import com.artlog.domain.auth.dto.response.SignupResponse;
import com.artlog.domain.auth.service.AuthService;
import com.artlog.global.response.ApiResponse;
import com.artlog.global.response.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "인증 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입", description = "이메일, 비밀번호, 닉네임을 입력받아 회원가입을 진행합니다.")
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignupResponse>> signup(
            @Valid @RequestBody SignupRequest request
    ) {
        SignupResponse response = authService.signup(request);

        return ResponseEntity
                .status(SuccessCode.SIGNUP_SUCCESS.getStatus())
                .body(ApiResponse.success(SuccessCode.SIGNUP_SUCCESS, response));
    }
}