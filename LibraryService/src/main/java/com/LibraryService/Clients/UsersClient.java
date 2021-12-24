package com.LibraryService.Clients;

import java.util.List;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.LibraryService.Entity.Book;
import com.LibraryService.Entity.User;

@FeignClient(name = "UserService")
@LoadBalancerClient(name="UserService")
public interface UsersClient {

	@RequestMapping(method = RequestMethod.GET, value = "/users")
	public List<User> getAllUsers();
//
//	@RequestMapping(method = RequestMethod.GET, value = "/users/{userId}")
//	public User getUser(@PathVariable("userId") int id);
	
	@RequestMapping(method = RequestMethod.GET, value = "/users/{username}")
	public ResponseEntity<?> getUser(@PathVariable("username") String username);

	@RequestMapping(method = RequestMethod.POST, value = "/users")
	public ResponseEntity<?> addNewUser(@RequestBody User newUser);
	
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable int userId);
	
	@PutMapping("/users/{id}")
	public ResponseEntity<?> updateUser(@RequestBody User user);
}
