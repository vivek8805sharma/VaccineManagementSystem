package com.userService.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.userService.Entity.User;
import com.userService.Entity.UserDto;
import com.userService.Repository.UserRepository;
import com.userService.helper.MyException;

@Component
public class UserDatabase {

	@Autowired
	private UserRepository userRepo;

	public User addUser(User newUser) throws MyException {
		if (userRepo.findById(newUser.getUserName()).isPresent()) {
			throw new MyException("User already exists");
		}
		userRepo.save(newUser);
		return newUser;
	}

	public User getUser(String username) throws MyException {
		Optional<User> user = userRepo.findById(username);
		if (user.isEmpty()) {
			throw new MyException("User not found");
		}
		return user.get();
	}

	public List<User> getAllUsers() {
		return (List<User>) userRepo.findAll();
	}

	public void deleteUser(String username) throws Exception {
		if (userRepo.findById(username).isEmpty()) {
			throw new MyException("User not found");
		}
		userRepo.deleteById(username);
	}

	public User updateUser(UserDto user, String username) throws Exception {
		if (userRepo.findById(username).isEmpty()) {
			throw new MyException("User not found");
		}
		User updatedUser = new User(user.getName(), username, user.getEmail());
		userRepo.save(updatedUser);
		return updatedUser;
	}

}
