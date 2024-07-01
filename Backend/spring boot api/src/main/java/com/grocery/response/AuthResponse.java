package com.grocery.response;
 
 
 
import com.grocery.domain.USER_ROLE;

import lombok.Data;
 
public class AuthResponse {
	private String message;
	private String jwt;
	private USER_ROLE role;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getJwt() {
		return jwt;
	}
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	public USER_ROLE getRole() {
		return role;
	}
	public void setRole(USER_ROLE role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "AuthResponse [message=" + message + ", jwt=" + jwt + ", role=" + role + "]";
	}
	public AuthResponse(String message, String jwt, USER_ROLE role) {
		super();
		this.message = message;
		this.jwt = jwt;
		this.role = role;
	}
	public AuthResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

 
 
}

