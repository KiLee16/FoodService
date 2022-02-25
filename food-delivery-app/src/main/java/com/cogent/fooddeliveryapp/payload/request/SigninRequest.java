package com.cogent.fooddeliveryapp.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author : Ki Beom Lee
 * @time : 2022. 2. 24.-오후 4:20:51
 */
@Data
@AllArgsConstructor
public class SigninRequest {
	@NotBlank
	private String username;
	@NotBlank
	private String password; 
	
}	
