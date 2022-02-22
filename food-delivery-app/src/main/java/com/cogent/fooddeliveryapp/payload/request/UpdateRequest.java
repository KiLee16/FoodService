package com.cogent.fooddeliveryapp.payload.request;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Ki Beom Lee
 * @time : 2022. 2. 22.-���� 12:08:25
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRequest {
	@NotNull
	private long id;
	@NotBlank
	private String email;
	@NotBlank
	private String password;
	@NotBlank
	private String name;
	@NotEmpty
	private Set<Address> address;
	@JsonFormat(pattern = "MM-dd-yyyy")
	private LocalDate doj;
	@NotEmpty
	private Set<String> roles;
}
