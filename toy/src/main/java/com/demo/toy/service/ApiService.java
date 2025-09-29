package com.demo.toy.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.demo.toy.common.NotFoundException;
import com.demo.toy.dto.ApiDTO;
import com.demo.toy.entity.ApiEntity;
import com.demo.toy.repository.ApiRepository;

@Service
public class ApiService {

    private final ApiRepository apiRepository;

    public ApiService(ApiRepository apiRepository) {
        this.apiRepository = apiRepository;
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
    
    // 데이터 동기화 시 PK값으로 중복 체크
    public boolean existsByGalContentId(String galContentId) {
        return apiRepository.existsByGalContentId(galContentId);
    }
    
    // 데이터 동기화
    public List<ApiEntity> saveAll(List<ApiEntity> entityList) {
        return apiRepository.saveAll(entityList);
    }
    
    // 지역 검색 조회 + 페이징
    public Page<ApiEntity> searchByGalPhotographyLocation(String galPhotographyLocation, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "galCreatedTime"));
        return apiRepository.findByGalPhotographyLocationContaining(galPhotographyLocation, pageable);
    }
    
//    public ApiEntity findById(Long  id) {
//        return apiRepository.findById(id).orElse(null);
//    }
    
    public ApiEntity getDetail(Long id) {
    	return apiRepository.findById(id).orElseThrow(() -> new NotFoundException("id=" + id + " 데이터 없음"));
    }
    
    // 저장
    @Transactional
    public ApiEntity save(ApiEntity entity) {
        // galContentId 없을 경우 생성
        if (entity.getGalContentId() == null || entity.getGalContentId().isEmpty()) {
            entity.setGalContentId(String.valueOf(System.currentTimeMillis()));
        }

        // 생성일 세팅
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        entity.setGalCreatedTime(now);

        return apiRepository.save(entity);
    }
    
    // 수정
    @Transactional
    public ApiEntity update(Long id, ApiDTO dto) {
        ApiEntity entity = apiRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 엔티티가 없습니다. id=" + id));
        entity.update(dto);
        return entity;
    }
    
    // 삭제
    public void delete(Long id) {
        if (!apiRepository.existsById(id)) {
            throw new IllegalArgumentException("삭제할 엔티티가 없습니다. id=" + id);
        }
        apiRepository.deleteById(id);
    }
    
    // 업로드
    public String uploadFile(MultipartFile file) {
        try {
            String uploadDir = "C:/dev/demo/uploads/";
            File dir = new File(uploadDir);
            if (!dir.exists() && !dir.mkdirs()) {
                throw new RuntimeException("업로드 폴더 생성 실패: " + uploadDir);
            }

            String originalFileName = file.getOriginalFilename();
            if (originalFileName != null) {
                originalFileName = new String(originalFileName.getBytes("ISO-8859-1"), "UTF-8");
            }

            String fileName = System.currentTimeMillis() + "_" + originalFileName;
            File dest = new File(uploadDir + fileName);
            file.transferTo(dest);

            return "/uploads/" + fileName;

        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 실패", e);
        }
    }
}