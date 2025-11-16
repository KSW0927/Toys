package com.demo.toy.user.dto;

public class LoginResponseDTO {
    private Long userNo;
    private String username;
    private String accessToken;
    private String refreshToken;

    public LoginResponseDTO(Long userNo, String username, String accessToken, String refreshToken) {
    	this.userNo = userNo;
    	this.username = username;
    	this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
    
    public Long getUserNo() {
    	return userNo;
    }
    
    public String getUsername() {
        return username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
