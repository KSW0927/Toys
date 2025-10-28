package com.demo.toy.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.demo.toy.common.exception.BusinessException;
import com.demo.toy.common.exception.FileUploadException;
import com.demo.toy.common.exception.NotFoundException;
import com.demo.toy.common.response.ApiResponse;
import com.demo.toy.common.response.ResponseResult;

/**
 * 공통 예외 처리 핸들러
 */
@RestControllerAdvice(basePackages = "com.demo.toy")
public class RespExcpHandler {
	
	private static final Logger log = LoggerFactory.getLogger(RespExcpHandler.class);

    /**
     * 비즈니스 로직 예외
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException ex) {
        log.warn("비즈니스 예외 발생: {}", ex.getMessage());
        ApiResponse<Void> response = new ApiResponse<>();
        response.setCode(ex.getResult().getCode());
        response.setMessage(ex.getResult().getMessage());
        return ResponseEntity.status(ex.getResult().getCode()).body(response);
    }
    
    /**
     * DB PK 위반 예외
     */
    
    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleDataIntegrityViolation(org.springframework.dao.DataIntegrityViolationException ex) {
        ApiResponse<Void> response = new ApiResponse<>();
        response.setCode(ResponseResult.ERROR_DUPLICATE.getCode());
        response.setMessage(ResponseResult.ERROR_DUPLICATE.getMessage());
        return ResponseEntity.status(ResponseResult.ERROR_DUPLICATE.getCode()).body(response);
    }
    
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
        response.setMessage(ResponseResult.ERROR_SERVER.getMessage());
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
