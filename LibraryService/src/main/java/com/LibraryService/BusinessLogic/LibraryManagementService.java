package com.LibraryService.BusinessLogic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.LibraryService.Clients.BooksClient;
import com.LibraryService.Clients.UsersClient;
import com.LibraryService.Entity.Book;
import com.LibraryService.Entity.Library;
import com.LibraryService.Entity.User;
import com.LibraryService.Entity.UserProfile;
import com.LibraryService.Repository.LibraryRepository;

@Component
public class LibraryManagementService {

	@Autowired
	LibraryRepository libraryRepo;
	@Autowired
	private BooksClient bookClient;
	@Autowired
	private UsersClient userClient;

	public List<Book> getAllBooks() {
		return bookClient.getAllBooks();
	}

	public Book getBook(int bookId) {
		return bookClient.getBook(bookId);
	}

	public void addNewBook(Book newBook) {
		bookClient.addNewBook(newBook);
	}

	public void deleteBook(int bookId) {
		bookClient.deleteBook(bookId);
	}

	public List<User> getAllUsers() {
		return userClient.getAllUsers();
	}

//	public User getUser(int userId) {
//		return userClient.getUser(userId);
//	}

	public User getUser(String userName) {
		return userClient.getUser(userName);
	}

	public void addNewUser(User newUser) {
		userClient.addNewUser(newUser);
	}

	public void deleteUser(int userId) {
		userClient.deleteUser(userId);
	}

	public void updateUser(User user) {
		userClient.updateUser(user);
	}

	public Library issueBook(String username, int bookId) {
		Library lib = new Library(username, bookId);
		libraryRepo.save(lib);
		return lib;
	}

	public UserProfile getUserProfile(String username) {
		List<Integer> userBook = getCurrentUserBooks(username);
		UserProfile up = new UserProfile();
		up.setUser(this.getUser(username));
		List<Book> bks = new ArrayList<>();
		for (int i = 0; i < userBook.size(); i++) {
			bks.add(this.getBook(userBook.get(i)));
		}
		up.setBooks(bks);
		return up;
	}

	//Return all the books ids of the given username
	public List<Integer> getCurrentUserBooks(String username) {
		return libraryRepo.findAllByUsername(username);
	}

	public void releaseBook(String username, int bookId) {
		libraryRepo.releaseBook(username, bookId);
	}
	
	public void releaseAllBook(String username) {
		libraryRepo.releaseAllBook(username);
	}

}
