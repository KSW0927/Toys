package com.demo.toy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.toy.entity.ApiEntity;

public interface ApiRepository extends JpaRepository<ApiEntity, Long> {
	// 데이터 동기화 시 PK값으로 중복 체크
//	boolean existsByGalContentId(String galContentId);
	
	Page<ApiEntity> findByTitleContaining(String title, Pageable pageable);
}
