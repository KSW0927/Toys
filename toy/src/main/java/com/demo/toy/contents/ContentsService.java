package com.demo.toy.contents;

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

@Service
public class ContentsService {

    private final ContentsRepository contentsRepository;
    
    @Value("${file.upload-dir}")
    private String uploadDir;

    public ContentsService(ContentsRepository contentsRepository, RestTemplate restTemplate) {
        this.contentsRepository = contentsRepository;
    }

    /**
     * 목록
     */
    public Page<ContentsEntity> getContentsList(ContentsSearchParamsDTO searchDTO) {
        Pageable pageable = PageRequest.of(
            searchDTO.getPage(),
            searchDTO.getSize(),
            Sort.by(Sort.Direction.DESC, "regDate")
        );

        return contentsRepository.findByTitleContaining(searchDTO.getTitle(), pageable);
    }
    
    /**
     * 저장
     */
    @Transactional
    public ContentsEntity insertContent(ContentsEntity entity) {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        entity.setRegDate(now);
        return contentsRepository.save(entity);
    }
    
    /**
     * 상세
     */
    public ContentsEntity getContentDetail(Long contentId) {
    	return contentsRepository.findById(contentId).orElseThrow(() -> new NotFoundException("contentId=" + contentId));
    }
    
    
    /**
     * 수정
     */
    @Transactional
    public ContentsEntity updateContent(Long contentId, ContentsDTO dto) {
        ContentsEntity entity = contentsRepository.findById(contentId).orElseThrow(() -> new IllegalArgumentException("contentId" + contentId));
        entity.update(dto);
        return entity;
    }
    
    /**
     * 삭제
     */
    public void deleteContent(Long contentId) {
        if (!contentsRepository.existsById(contentId)) {
            throw new IllegalArgumentException("contentId=" + contentId);
        }
        contentsRepository.deleteById(contentId);
    }
    
    /**
     * 업로드
     */
    public String uploadContentImage(MultipartFile file) {
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