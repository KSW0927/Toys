package com.demo.toy.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.toy.common.exception.BusinessException;
import com.demo.toy.common.response.ResponseResult;
import com.demo.toy.dto.SignUpRequestDTO;
import com.demo.toy.dto.SignUpResponseDTO;
import com.demo.toy.entity.UserEntity;
import com.demo.toy.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Transactional
    public SignUpResponseDTO signUp(SignUpRequestDTO dto) {
        // 1. 아이디 중복 체크
        if (userRepository.existsByUsername(dto.getUserId())) {
            throw new BusinessException(ResponseResult.ERROR_DUPLICATE);
        }

        // 2. Entity 생성 & 비밀번호 암호화
        UserEntity user = new UserEntity();
        user.SignUpEntity(dto);

        // 3. DB 저장
        userRepository.save(user);

        // 4. 응답 DTO 생성
        return new SignUpResponseDTO(
        		user.getUserId(), 
        		user.getUsername() + "님 회원가입 되었습니다.");
    }
}
