package com.cogent.fooddeliveryapp.payload.response;

import java.util.List;

import lombok.Data;

/**
 * @author : Ki Beom Lee
 * @time : 2022. 2. 24.-오후 4:43:04
 */
@Data
public class JwtResponse {
	private String token;

	private String type = "Bearer ";

	private long id;
	private String username;
	private String email;
	private List<String> roles;
	
	public JwtResponse(String token, 	 long id, String username, String email, List<String> roles) {
		this.token = token;
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
	}
	
}
