package com.demo.toy.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 공통 예외 처리 핸들러
 */
@RestControllerAdvice
public class RespExcpHandler {
	
	private static final Logger log = LoggerFactory.getLogger(RespExcpHandler.class);

    /**
     * 파일 업로드 관련 예외 처리
     */
	@ExceptionHandler(FileUploadException.class)
    public ResponseEntity<ApiResponse<Void>> handleFileUpload(FileUploadException ex) {
        log.error("파일 업로드 오류 발생: {}", ex.getMessage(), ex);
        ApiResponse<Void> response = new ApiResponse<>();
        response.setCode(ResponseResult.ERROR_UPLOAD.getCode());
        response.setMessage(ResponseResult.ERROR_UPLOAD.getMessage());
        return ResponseEntity.status(ResponseResult.ERROR_UPLOAD.getCode()).body(response);
    }
    
    /**
     * 잘못된 요청
     */
	@ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("잘못된 요청 발생: {}", ex.getMessage());
        ApiResponse<Void> response = new ApiResponse<>();
        response.setCode(ResponseResult.ERROR_SERVER.getCode());
        response.setMessage(ResponseResult.ERROR_NOT_FOUND.getMessage());
        return ResponseEntity.status(ResponseResult.ERROR_SERVER.getCode()).body(response);
    }

    /**
     * 데이터 없음
     */
	@ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomNotFound(NotFoundException ex) {
        log.info("데이터 없음: {}", ex.getMessage());
        ApiResponse<Void> response = new ApiResponse<>();
        response.setCode(ResponseResult.ERROR_NOT_FOUND.getCode());
        response.setMessage(ex.getMessage());
        return ResponseEntity.status(ResponseResult.ERROR_NOT_FOUND.getCode()).body(response);
    }

    /**
     * 그 외 모든 예외
     */
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        log.error("서버 내부 예외 발생", ex.getMessage());
        ApiResponse<Void> response = new ApiResponse<>();
        response.setCode(ResponseResult.ERROR_SERVER.getCode());
        response.setMessage(ResponseResult.ERROR_SERVER.getMessage());
        return ResponseEntity.status(ResponseResult.ERROR_SERVER.getCode()).body(response);
    }
}
