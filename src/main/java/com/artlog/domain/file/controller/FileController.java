package com.artlog.domain.file.controller;

import com.artlog.domain.file.dto.response.ImageUploadResponse;
import com.artlog.domain.file.service.FileService;
import com.artlog.global.response.ApiResponse;
import com.artlog.global.response.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "File", description = "파일 업로드 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;

    @Operation(summary = "이미지 업로드", description = "전시 포스터, 작품 이미지, 감상 이미지 등에 사용할 이미지를 업로드합니다.")
    @PostMapping(value = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ImageUploadResponse> uploadImage(
            @RequestPart("file") MultipartFile file
    ) {
        ImageUploadResponse response = fileService.uploadImage(file);

        return ApiResponse.success(SuccessCode.IMAGE_UPLOAD_SUCCESS, response);
    }
}