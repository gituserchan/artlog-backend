package com.artlog.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    // Common
    OK(HttpStatus.OK, "요청이 성공적으로 처리되었습니다."),
    CREATED(HttpStatus.CREATED, "리소스가 성공적으로 생성되었습니다."),

    // Auth
    SIGNUP_SUCCESS(HttpStatus.CREATED, "회원가입이 완료되었습니다."),
    LOGIN_SUCCESS(HttpStatus.OK, "로그인이 완료되었습니다."),
    LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃이 완료되었습니다."),

    // User
    USER_INFO_SUCCESS(HttpStatus.OK, "회원 정보 조회가 완료되었습니다."),
    USER_UPDATE_SUCCESS(HttpStatus.OK, "회원 정보가 수정되었습니다."),

    // Exhibition
    EXHIBITION_CREATE_SUCCESS(HttpStatus.CREATED, "전시 기록이 등록되었습니다."),
    EXHIBITION_LIST_SUCCESS(HttpStatus.OK, "전시 기록 목록 조회가 완료되었습니다."),
    EXHIBITION_DETAIL_SUCCESS(HttpStatus.OK, "전시 기록 상세 조회가 완료되었습니다."),
    EXHIBITION_UPDATE_SUCCESS(HttpStatus.OK, "전시 기록이 수정되었습니다."),
    EXHIBITION_DELETE_SUCCESS(HttpStatus.OK, "전시 기록이 삭제되었습니다."),

    // Artwork
    ARTWORK_CREATE_SUCCESS(HttpStatus.CREATED, "작품 기록이 등록되었습니다."),
    ARTWORK_LIST_SUCCESS(HttpStatus.OK, "작품 기록 목록 조회가 완료되었습니다."),
    ARTWORK_DETAIL_SUCCESS(HttpStatus.OK, "작품 기록 상세 조회가 완료되었습니다."),
    ARTWORK_UPDATE_SUCCESS(HttpStatus.OK, "작품 기록이 수정되었습니다."),
    ARTWORK_DELETE_SUCCESS(HttpStatus.OK, "작품 기록이 삭제되었습니다."),

    // Review
    REVIEW_CREATE_SUCCESS(HttpStatus.CREATED, "감상 기록이 등록되었습니다."),
    REVIEW_LIST_SUCCESS(HttpStatus.OK, "감상 기록 목록 조회가 완료되었습니다."),
    REVIEW_DETAIL_SUCCESS(HttpStatus.OK, "감상 기록 상세 조회가 완료되었습니다."),
    REVIEW_UPDATE_SUCCESS(HttpStatus.OK, "감상 기록이 수정되었습니다."),
    REVIEW_DELETE_SUCCESS(HttpStatus.OK, "감상 기록이 삭제되었습니다.");

    private final HttpStatus status;
    private final String message;
}