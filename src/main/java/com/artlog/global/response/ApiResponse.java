package com.artlog.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private final int status;
    private final String message;
    private final T data;

    public static <T> ApiResponse<T> success(SuccessCode successCode, T data) {
        return ApiResponse.<T>builder()
                .status(successCode.getStatus().value())
                .message(successCode.getMessage())
                .data(data)
                .build();
    }

    public static ApiResponse<Void> success(SuccessCode successCode) {
        return ApiResponse.<Void>builder()
                .status(successCode.getStatus().value())
                .message(successCode.getMessage())
                .build();
    }
}