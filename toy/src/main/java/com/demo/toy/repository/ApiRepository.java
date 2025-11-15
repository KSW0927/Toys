package com.demo.toy.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.toy.entity.ApiEntity;

public interface ApiRepository extends JpaRepository<ApiEntity, Long> {
    Optional<ApiEntity> findByContentId(String contentId);
    
	Page<ApiEntity> findByTitleContaining(String title, Pageable pageable);
}
