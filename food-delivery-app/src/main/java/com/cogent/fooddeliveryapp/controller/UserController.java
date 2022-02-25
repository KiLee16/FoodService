package com.cogent.fooddeliveryapp.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cogent.fooddeliveryapp.IdNotFoundException;
import com.cogent.fooddeliveryapp.dto.Address;
import com.cogent.fooddeliveryapp.dto.Role;
import com.cogent.fooddeliveryapp.dto.User;
import com.cogent.fooddeliveryapp.enums.ERole;
import com.cogent.fooddeliveryapp.exception.MyOwnException;
import com.cogent.fooddeliveryapp.exception.NoDataFoundException;
import com.cogent.fooddeliveryapp.payload.request.SignupRequest;
import com.cogent.fooddeliveryapp.payload.request.UpdateRequest;
import com.cogent.fooddeliveryapp.payload.response.UserResponse;
import com.cogent.fooddeliveryapp.repository.RoleRepository;
import com.cogent.fooddeliveryapp.service.UserService;

@RestController
// /api
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleRepository roleRepository;

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteByUserId(@PathVariable("id") Long id) {
		// TODO: process DELETE request

		// check userid exists or not
		if (userService.existsById(id)) {
			// success part
			userService.deleteUserById(id);
			return ResponseEntity.noContent().build();
		} else {
			// failure part
			throw new NoDataFoundException("record not found");
		}
		// if exists then delete it
		// if not then throw exception.

	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getUserById(@PathVariable("id") long id) {

		User user = userService.getUserById(id).orElseThrow(() -> new NoDataFoundException("data not available"));
		// DTO ===> UserResponse()
		UserResponse userResponse = new UserResponse();
		userResponse.setEmail(user.getEmail());
		userResponse.setUsername(user.getUsername());
		Set<String> roles = new HashSet<>();
		userResponse.setDoj(user.getDoj());
		user.getRoles().forEach(e2 -> {
			roles.add(e2.getRoleName().name());
		});
		Set<com.cogent.fooddeliveryapp.payload.request.Address> addresses = new HashSet<>();
		user.getAddresses().forEach(e3 -> {
			com.cogent.fooddeliveryapp.payload.request.Address address2 = new com.cogent.fooddeliveryapp.payload.request.Address();
			address2.setHouseNumber(e3.getHouseNumber());
			address2.setCity(e3.getCity());
			address2.setCountry(e3.getCountry());
			address2.setState(e3.getState());
			address2.setStreet(e3.getStreet());
			address2.setZip(e3.getZip());
			addresses.add(address2);
		});
		userResponse.setAddress(addresses);
		userResponse.setRoles(roles);
		return ResponseEntity.status(200).body(userResponse);
	}

	@GetMapping(value = "/")
	public ResponseEntity<?> getAllUsers() {

		List<User> list = userService.getAllUsers();
		List<UserResponse> userResponses = new ArrayList<>();
		list.forEach(e -> {
			UserResponse userResponse = new UserResponse();
			userResponse.setEmail(e.getEmail());
			userResponse.setUsername(e.getUsername());
			Set<String> roles = new HashSet<>();
			userResponse.setDoj(e.getDoj());
			e.getRoles().forEach(e2 -> {
				roles.add(e2.getRoleName().name());
			});
			Set<com.cogent.fooddeliveryapp.payload.request.Address> addresses = new HashSet<>();
			e.getAddresses().forEach(e3 -> {
				com.cogent.fooddeliveryapp.payload.request.Address address2 = new com.cogent.fooddeliveryapp.payload.request.Address();
				address2.setHouseNumber(e3.getHouseNumber());
				address2.setCity(e3.getCity());
				address2.setCountry(e3.getCountry());
				address2.setState(e3.getState());
				address2.setStreet(e3.getStreet());
				address2.setZip(e3.getZip());
				addresses.add(address2);
			});
			userResponse.setAddress(addresses);
			userResponse.setRoles(roles);
			userResponses.add(userResponse);

		});
		if (userResponses.size() > 0)
			return ResponseEntity.ok(userResponses);
		else {
			throw new NoDataFoundException("no users are there");
		}
	}

	@PostMapping("/register")
	public ResponseEntity<?> createUser(@Valid @RequestBody SignupRequest signupRequest) {
		// can u create user object?
		// can u initialize the values based on the signuprequest object?
		Set<Role> roles = new HashSet<>();
		if (signupRequest.getRoles() == null) {
			Role userRole = roleRepository.findByRoleName(ERole.ROLE_USER)
					.orElseThrow(() -> new IdNotFoundException("RoleId not found exception"));
			roles.add(userRole);
		} else {
			signupRequest.getRoles().forEach(e -> {

				switch (e) {
				case "user":

					Role userRole = roleRepository.findByRoleName(ERole.ROLE_USER)
							.orElseThrow(() -> new IdNotFoundException("RoleId not found exception"));
					roles.add(userRole);
					break;
				case "admin":
					Role userAdmin = roleRepository.findByRoleName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new IdNotFoundException("RoleId not found exception"));
					roles.add(userAdmin);

				default:
					break;
				}

			});
		}

		User user = new User();

		Set<Address> addresses = new HashSet<>();
		signupRequest.getAddress().forEach(e -> {
			Address address = new Address();
			address.setCity(e.getCity());
			address.setCountry(e.getCountry());
			address.setHouseNumber(e.getHouseNumber());
			address.setState(e.getState());
			address.setStreet(e.getStreet());
			address.setUser(user);
			address.setZip(e.getZip());
			addresses.add(address);
		});
		user.setAddresses(addresses);
		user.setEmail(signupRequest.getEmail());
		user.setUsername(signupRequest.getName());
		user.setPassword(signupRequest.getPassword());
		user.setRoles(roles);
		user.setDoj(signupRequest.getDoj());

		User user2 = userService.addUser(user);

		return ResponseEntity.status(201).body(user2);

	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<?> updateUser(@PathVariable(value = "id") Long id, @Valid @RequestBody UpdateRequest info) {

		User previousData = userService.getUserById(info.getId()).orElseThrow(() -> new MyOwnException("no user"));
		User user = new User();
		user.setDoj(info.getDoj());
		user.setEmail(info.getEmail());

		user.setUsername(info.getName());
		user.setPassword(info.getPassword());
		Set<Address> addresses = new HashSet<>();
		info.getAddress().forEach(e -> {
			Address address = new Address();
			address.setCity(e.getCity());
			address.setCountry(e.getCountry());
			address.setHouseNumber(e.getHouseNumber());
			address.setState(e.getState());
			address.setStreet(e.getStreet());
			address.setZip(e.getZip());
			address.setUser(user);
			addresses.add(address);
		});

		user.setAddresses(addresses);

		

		Set<Role> roles = new HashSet<>();

		info.getRoles().forEach(e -> {

			switch (e) {
			case "user":

				Role userRole = roleRepository.findByRoleName(ERole.ROLE_USER)
						.orElseThrow(() -> new IdNotFoundException("RoleId not found exception"));
				roles.add(userRole);
				break;
			case "admin":
				Role userAdmin = roleRepository.findByRoleName(ERole.ROLE_ADMIN)
						.orElseThrow(() -> new IdNotFoundException("RoleId not found exception"));
				roles.add(userAdmin);

			default:
				break;
			}

		});
		user.setRoles(roles);
		User updated = userService.updateUser(user, id);
		
		return ResponseEntity.status(201).body(user);

	}
	
	

}
