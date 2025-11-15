package com.demo.toy.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    
    @Value("${file.upload-dir}")
    private String uploadDir;

    public ApiService(ApiRepository apiRepository, RestTemplate restTemplate) {
        this.apiRepository = apiRepository;
    }

    /**
     * 목록
     */
    public Page<ApiEntity> getPictureList(ApiSearchParamsDTO searchDTO) {
        Pageable pageable = PageRequest.of(
            searchDTO.getPage(),
            searchDTO.getSize(),
            Sort.by(Sort.Direction.DESC, "regDate")
        );

        return apiRepository.findByTitleContaining(searchDTO.getTitle(), pageable);
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
        if (entity.getContentId() == null || entity.getContentId().isEmpty()) {
            entity.setContentId(String.valueOf(System.currentTimeMillis()));
        }

        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        entity.setRegDate(now);

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