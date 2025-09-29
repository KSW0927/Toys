package com.demo.toy.common;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 공통 응답 코드 Handler
 */
@RestControllerAdvice
public class RespExcpHandler {

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiResponse<Void>> handleNotFound(IllegalArgumentException ex) {
	    ApiResponse<Void> response = new ApiResponse<>();
	    response.setCode(ResponseResult.ERROR_NOT_FOUND.getCode());
	    response.setMessage(ResponseResult.ERROR_NOT_FOUND.getMessage());
	    return ResponseEntity.status(ResponseResult.ERROR_NOT_FOUND.getCode()).body(response);
	}
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        return ResponseEntity.status(ResponseResult.ERROR_SERVER.getCode())
                             .body(ResponseResult.ERROR_SERVER.getMessage());
    }
    
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFound(NotFoundException ex) {
        return ResponseEntity.status(ResponseResult.ERROR_NOT_FOUND.getCode())
                             .body(ex.getMessage());
    }
}
