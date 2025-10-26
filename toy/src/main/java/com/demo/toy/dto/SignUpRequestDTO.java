package com.demo.toy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignUpRequestDTO {
    private String username;
    private String userId;
    private String password;
    private String email;
    
    public SignUpRequestDTO() {}
    
    // 생성자
    public SignUpRequestDTO(String username, String userId, String password, String email) {
        this.username = username;
        this.userId = userId;
        this.password = password;
        this.email = email;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
