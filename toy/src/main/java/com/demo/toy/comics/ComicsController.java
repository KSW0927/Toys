package com.demo.toy.comics;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.toy.common.response.ResponseResult;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/comics")
@Tag(name = "만화 API", description = "만화 별 데이터 등록, 수정, 삭제, 조회 API")
public class ComicsController {

    private final ComicsService comicsService;

    public ComicsController(ComicsService comicsService) {
        this.comicsService = comicsService;
    }

    /**
     * 콘텐츠 별 목록 조회
     */
    @GetMapping("/{contentId}/volumes")
    @Operation(summary = "만화 목록 조회", description = "콘텐츠 ID로 해당 콘텐츠의 만화 정보를 조회합니다.")
    public ResponseEntity<List<ComicsEntity>> getComicsList(@PathVariable("contentId") Long contentId) {
        List<ComicsEntity> result = comicsService.getComicsByContentId(contentId);
        return ResponseEntity.status(ResponseResult.SUCCESS_READ.getCode()).body(result);
    }

    /**
     * 만화 배치 등록
     */
    @PostMapping("/{contentId}/volumes")
    @Operation(summary = "만화 일괄 등록", description = "콘텐츠 ID에 여러 만화를 일괄 등록합니다.")
    public ResponseEntity<List<ComicsEntity>> insertComicsBatch(@PathVariable("contentId") Long contentId, @RequestBody List<ComicsDTO> dtoList) {
        List<ComicsEntity> insert = comicsService.insertComicsBatch(contentId, dtoList);
        return ResponseEntity.status(ResponseResult.SUCCESS_SAVE.getCode()).body(insert);
    }
    
    /**
     * 만화 배치 수정
     */
    @PutMapping("/{contentId}/volumes")
    @Operation(summary = "만화 일괄 수정", description = "콘텐츠 ID에 여러 만화를 일괄 수정합니다.")
    public ResponseEntity<List<ComicsEntity>> updateComicsBatch(@PathVariable("contentId") Long contentId, @RequestBody List<ComicsDTO> dtoList) {
    	List<ComicsEntity> update = comicsService.updateComicsBatch(contentId, dtoList);
    	return ResponseEntity.status(ResponseResult.SUCCESS_SAVE.getCode()).body(update);
    }

    /**
     * 권수 삭제
     */
    @DeleteMapping("/{contentId}/volumes/{comicsId}")
    @Operation(summary = "만화 삭제", description = "만화 ID로 권수 정보를 삭제합니다.")
    public ResponseEntity<String> deleteComics(@PathVariable("comicsId") Long comicsId) {
        comicsService.deleteComics(comicsId);
        return ResponseEntity.status(ResponseResult.SUCCESS_DELETE.getCode()).body(ResponseResult.SUCCESS_DELETE.getMessage());
    }
}
