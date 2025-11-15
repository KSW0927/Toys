package com.demo.toy.comics;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.demo.toy.common.exception.NotFoundException;
import com.demo.toy.entity.ApiEntity;
import com.demo.toy.repository.ApiRepository;

@Service
public class ComicsService {

    private final ComicsRepository comicsRepository;
    private final ApiRepository apiRepository;

    public ComicsService(ComicsRepository comicsRepository, ApiRepository apiRepository) {
        this.comicsRepository = comicsRepository;
        this.apiRepository = apiRepository;
    }

    /**
     * 특정 콘텐츠의 모든 권수 조회
     */
    public List<ComicsEntity> getComicsByContentId(String contentId) {
        ApiEntity content = apiRepository.findByContentId(contentId)
                .orElseThrow(() -> new NotFoundException("콘텐츠를 찾을 수 없습니다. contentId=" + contentId));

        return comicsRepository.findByContent(content);
    }

    /**
     * 페이징 조회
     */
    public Page<ComicsEntity> getComicsByContentId(String contentId, Pageable pageable) {
        ApiEntity content = apiRepository.findByContentId(contentId)
                .orElseThrow(() -> new NotFoundException("콘텐츠를 찾을 수 없습니다. contentId=" + contentId));

        return comicsRepository.findByContent(content, pageable);
    }

    /**
     * 권수 등록(단건)
     */
    public ComicsEntity insertComics(String contentId, ComicsDTO dto) {
        ApiEntity content = apiRepository.findByContentId(contentId)
                .orElseThrow(() -> new NotFoundException("콘텐츠를 찾을 수 없습니다. contentId=" + contentId));

        ComicsEntity entity = new ComicsEntity();
        entity.setComicsId(dto.getComicsId());
        entity.setContent(content);
        entity.setVolume(dto.getVolume());
        entity.setPage(dto.getPage());
        entity.setVolumePrice(dto.getVolumePrice() != null ? dto.getVolumePrice() : BigDecimal.ZERO);
        entity.setVolumeImageUrl(dto.getVolumeImageUrl());
        entity.setVolumeFileSize(dto.getVolumeFileSize());
        entity.setRegDate(dto.getRegDate());

        return comicsRepository.save(entity);
    }

    /**
     * 권수 등록(배치)
     */
    public List<ComicsEntity> insertComicsBatch(String contentId, List<ComicsDTO> dtoList) {
        ApiEntity content = apiRepository.findByContentId(contentId)
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
     * 권수 단건 삭제
     */
    public void deleteComics(String comicsId) {
        ComicsEntity entity = comicsRepository.findByComicsId(comicsId)
                .orElseThrow(() -> new NotFoundException("권수를 찾을 수 없습니다. comicsId=" + comicsId));

        comicsRepository.delete(entity);
    }

    /**
     * 권수 단건 수정
     */
    public ComicsEntity updateComics(String comicsId, ComicsDTO dto) {
        ComicsEntity entity = comicsRepository.findByComicsId(comicsId)
                .orElseThrow(() -> new NotFoundException("권수를 찾을 수 없습니다. comicsId=" + comicsId));

        entity.setVolume(dto.getVolume());
        entity.setPage(dto.getPage());
        entity.setVolumePrice(dto.getVolumePrice() != null ? dto.getVolumePrice() : BigDecimal.ZERO);
        entity.setVolumeImageUrl(dto.getVolumeImageUrl());
        entity.setVolumeFileSize(dto.getVolumeFileSize());
        entity.setRegDate(dto.getRegDate());

        return comicsRepository.save(entity);
    }
}
