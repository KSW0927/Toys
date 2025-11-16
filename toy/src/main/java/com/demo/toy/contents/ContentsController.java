package com.demo.toy.contents;

import java.io.IOException;

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

import com.demo.toy.common.response.ApiResponse;
import com.demo.toy.common.response.ResponseResult;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "콘텐츠 API", description = "콘텐츠 등록, 수정, 삭제, 조회 API")
public class ContentsController {

    private final ContentsService contentsService;

    public ContentsController(ContentsService contentsService) {
        this.contentsService = contentsService;
    }

    /**
     * 목록
     */
    @GetMapping("/contents")
    @Operation(summary = "콘텐츠 목록 조회", description = "콘텐츠 목록을 조회합니다.")
    public ResponseEntity<?> getContentsList(ContentsSearchParamsDTO searchDTO) {
        Page<ContentsEntity> result = contentsService.getContentsList(searchDTO);
        return ResponseEntity.status(ResponseResult.SUCCESS_READ.getCode()).body(result);
    }
    
    /**
     * 저장
     */
    @PostMapping("/contents")
    @Operation(summary = "콘텐츠 등록", description = "콘텐츠를 등록합니다.")
    public ResponseEntity<ApiResponse<ContentsEntity>> insertContent(@RequestBody ContentsEntity apiEntity) {
        ContentsEntity saved = contentsService.insertContent(apiEntity);
        ApiResponse<ContentsEntity> response = new ApiResponse<>(ResponseResult.SUCCESS_SAVE, saved);
        return ResponseEntity.status(ResponseResult.SUCCESS_SAVE.getCode()).body(response);
    }
    
    /**
     * 상세
     */
    @GetMapping("/contents/{contentId}")
    @Operation(summary = "콘텐츠 상세", description = "ID로 콘텐츠 상세 정보를 조회합니다.")
    public ResponseEntity<?> getContentDetail(@PathVariable("contentId") Long contentId) {
        ContentsEntity entity = contentsService.getContentDetail(contentId);
        return ResponseEntity.status(ResponseResult.SUCCESS_READ.getCode()).body(entity);
    }

    /**
     * 수정
     */
    @PutMapping("/contents/{contentId}")
    @Operation(summary = "콘텐츠 수정", description = "ID로 콘텐츠 상세 정보를 수정합니다.")
    public ResponseEntity<String> updateContent(@PathVariable("contentId") Long contentId, @RequestBody ContentsDTO dto) {
        ContentsEntity updated = contentsService.updateContent(contentId, dto);
        ApiResponse<ContentsEntity> response = new ApiResponse<>(ResponseResult.SUCCESS_UPDATE, updated);
        return ResponseEntity.status(ResponseResult.SUCCESS_UPDATE.getCode()).body(response.getMessage());
    }
    
    /**
     * 삭제
     */
    @DeleteMapping("/contents/{contentId}")
    @Operation(summary = "콘텐츠 삭제", description = "ID로 콘텐츠 상세 정보를 삭제합니다.")
    public ResponseEntity<String> deleteContent(@PathVariable("contentId") Long contentId) {
    	contentsService.deleteContent(contentId);
        return ResponseEntity.status(ResponseResult.SUCCESS_DELETE.getCode()).body(ResponseResult.SUCCESS_DELETE.getMessage());
    }

    /**
     * 업로드
     */
    @PostMapping("/contents/upload")
    @Operation(summary = "콘텐츠 업로드", description = "콘텐츠 이미지를 등록/수정합니다.")
    public ResponseEntity<ApiResponse<String>> uploadContentImage(@RequestParam("file") MultipartFile file) throws IOException {
    	String filePath = contentsService.uploadContentImage(file);
        return ResponseEntity.status(ResponseResult.SUCCESS_SAVE.getCode()).body(new ApiResponse<>(ResponseResult.SUCCESS_SAVE, filePath));
    }
}