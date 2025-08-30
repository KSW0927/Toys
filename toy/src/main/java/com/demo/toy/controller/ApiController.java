package com.demo.toy.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.demo.toy.common.ResponseResult;
import com.demo.toy.entity.ApiEntity;
import com.demo.toy.service.ApiService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/data")
public class ApiController {

    private final ApiService apiService;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    // 동기화
    @PostMapping("/sync")
    public ResponseEntity<?> apiSync() {
        try {
            String serviceKey = "XsHEUftHrR8BU7rVbvE/4y3hbn4XXwfS5BpxrMBbTZhGNWqI935dAI6i97WctO3EoAW5saabVeBUWG2qSOp3xg==";
            String baseUrl = "https://apis.data.go.kr/B551011/PhotoGalleryService1/galleryList1";

            int numOfRows = 1000;
            int pageNo = 1;
            int totalCount = Integer.MAX_VALUE;

            List<ApiEntity> newEntities = new ArrayList<>();

            while ((pageNo - 1) * numOfRows < totalCount) {
                String apiUrl = baseUrl
                        + "?serviceKey=" + serviceKey
                        + "&numOfRows=" + numOfRows
                        + "&pageNo=" + pageNo
                        + "&MobileOS=ETC&MobileApp=AppTest&arrange=A&_type=json";

                Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);
                Map<String, Object> body = (Map<String, Object>) ((Map<String, Object>) response.get("response")).get("body");

                if (totalCount == Integer.MAX_VALUE) {
                    totalCount = (int) body.get("totalCount");
                }

                Map<String, Object> items = (Map<String, Object>) body.get("items");
                List<Map<String, Object>> itemList = (List<Map<String, Object>>) items.get("item");

                for (Map<String, Object> itemMap : itemList) {
                    String galContentId = (String) itemMap.get("galContentId");

                    // 중복 체크
                    if (!apiService.existsByGalContentId(galContentId)) {
                        ApiEntity entity = new ApiEntity(
                                galContentId,
                                (String) itemMap.get("galContentTypeId"),
                                (String) itemMap.get("galTitle"),
                                (String) itemMap.get("galWebImageUrl"),
                                (String) itemMap.get("galCreatedtime"),
                                (String) itemMap.get("galModifiedtime"),
                                (String) itemMap.get("galPhotographyMonth"),
                                (String) itemMap.get("galPhotographyLocation"),
                                (String) itemMap.get("galPhotographer"),
                                (String) itemMap.get("galSearchKeyword")
                        );
                        newEntities.add(entity);
                    }
                }

                pageNo++;
            }

            if (newEntities.isEmpty()) {
                return ResponseEntity.ok("새로운 데이터가 없습니다.");
            } else {
                List<ApiEntity> saved = apiService.saveAll(newEntities);
                return ResponseEntity.status(201).body(saved); // 201 Created
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("동기화 중 오류 발생: " + e.getMessage());
        }
    }
    
    // 목록 조회
    @GetMapping("/search")
    public ResponseEntity<?> searchByGalPhotographyLocation(
            @RequestParam("galPhotographyLocation") String galPhotographyLocation,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "9") int size
    ) {
        Page<ApiEntity> result = apiService.searchByGalPhotographyLocation(galPhotographyLocation, page, size);
        return ResponseEntity.status(ResponseResult.SUCCESS_FETCH.getCode())
                             .body(result);
    }

    // 상세 조회
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getDetail(@PathVariable("id") Long id) {
        ApiEntity entity = apiService.findById(id);
        if (entity != null) {
            return ResponseEntity.status(ResponseResult.SUCCESS_FETCH.getCode())
                                 .body(entity);
        } else {
            return ResponseEntity.status(ResponseResult.ERROR_NOT_FOUND.getCode())
                                 .body(ResponseResult.ERROR_NOT_FOUND.getMessage());
        }
    }

    // 저장
    @PostMapping("/insert")
    public ResponseEntity<?> insertGallery(@RequestBody ApiEntity apiEntity) throws Exception {
        if (apiEntity.getGalContentId() == null || apiEntity.getGalContentId().isEmpty()) {
            apiEntity.setGalContentId(System.currentTimeMillis() + "");
        }
        
        // 등록일 세팅
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        apiEntity.setGalCreatedTime(now);

        ApiEntity saved = apiService.save(apiEntity);
        
        return ResponseEntity.status(ResponseResult.SUCCESS_SAVE.getCode())
                             .body(ResponseResult.SUCCESS_SAVE.getMessage());
    }

    // 수정
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateGallery(@PathVariable("id") Long id, @RequestBody ApiEntity apiEntity) throws Exception {
        ApiEntity existing = apiService.findById(id);
        if (existing == null) {
            return ResponseEntity.status(ResponseResult.ERROR_NOT_FOUND.getCode())
                                 .body(ResponseResult.ERROR_NOT_FOUND.getMessage());
        }

        existing.setGalTitle(apiEntity.getGalTitle());
        existing.setGalPhotographer(apiEntity.getGalPhotographer());
        existing.setGalPhotographyLocation(apiEntity.getGalPhotographyLocation());
        existing.setGalPhotographyMonth(apiEntity.getGalPhotographyMonth());
        existing.setGalSearchKeyword(apiEntity.getGalSearchKeyword());
        existing.setGalWebImageUrl(apiEntity.getGalWebImageUrl());

        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        existing.setGalModifiedTime(now);

        apiService.save(existing);
        
        return ResponseEntity.status(ResponseResult.SUCCESS_UPDATE.getCode())
                             .body(ResponseResult.SUCCESS_UPDATE.getMessage());
    }

    // 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteGallery(@PathVariable("id") Long id) {
        ApiEntity existing = apiService.findById(id);
        if (existing == null) {
            return ResponseEntity.status(ResponseResult.ERROR_NOT_FOUND.getCode())
                                 .body(ResponseResult.ERROR_NOT_FOUND.getMessage());
        }

        apiService.delete(id);
        
        return ResponseEntity.status(ResponseResult.SUCCESS_DELETE.getCode())
                             .body(ResponseResult.SUCCESS_DELETE.getMessage());
    }

    // 이미지 업로드
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String uploadDir = "C:/dev/demo/uploads/";
            File dir = new File(uploadDir);
            if (!dir.exists() && !dir.mkdirs()) {
                return ResponseEntity.status(ResponseResult.ERROR_SERVER.getCode())
                                     .body("업로드 폴더 생성 실패: " + uploadDir);
            }

            String originalFileName = file.getOriginalFilename();
            if (originalFileName != null) {
                originalFileName = new String(originalFileName.getBytes("ISO-8859-1"), "UTF-8");
            }
            String fileName = System.currentTimeMillis() + "_" + originalFileName;
            File dest = new File(uploadDir + fileName);
            file.transferTo(dest);

            String filePath = "/uploads/" + fileName;
            return ResponseEntity.status(ResponseResult.SUCCESS_SAVE.getCode()).body(filePath);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(ResponseResult.ERROR_SERVER.getCode())
                                 .body(ResponseResult.ERROR_SERVER.getMessage() + ": " + e.getMessage());
        }
    }
}
