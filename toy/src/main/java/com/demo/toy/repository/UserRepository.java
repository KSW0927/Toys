package com.demo.toy.repository;

import com.demo.toy.entity.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // username으로 사용자 조회
    Optional<UserEntity> findByUsername(String username);

    // userId로 사용자 조회
    Optional<UserEntity> findByUserId(String userId);

    // username 중복 체크
    boolean existsByUsername(String username);
    
    // userId 중복 체크
    boolean existsByUserId(String userId);
}
