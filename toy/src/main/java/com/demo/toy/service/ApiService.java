package com.demo.toy.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
    
    public ApiEntity findById(Long  id) {
        return apiRepository.findById(id).orElse(null);
    }
    
    // 단건 저장
    public ApiEntity save(ApiEntity entity) {
        return apiRepository.save(entity);
    }
    
    public void delete(Long id) {
    	apiRepository.deleteById(id);
    }
}