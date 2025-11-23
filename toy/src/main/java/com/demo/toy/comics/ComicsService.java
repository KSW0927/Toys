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
        ContentsEntity content = contentsRepository.findByContentId(contentId)
                .orElseThrow(() -> new NotFoundException("콘텐츠를 찾을 수 없습니다. contentId=" + contentId));

        List<ComicsEntity> entities = dtoList.stream().map(dto -> {
            if (dto.getComicsId() == null) {
                throw new IllegalArgumentException("수정할 권수 정보(Comics ID)가 누락되었습니다.");
            }

            ComicsEntity entity = new ComicsEntity();
            
            entity.setComicsId(dto.getComicsId()); 
            
            // 외래 키 설정
            entity.setContent(content);
            
            // 데이터 필드 설정 (수정될 수 있는 필드)
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
     * 권수 단건 삭제
     */
    public void deleteComics(Long comicsId) {
        ComicsEntity entity = comicsRepository.findByComicsId(comicsId)
                .orElseThrow(() -> new NotFoundException("권수를 찾을 수 없습니다. comicsId=" + comicsId));

        comicsRepository.delete(entity);
    }
}
