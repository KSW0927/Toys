package com.demo.toy.comics;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.demo.toy.common.exception.NotFoundException;
import com.demo.toy.contents.ContentsEntity;
import com.demo.toy.contents.ContentsRepository;

@Service
public class ComicsService {

	private final ContentsRepository contentsRepository;
    private final ComicsRepository comicsRepository;

    public ComicsService(ContentsRepository contentsRepository, ComicsRepository comicsRepository) {
    	this.contentsRepository = contentsRepository;
        this.comicsRepository = comicsRepository;
    }

    /**
     * 특정 콘텐츠의 모든 권수 조회
     */
    public List<ComicsEntity> getComicsByContentId(Long contentId) {
        ContentsEntity content = contentsRepository.findByContentId(contentId)
                .orElseThrow(() -> new NotFoundException("콘텐츠를 찾을 수 없습니다. contentId=" + contentId));

        return comicsRepository.findByContent(content);
    }

    /**
     * 페이징 조회
     */
    public Page<ComicsEntity> getComicsByContentId(Long contentId, Pageable pageable) {
        ContentsEntity content = contentsRepository.findByContentId(contentId)
                .orElseThrow(() -> new NotFoundException("콘텐츠를 찾을 수 없습니다. contentId=" + contentId));

        return comicsRepository.findByContent(content, pageable);
    }

    /**
     * 권수 등록(단건)
     */
//    public ComicsEntity insertComics(Long contentId, ComicsDTO dto) {
//        ApiEntity content = apiRepository.findByContentId(contentId)
//                .orElseThrow(() -> new NotFoundException("콘텐츠를 찾을 수 없습니다. contentId=" + contentId));
//
//        ComicsEntity entity = new ComicsEntity();
//        entity.setComicsId(dto.getComicsId());
//        entity.setContent(content);
//        entity.setVolume(dto.getVolume());
//        entity.setPage(dto.getPage());
//        entity.setVolumePrice(dto.getVolumePrice() != null ? dto.getVolumePrice() : BigDecimal.ZERO);
//        entity.setVolumeImageUrl(dto.getVolumeImageUrl());
//        entity.setVolumeFileSize(dto.getVolumeFileSize());
//        entity.setRegDate(dto.getRegDate());
//
//        return comicsRepository.save(entity);
//    }

    /**
     * 권수 등록(배치)
     */
    public List<ComicsEntity> insertComicsBatch(Long contentId, List<ComicsDTO> dtoList) {
        ContentsEntity content = contentsRepository.findByContentId(contentId)
                .orElseThrow(() -> new NotFoundException("콘텐츠를 찾을 수 없습니다. contentId=" + contentId));
        
        List<ComicsEntity> entities = dtoList.stream().map(dto -> {
            ComicsEntity entity = new ComicsEntity();
            entity.setComicsId(dto.getComicsId());
            entity.setContent(content);
            entity.setVolume(dto.getVolume());
            entity.setPage(dto.getPage());
            entity.setVolumePrice(dto.getVolumePrice() != null ? dto.getVolumePrice() : BigDecimal.ZERO);
            entity.setVolumeImageUrl(dto.getVolumeImageUrl());
            entity.setVolumeFileSize(dto.getVolumeFileSize());
            entity.setRegDate(dto.getRegDate());
            return entity;
        }).toList();

        return comicsRepository.saveAll(entities);
    }
    
    /**
     * 권수 수정(배치)
     */
    public List<ComicsEntity> updateComicsBatch(Long contentId, List<ComicsDTO> dtoList) {
        // 1. 콘텐츠 존재 여부 확인 (외래 키 제약 조건 및 유효성 검사)
        // 등록 로직과 동일하게 해당 ContentId의 부모 콘텐츠가 존재하는지 확인합니다.
        ContentsEntity content = contentsRepository.findByContentId(contentId)
                .orElseThrow(() -> new NotFoundException("콘텐츠를 찾을 수 없습니다. contentId=" + contentId));

        // 2. DTO 리스트를 Entity 리스트로 변환 및 ID 유효성 검사
        List<ComicsEntity> entities = dtoList.stream().map(dto -> {
            // 수정 로직이므로, ComicsId가 반드시 존재해야 합니다.
            if (dto.getComicsId() == null) {
                throw new IllegalArgumentException("수정할 권수 정보(Comics ID)가 누락되었습니다.");
            }

            ComicsEntity entity = new ComicsEntity();
            
            // 🚨 중요: 기존 레코드를 수정하기 위해 ComicsId를 반드시 설정해야 합니다.
            entity.setComicsId(dto.getComicsId()); 
            
            // 외래 키 설정
            entity.setContent(content);
            
            // 데이터 필드 설정 (수정될 수 있는 필드)
            entity.setVolume(dto.getVolume());
            entity.setPage(dto.getPage());
            entity.setVolumePrice(dto.getVolumePrice() != null ? dto.getVolumePrice() : BigDecimal.ZERO);
            entity.setVolumeImageUrl(dto.getVolumeImageUrl());
            entity.setVolumeFileSize(dto.getVolumeFileSize());
            
            // 등록일 (필요하다면 수정 시간 필드를 별도로 관리할 수 있습니다.)
            entity.setRegDate(dto.getRegDate()); 
            
            return entity;
        }).toList();

        // 3. saveAll을 통한 일괄 수정 (DB ID가 Entity에 포함되어 있으면 UPDATE 실행)
        return comicsRepository.saveAll(entities);
    }

    /**
     * 권수 단건 삭제
     */
    public void deleteComics(Long comicsId) {
        ComicsEntity entity = comicsRepository.findByComicsId(comicsId)
                .orElseThrow(() -> new NotFoundException("권수를 찾을 수 없습니다. comicsId=" + comicsId));

        comicsRepository.delete(entity);
    }

    /**
     * 권수 단건 수정
     */
//    public ComicsEntity updateComics(Long comicsId, ComicsDTO dto) {
//        ComicsEntity entity = comicsRepository.findByComicsId(comicsId)
//                .orElseThrow(() -> new NotFoundException("권수를 찾을 수 없습니다. comicsId=" + comicsId));
//
//        entity.setVolume(dto.getVolume());
//        entity.setPage(dto.getPage());
//        entity.setVolumePrice(dto.getVolumePrice() != null ? dto.getVolumePrice() : BigDecimal.ZERO);
//        entity.setVolumeImageUrl(dto.getVolumeImageUrl());
//        entity.setVolumeFileSize(dto.getVolumeFileSize());
//        entity.setRegDate(dto.getRegDate());
//
//        return comicsRepository.save(entity);
//    }
}
