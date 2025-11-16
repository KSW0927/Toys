package com.demo.toy.user;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.toy.common.exception.BusinessException;
import com.demo.toy.common.jwt.JwtTokenProvider;
import com.demo.toy.common.redis.RedisService;
import com.demo.toy.common.response.ResponseResult;
import com.demo.toy.user.dto.LoginResponseDTO;
import com.demo.toy.user.dto.SignUpRequestDTO;
import com.demo.toy.user.dto.SignUpResponseDTO;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserService {
	
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final StringRedisTemplate redisTemplate;
    
    
    public UserService
    	(
    		UserRepository userRepository,
    		PasswordEncoder passwordEncoder,
    		JwtTokenProvider jwtTokenProvider,
    		RedisService redisService,
    		StringRedisTemplate redisTemplate
    	)
	    {
	        this.userRepository = userRepository;
	        this.passwordEncoder = passwordEncoder;
	        this.jwtTokenProvider = jwtTokenProvider;
	        this.redisTemplate = redisTemplate;
	
	    }
    
    @Transactional
    public SignUpResponseDTO signUp(SignUpRequestDTO dto) {
        // 아이디 중복 체크
        if (userRepository.existsByUserId(dto.getUserId())) {
            throw new BusinessException(ResponseResult.ERROR_DUPLICATE);
        }

        // 비밀번호 암호화 및 엔티티 생성
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        UserEntity user = new UserEntity();
        user.SignUpEntity(dto);

        // DB 저장
        userRepository.save(user);

        // 응답 DTO 반환
        return new SignUpResponseDTO(
        		user.getUserId(), 
        		user.getUsername() + "님 회원가입 되었습니다.");
    }
    
    @Transactional(readOnly = true)
    public LoginResponseDTO login(String userId, String password) {
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ResponseResult.ERROR_NOT_FOUND));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessException(ResponseResult.ERROR_INVALID_PASSWORD);
        }

        String accessToken = jwtTokenProvider.createAccessToken(user.getUserId());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getUserId());

        // Redis에 refreshToken 저장 (7일 만료)
        redisTemplate.opsForValue().set(user.getUserId(), refreshToken, Duration.ofDays(7));

        // LoginResponseDTO 반환
        return new LoginResponseDTO(user.getUserNo(), user.getUsername(),accessToken, refreshToken);
    }
    
    // 로그아웃
    public void logout(Long userNo) {
        // Redis에 저장된 Refresh Token 삭제
        String key = "refreshToken:" + userNo;
        redisTemplate.delete(key);
    }
}
