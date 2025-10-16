package com.demo.toy.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.toy.common.ApiResponse;
import com.demo.toy.common.ResponseResult;
import com.demo.toy.dto.ApiDTO;
import com.demo.toy.dto.ApiSearchParamsDTO;
import com.demo.toy.entity.ApiEntity;
import com.demo.toy.service.ApiService;

@RestController
@RequestMapping("/api/data")
public class ApiController {

    private final ApiService apiService;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    /**
     * 동기화
     */
    @PostMapping("/pictures/sync")
    public ResponseEntity<ApiResponse<List<ApiEntity>>> syncPicture() {
        List<ApiEntity> syncedList = apiService.syncPicture();

        ApiResponse<List<ApiEntity>> response;

        if (syncedList.isEmpty()) {
            response = new ApiResponse<>(ResponseResult.SUCCESS_NO_DATA, syncedList);
        } else {
            response = new ApiResponse<>(ResponseResult.SUCCESS_SAVE, syncedList);
        }

        return ResponseEntity.status(ResponseResult.SUCCESS_SAVE.getCode()).body(response);
    }
    
    /**
     * 목록
     */
    @GetMapping("/pictures")
    public ResponseEntity<?> getPictureList(ApiSearchParamsDTO searchDTO) {
        Page<ApiEntity> result = apiService.getPictureList(searchDTO);
        return ResponseEntity.status(ResponseResult.SUCCESS_READ.getCode()).body(result);
    }
    
    /**
     * 저장
     */
    @PostMapping("/pictures")
    public ResponseEntity<String> insertPicture(@RequestBody ApiEntity apiEntity) {
        ApiEntity saved = apiService.insertPicture(apiEntity);
        ApiResponse<ApiEntity> response = new ApiResponse<>(ResponseResult.SUCCESS_SAVE, saved);
        return ResponseEntity.status(ResponseResult.SUCCESS_SAVE.getCode()).body(response.getMessage());
    }
    
    /**
     * 상세
     */
    @GetMapping("/pictures/{id}")
    public ResponseEntity<?> getPictureDetail(@PathVariable("id") Long id) {
        ApiEntity entity = apiService.getPictureDetail(id);
        return ResponseEntity.status(ResponseResult.SUCCESS_READ.getCode()).body(entity);
    }

    /**
     * 수정
     */
    @PutMapping("/pictures/{id}")
    public ResponseEntity<String> updatePicture(@PathVariable("id") Long id, @RequestBody ApiDTO dto) {
        ApiEntity updated = apiService.updatePicture(id, dto);
        ApiResponse<ApiEntity> response = new ApiResponse<>(ResponseResult.SUCCESS_UPDATE, updated);
        return ResponseEntity.status(ResponseResult.SUCCESS_UPDATE.getCode()).body(response.getMessage());
    }
    
    /**
     * 삭제
     */
    @DeleteMapping("/pictures/{id}")
    public ResponseEntity<String> deletePicture(@PathVariable("id") Long id) {
        apiService.deletePicture(id);
        return ResponseEntity.status(ResponseResult.SUCCESS_DELETE.getCode()).body(ResponseResult.SUCCESS_DELETE.getMessage());
    }

    /**
     * 업로드
     */
    @PostMapping("/pictures/upload")
    public ResponseEntity<ApiResponse<String>> uploadPicture(@RequestParam("file") MultipartFile file) throws IOException {
    	String filePath = apiService.uploadPicture(file);
        return ResponseEntity.status(ResponseResult.SUCCESS_SAVE.getCode()).body(new ApiResponse<>(ResponseResult.SUCCESS_SAVE, filePath));
    }
}