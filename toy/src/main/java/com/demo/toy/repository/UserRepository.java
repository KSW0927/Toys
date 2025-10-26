package com.demo.toy.repository;

import com.demo.toy.entity.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // username으로 사용자 조회
    Optional<UserEntity> findByUsername(String username);

    // username 중복 체크
    boolean existsByUsername(String username);
}
