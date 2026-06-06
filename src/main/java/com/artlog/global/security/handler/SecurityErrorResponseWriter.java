package com.artlog.global.security.handler;

import com.artlog.global.exception.ErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class SecurityErrorResponseWriter {

    public void write(
            HttpServletResponse response,
            ErrorCode errorCode
    ) throws IOException {
        response.setStatus(errorCode.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        response.getWriter().write("""
                {
                "status" : %d,
                "code": "%s",
                "message": "%s"
                }
                """.formatted(
                        errorCode.getStatus().value(),
                errorCode.getCode(),
                errorCode.getMessage()
                ));
    }
}
