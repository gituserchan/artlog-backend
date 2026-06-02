package com.artlog.global.health;

import com.artlog.global.response.ApiResponse;
import com.artlog.global.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/api/health")
    public ResponseEntity<ApiResponse<Map<String, String>>> healthCheck() {
        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .body(ApiResponse.success(
                        SuccessCode.OK,
                        Map.of("status", "Artlog server is running")
                ));
    }
}