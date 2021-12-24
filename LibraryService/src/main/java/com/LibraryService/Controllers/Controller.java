package com.LibraryService.Controllers;

import java.util.List;

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

import com.LibraryService.BusinessLogic.LibraryManagementService;
import com.LibraryService.Entity.Book;
import com.LibraryService.Entity.Library;
import com.LibraryService.Entity.User;
import com.LibraryService.Entity.UserProfile;
import com.LibraryService.helper.MyException;

@RestController
@RequestMapping("/library")
public class Controller {

	@Autowired
	private LibraryManagementService libraryManagementService;

	@GetMapping("/books")
	public ResponseEntity<List<Book>> getAllBooks() {
		return ResponseEntity.ok().body(libraryManagementService.getAllBooks());
	}
	
	@GetMapping("/books/{bookId}")
	public ResponseEntity<Book> getBook(@PathVariable int bookId) {
		return libraryManagementService.getBook(bookId);
	}
	
	@PostMapping("/books")
	public ResponseEntity<?> addNewBook(@RequestBody Book newBook){
		libraryManagementService.addNewBook(newBook);
		return  ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@DeleteMapping("/books/{bookId}")
	public ResponseEntity<?> deleteBook(@PathVariable int bookId) {
		libraryManagementService.deleteBook(bookId);
		return  ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.ok().body(libraryManagementService.getAllUsers());
	}
	
//	@GetMapping("/users/{username}")
//	public ResponseEntity<User> getUser(@PathVariable String username) {
//		return ResponseEntity.ok().body(libraryManagementService.getUser(userId));
//	}
	
	@GetMapping("/users/{username}")
	public ResponseEntity<UserProfile> getUserProfile(@PathVariable String username) throws MyException{
		return ResponseEntity.ok().body(libraryManagementService.getUserProfile(username));
	}
	
	@PostMapping("/users")
	public ResponseEntity<?> addNewUser(@RequestBody User newUser){
		libraryManagementService.addNewUser(newUser);
		return  ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@DeleteMapping("/users/{username}/books/{bookId}")
	public ResponseEntity<?> releaseBook(@PathVariable String username, @PathVariable int bookId){
		libraryManagementService.releaseBook(username, bookId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	
	@PutMapping("/users/{username}")
	public ResponseEntity<?> updateUser(@RequestBody User user){
		libraryManagementService.updateUser(user);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
//	@DeleteMapping("/users/{userId}")
//	public ResponseEntity<?> deleteUser(@PathVariable int userId) {
//		libraryManagementService.deleteUser(userId);
//		return  ResponseEntity.status(HttpStatus.OK).build();
//	}
	
	
	
	@PostMapping("/users/{username}/books/{bookId}")
	public ResponseEntity<Library> issueBook(@PathVariable String username , @PathVariable int bookId) throws MyException{
		return ResponseEntity.ok().body(libraryManagementService.issueBook(username, bookId));
	}
	
	@DeleteMapping("/users/{username}")
	public ResponseEntity<?> releaseAllBook(@PathVariable String username){
		libraryManagementService.releaseAllBook(username);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	

	
//	@DeleteMapping("/users/{username}/books/{bookId}")
//	public ResponseEntity<?> releaseBook(@PathVariable String username, @PathVariable int bookId) {
//		libraryManagementService.releaseBook(username, bookId);
//		return  ResponseEntity.status(HttpStatus.OK).build();
//	}
	
//	@GetMapping("/users/{username}")
//	public ResponseEntity<Library> getUserProfile(@PathVariable String username){
//		return ResponseEntity.ok().body(libraryManagementService.getCurrentUserBooks(username));
//	}
	
	

}
