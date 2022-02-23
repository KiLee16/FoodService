package com.cogent.fooddeliveryapp.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cogent.fooddeliveryapp.dto.User;
import com.cogent.fooddeliveryapp.exception.NoDataFoundException;
import com.cogent.fooddeliveryapp.repository.UserRepository;
import com.cogent.fooddeliveryapp.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	
	UserRepository userRepository;
	@Override
	public User addUser(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

	@Override
	public Optional<User> getUserById(long id) {
		// TODO Auto-generated method stub
		
		return userRepository.findById(id);
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public String deleteUserById(long id) {
		// TODO Auto-generated method stub
		userRepository.deleteById(id);
		return "success";
	}

	@Override
	public User updateUser(User user, long id ) {
		// TODO Auto-generated method stub
//		User user2 = userRepository.findById(user.getId()).get();
//		userRepository.saveAll(null)
		User prevUser = userRepository.findById(id).orElseThrow(()-> new NoDataFoundException("No user with this id"));
		prevUser.setAddresses(user.getAddresses());
		prevUser.setDoj(user.getDoj());
		prevUser.setEmail(user.getEmail());
		prevUser.setUsername(user.getUsername());
		prevUser.setPassword(user.getPassword());
		prevUser.setRoles(user.getRoles());
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUsersAscOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getAllUsersDescOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(long id) {
		// TODO Auto-generated method stub
		return userRepository.existsById(id);
	}

}
