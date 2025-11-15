package com.demo.toy.comics;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.demo.toy.entity.ApiEntity;

public interface ComicsRepository extends JpaRepository<ComicsEntity, Long> {

    /**
     * comicsId로 단건 조회
     */
    Optional<ComicsEntity> findByComicsId(String comicsId);

    /**
     * 부모 콘텐츠(ApiEntity)로 권수 목록 조회
     */
    List<ComicsEntity> findByContent(ApiEntity content);

    /**
     * 부모 콘텐츠(ApiEntity)로 페이징 조회
     */
    Page<ComicsEntity> findByContent(ApiEntity content, Pageable pageable);

    /**
     * 삭제 시 존재 여부 확인용
     */
    boolean existsByComicsId(String comicsId);
}
