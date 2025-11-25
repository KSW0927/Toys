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
     * 만화 메타데이터 조회
     */
    public List<ComicsEntity> getComicsByContentId(Long contentId) {
        ContentsEntity content = contentsRepository.findByContentId(contentId)
                .orElseThrow(() -> new NotFoundException("콘텐츠를 찾을 수 없습니다. contentId=" + contentId));

        return comicsRepository.findByContent(content);
    }

    /**
     * 만화 메타데이터 조회(페이징)
     */
    public Page<ComicsEntity> getComicsByContentId(Long contentId, Pageable pageable) {
        ContentsEntity content = contentsRepository.findByContentId(contentId)
                .orElseThrow(() -> new NotFoundException("콘텐츠를 찾을 수 없습니다. contentId=" + contentId));

        return comicsRepository.findByContent(content, pageable);
    }

    /**
     * 만화 메타데이터 등록
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
            return entity;
        }).toList();

        return comicsRepository.saveAll(entities);
    }
    
    /**
     * 만화 메타데이터 수정
     */
    public List<ComicsEntity> updateComicsBatch(Long contentId, List<ComicsDTO> dtoList) {
        ContentsEntity content = contentsRepository.findByContentId(contentId)
                .orElseThrow(() -> new NotFoundException("콘텐츠를 찾을 수 없습니다. contentId=" + contentId));

        List<ComicsEntity> entities = dtoList.stream().map(dto -> {
            if (dto.getComicsId() == null) {
                throw new IllegalArgumentException("수정할 만화 정보(Comics ID)가 누락되었습니다.");
            }

            ComicsEntity entity = new ComicsEntity();
            
            entity.setComicsId(dto.getComicsId()); 
            entity.setContent(content);
            entity.setVolume(dto.getVolume());
            entity.setPage(dto.getPage());
            entity.setVolumePrice(dto.getVolumePrice() != null ? dto.getVolumePrice() : BigDecimal.ZERO);
            entity.setVolumeImageUrl(dto.getVolumeImageUrl());
            entity.setVolumeFileSize(dto.getVolumeFileSize());
            
            return entity;
        }).toList();

        return comicsRepository.saveAll(entities);
    }

    /**
     * 만화 메타데이터 삭제
     */
    public void deleteComics(Long comicsId) {
        ComicsEntity entity = comicsRepository.findByComicsId(comicsId)
                .orElseThrow(() -> new NotFoundException("만화를 찾을 수 없습니다. comicsId=" + comicsId));

        comicsRepository.delete(entity);
    }
}
