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
@Tag(name = "만화 API", description = "만화 메타 데이터 API")
public class ComicsController {

    private final ComicsService comicsService;

    public ComicsController(ComicsService comicsService) {
        this.comicsService = comicsService;
    }

    /**
     * 콘텐츠별 권수 목록 조회
     */
    @GetMapping("/{contentId}")
    @Operation(summary = "콘텐츠 권수 목록 조회", description = "콘텐츠 ID로 해당 콘텐츠의 권수 정보를 조회합니다.")
    public ResponseEntity<List<ComicsEntity>> getComicsList(@PathVariable String contentId) {
        List<ComicsEntity> result = comicsService.getComicsByContentId(contentId);
        return ResponseEntity.status(ResponseResult.SUCCESS_READ.getCode()).body(result);
    }

    /**
     * 권수 단건 등록
     */
    @PostMapping("/{contentId}")
    @Operation(summary = "권수 등록", description = "콘텐츠 ID에 권수를 등록합니다.")
    public ResponseEntity<ComicsEntity> insertComics(@PathVariable String contentId, @RequestBody ComicsDTO dto) {
        ComicsEntity saved = comicsService.insertComics(contentId, dto);
        return ResponseEntity.status(ResponseResult.SUCCESS_SAVE.getCode()).body(saved);
    }

    /**
     * 권수 배치 등록
     */
    @PostMapping("/{contentId}/batch")
    @Operation(summary = "권수 일괄 등록", description = "콘텐츠 ID에 여러 권수를 일괄 등록합니다.")
    public ResponseEntity<List<ComicsEntity>> insertComicsBatch(@PathVariable("contentId") String contentId, @RequestBody List<ComicsDTO> dtoList) {
        List<ComicsEntity> savedList = comicsService.insertComicsBatch(contentId, dtoList);
        return ResponseEntity.status(ResponseResult.SUCCESS_SAVE.getCode()).body(savedList);
    }

    /**
     * 권수 단건 수정
     */
    @PutMapping("/{comicsId}")
    @Operation(summary = "권수 수정", description = "권수 ID로 권수 정보를 수정합니다.")
    public ResponseEntity<ComicsEntity> updateComics(@PathVariable("id") String comicsId, @RequestBody ComicsDTO dto) {
        ComicsEntity updated = comicsService.updateComics(comicsId, dto);
        return ResponseEntity.status(ResponseResult.SUCCESS_UPDATE.getCode()).body(updated);
    }

    /**
     * 권수 삭제
     */
    @DeleteMapping("/{comicsId}")
    @Operation(summary = "권수 삭제", description = "권수 ID로 권수 정보를 삭제합니다.")
    public ResponseEntity<String> deleteComics(@PathVariable String comicsId) {
        comicsService.deleteComics(comicsId);
        return ResponseEntity.status(ResponseResult.SUCCESS_DELETE.getCode()).body(ResponseResult.SUCCESS_DELETE.getMessage());
    }
}
