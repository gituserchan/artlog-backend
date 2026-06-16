package com.artlog.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "COMMON_400", "입력값이 올바르지 않습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "COMMON_405", "지원하지 않는 HTTP 메서드입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_500", "서버 내부 오류가 발생했습니다."),

    // Auth
    INVALID_LOGIN_REQUEST(HttpStatus.BAD_REQUEST, "AUTH_400", "이메일 또는 비밀번호가 올바르지 않습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH_401", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "AUTH_403", "접근 권한이 없습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_401_1", "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_401_2", "만료된 토큰입니다."),

    // User
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "USER_409_1", "이미 사용 중인 이메일입니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "USER_409_2", "이미 사용 중인 닉네임입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_404", "회원을 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "USER_400_1", "현재 비밀번호가 올바르지 않습니다."),
    SAME_AS_OLD_PASSWORD(HttpStatus.BAD_REQUEST, "USER_400_2", "새 비밀번호는 현재 비밀번호와 달라야 합니다."),

    // Exhibition
    EXHIBITION_NOT_FOUND(HttpStatus.NOT_FOUND, "EXHIBITION_404", "전시 기록을 찾을 수 없습니다."),

    // Artwork
    ARTWORK_NOT_FOUND(HttpStatus.NOT_FOUND, "ARTWORK_404", "작품 기록을 찾을 수 없습니다."),

    // Review
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW_404", "감상 기록을 찾을 수 없습니다."),

    // File
    EMPTY_FILE(HttpStatus.BAD_REQUEST, "FILE_400_1", "업로드할 파일이 비어 있습니다."),
    INVALID_IMAGE_FILE(HttpStatus.BAD_REQUEST, "FILE_400_2", "이미지 파일만 업로드할 수 있습니다."),
    FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "FILE_500", "파일 업로드에 실패했습니다."),

    // Review Reaction
    DUPLICATE_REVIEW_LIKE(HttpStatus.CONFLICT, "REVIEW_LIKE_409", "이미 좋아요한 감상 기록입니다."),
    REVIEW_LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW_LIKE_404", "좋아요 기록을 찾을 수 없습니다."),
    DUPLICATE_REVIEW_BOOKMARK(HttpStatus.CONFLICT, "REVIEW_BOOKMARK_409", "이미 북마크한 감상 기록입니다."),
    REVIEW_BOOKMARK_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW_BOOKMARK_404", "북마크 기록을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}