package com.demo.toy.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.demo.toy.entity.ApiEntity;
import com.demo.toy.service.ApiService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final ApiService apiService;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    @PostMapping("/sync")
    public List<ApiEntity> apiSync() throws Exception {

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

                // galContentId로 중복 데이터 체크
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

        return apiService.saveAll(newEntities);
    }
    
    // 전체 검색 + 페이징
    @GetMapping("/data")
    public Page<ApiEntity> searchAll(
    	    @RequestParam(name = "page", defaultValue = "0") int page,
    	    @RequestParam(name = "size", defaultValue = "9") int size
            ) {
        return apiService.getAllData(page, size);
    }

    // 조회 + 페이징
    @GetMapping("/data/search")
    public Page<ApiEntity> searchByGalPhotographyLocation(
    		@RequestParam("galPhotographyLocation") String galPhotographyLocation,
    	    @RequestParam(name = "page", defaultValue = "0") int page,
    	    @RequestParam(name = "size", defaultValue = "9") int size
    		) {
        return apiService.searchByGalPhotographyLocation(galPhotographyLocation, page, size);
    }
    
    // 상세 조회
    @GetMapping("/data/{id}")
    public ResponseEntity<ApiEntity> getDetail(@PathVariable("id") Long id) {
        ApiEntity entity = apiService.findById(id);
        if (entity != null) {
            return ResponseEntity.ok(entity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // 이미지 업로드 API
    @PostMapping("/data/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // 1) 실제 저장 경로 (윈도우)
            String uploadDir = "C:/dev/demo/uploads/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                boolean created = dir.mkdirs();
                if (!created) {
                    throw new IOException("업로드 폴더 생성 실패: " + uploadDir);
                }
            }

            // 2) 파일 이름 (중복 방지용 timestamp 붙임)
            String originalFileName = file.getOriginalFilename();
            if (originalFileName != null) {
                originalFileName = new String(originalFileName.getBytes("ISO-8859-1"), "UTF-8"); // 한글 처리
            }
            String fileName = System.currentTimeMillis() + "_" + originalFileName;
            File dest = new File(uploadDir + fileName);

            // 3) 파일 저장
            file.transferTo(dest);

            // 4) 프론트/DB에 보낼 경로 (웹에서 접근 가능한 상대 URL)
            String filePath = "/uploads/" + fileName;

            return ResponseEntity.ok(filePath);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("파일 업로드 실패: " + e.getMessage());
        }
    }

    // 저장
    @PostMapping("/data/insert")
    public ApiEntity insertGallery(@RequestBody ApiEntity apiEntity) throws Exception {
        if (apiEntity.getGalContentId() == null || apiEntity.getGalContentId().isEmpty()) {
            apiEntity.setGalContentId(System.currentTimeMillis() + ""); 
        }
        
        // 등록 일시 세팅
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        apiEntity.setGalCreatedTime(now);

        return apiService.save(apiEntity);
    }
}
