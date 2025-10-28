package com.demo.toy.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.demo.toy.common.exception.FileUploadException;
import com.demo.toy.common.exception.NotFoundException;
import com.demo.toy.dto.ApiDTO;
import com.demo.toy.dto.ApiSearchParamsDTO;
import com.demo.toy.entity.ApiEntity;
import com.demo.toy.repository.ApiRepository;

@Service
public class ApiService {

    private final ApiRepository apiRepository;
    private final RestTemplate restTemplate;
    
    @Value("${file.upload-dir}")
    private String uploadDir;

    public ApiService(ApiRepository apiRepository, RestTemplate restTemplate) {
        this.apiRepository = apiRepository;
        this.restTemplate = restTemplate;
    }

    public ApiEntity initApiData(ApiDTO dto) {
        ApiEntity entity = new ApiEntity(
                dto.getGalContentId(),
                dto.getGalContentTypeId(),
                dto.getGalTitle(),
                dto.getGalWebImageUrl(),
                dto.getGalCreatedTime(),
                dto.getGalModifiedTime(),
                dto.getGalPhotographyMonth(),
                dto.getGalPhotographyLocation(),
                dto.getGalPhotographer(),
                dto.getGalSearchKeyword()
        );
        return apiRepository.save(entity);
    }
    
    /**
     * 동기화
     */
    public List<ApiEntity> syncPicture() {
    	String SERVICE_KEY = "XsHEUftHrR8BU7rVbvE/4y3hbn4XXwfS5BpxrMBbTZhGNWqI935dAI6i97WctO3EoAW5saabVeBUWG2qSOp3xg==";
    	String BASE_URL = "https://apis.data.go.kr/B551011/PhotoGalleryService1/galleryList1";
    	
        int numOfRows = 1000;
        int pageNo = 1;
        int totalCount = Integer.MAX_VALUE;
        List<ApiEntity> newEntities = new ArrayList<>();

        while ((pageNo - 1) * numOfRows < totalCount) {
            String apiUrl = BASE_URL
                    + "?serviceKey=" + SERVICE_KEY
                    + "&numOfRows=" + numOfRows
                    + "&pageNo=" + pageNo
                    + "&MobileOS=ETC&MobileApp=AppTest&arrange=A&_type=json";

            Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);
            Map<String, Object> body = (Map<String, Object>) ((Map<String, Object>) response.get("response")).get("body");

            if (totalCount == Integer.MAX_VALUE) {
                totalCount = ((Number) body.get("totalCount")).intValue();
            }

            Map<String, Object> items = (Map<String, Object>) body.get("items");
            List<Map<String, Object>> itemList = (List<Map<String, Object>>) items.get("item");

            for (Map<String, Object> item : itemList) {
                String galContentId = (String) item.get("galContentId");
                if (!apiRepository.existsByGalContentId(galContentId)) {
                    ApiEntity entity = new ApiEntity(
                            galContentId,
                            (String) item.get("galContentTypeId"),
                            (String) item.get("galTitle"),
                            (String) item.get("galWebImageUrl"),
                            (String) item.get("galCreatedtime"),
                            (String) item.get("galModifiedtime"),
                            (String) item.get("galPhotographyMonth"),
                            (String) item.get("galPhotographyLocation"),
                            (String) item.get("galPhotographer"),
                            (String) item.get("galSearchKeyword")
                    );
                    newEntities.add(entity);
                }
            }

            pageNo++;
        }

        if (!newEntities.isEmpty()) {
            return apiRepository.saveAll(newEntities);
        }

        return newEntities;
    }

    /**
     * 목록
     */
    public Page<ApiEntity> getPictureList(ApiSearchParamsDTO searchDTO) {
        Pageable pageable = PageRequest.of(
            searchDTO.getPage(),
            searchDTO.getSize(),
            Sort.by(Sort.Direction.DESC, "galCreatedTime")
        );

        return apiRepository.findByGalPhotographyLocationContaining(searchDTO.getLocation(), pageable);
    }
    
    /**
     * 상세
     */
    public ApiEntity getPictureDetail(Long id) {
    	return apiRepository.findById(id).orElseThrow(() -> new NotFoundException("id=" + id));
    }
    
    /**
     * 저장
     */
    @Transactional
    public ApiEntity insertPicture(ApiEntity entity) {
        if (entity.getGalContentId() == null || entity.getGalContentId().isEmpty()) {
            entity.setGalContentId(String.valueOf(System.currentTimeMillis()));
        }

        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        entity.setGalCreatedTime(now);

        return apiRepository.save(entity);
    }
    
    /**
     * 수정
     */
    @Transactional
    public ApiEntity updatePicture(Long id, ApiDTO dto) {
        ApiEntity entity = apiRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id=" + id));
        entity.update(dto);
        return entity;
    }
    
    /**
     * 삭제
     */
    public void deletePicture(Long id) {
        if (!apiRepository.existsById(id)) {
            throw new IllegalArgumentException("id=" + id);
        }
        apiRepository.deleteById(id);
    }
    
    /**
     * 업로드
     */
    public String uploadPicture(MultipartFile file) {
        try {
            File dir = new File(uploadDir);
            if (!dir.exists() && !dir.mkdirs()) {
                throw new FileUploadException("업로드 폴더 생성 실패");
            }

            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null) {
                throw new FileUploadException("파일 이름이 없습니다.");
            }

            String fileName = UUID.randomUUID() + "_" + originalFileName;
            File dest = new File(uploadDir, fileName);
            file.transferTo(dest);

            return "/uploads/" + fileName;

        } catch (IOException e) {
            throw new FileUploadException("파일 업로드 실패", e);
        }
    }
}