package com.userService.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.userService.Entity.User;
import com.userService.Entity.UserDto;
import com.userService.dao.UserDatabase;
import com.userService.helper.MyException;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserDatabase userDb;

	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() throws MyException {
		List<User> allUsers = userDb.getAllUsers();
		return ResponseEntity.ok().body(allUsers);

	}

	@GetMapping("/{username}")
	public ResponseEntity<User> getUserfromUsername(@PathVariable @Valid String username) throws MyException {
		User user = userDb.getUser(username);
		return ResponseEntity.ok().body(user);

	}

	@PostMapping
	public ResponseEntity<?> addUser(@RequestBody @Valid User newUser) throws MyException {
		userDb.addUser(newUser);
		return ResponseEntity.status(HttpStatus.OK).build();

	}

	@DeleteMapping("/{username}")
	public ResponseEntity<?> deleteUser(@PathVariable @Valid String username) throws Exception {
		userDb.deleteUser(username);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PutMapping("/{username}")
	public ResponseEntity<?> updateUser(@RequestBody @Valid UserDto user, @PathVariable @Valid String username)
			throws Exception {
		userDb.updateUser(user, username);
		return ResponseEntity.status(HttpStatus.OK).build();

	}

}
