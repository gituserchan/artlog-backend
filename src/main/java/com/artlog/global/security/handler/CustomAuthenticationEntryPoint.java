package com.artlog.global.security.handler;

import com.artlog.global.exception.ErrorCode;
import com.artlog.global.security.jwt.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final SecurityErrorResponseWriter responseWriter;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        ErrorCode errorCode = (ErrorCode) request.getAttribute(
                JwtAuthenticationFilter.TOKEN_EXCEPTION_ATTRIBUTE
        );

        if (errorCode == null) {
            errorCode = ErrorCode.UNAUTHORIZED;
        }

        responseWriter.write(response, errorCode);
    }
}