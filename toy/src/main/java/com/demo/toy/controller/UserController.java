package com.demo.toy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.toy.common.response.ApiResponse;
import com.demo.toy.common.response.ResponseResult;
import com.demo.toy.dto.LoginRequestDTO;
import com.demo.toy.dto.LoginResponseDTO;
import com.demo.toy.dto.SignUpRequestDTO;
import com.demo.toy.dto.SignUpResponseDTO;
import com.demo.toy.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "회원 API", description = "회원가입, 로그인")
public class UserController {

    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signUp")
    @Operation(summary = "회원가입", description = "사용자 회원가입 처리")
    public ResponseEntity<ApiResponse<SignUpResponseDTO>> signUp(@RequestBody @Valid SignUpRequestDTO dto) {
        SignUpResponseDTO signUpData = userService.signUp(dto);
        ApiResponse<SignUpResponseDTO> response = new ApiResponse<>(ResponseResult.SUCCESS_SIGNUP, signUpData);
        return ResponseEntity.status(response.getCode()).body(response);
    }
    
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "사용자 로그인 처리")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@RequestBody @Valid LoginRequestDTO dto) {
        LoginResponseDTO loginData = userService.login(dto.getUserId(), dto.getPassword());
        ApiResponse<LoginResponseDTO> response = new ApiResponse<>(ResponseResult.SUCCESS_LOGIN, loginData);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PostMapping("/logout/{userNo}")
    @Operation(summary = "로그아웃", description = "사용자 로그아웃 처리")
    public ResponseEntity<ApiResponse<Void>> logout(@PathVariable("userNo") Long userNo) {
        userService.logout(userNo);
        ApiResponse<Void> response = new ApiResponse<>(ResponseResult.SUCCESS_LOGOUT, null);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
