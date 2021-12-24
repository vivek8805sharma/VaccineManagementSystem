package com.LibraryService.Clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.LibraryService.Entity.Book;

@FeignClient(name = "BookService")
public interface BooksClient {

	@RequestMapping(method = RequestMethod.GET, value = "/books")
	public List<Book> getAllBooks();

	@RequestMapping(method = RequestMethod.GET, value = "/books/{bookId}")
	public ResponseEntity<Book> getBook(@PathVariable("bookId") int id);

	@RequestMapping(method = RequestMethod.POST, value = "/books")
	public ResponseEntity<?> addNewBook(@RequestBody Book newBook);
	
	@DeleteMapping("/books/{bookId}")
	public ResponseEntity<?> deleteBook(@PathVariable int bookId);

}
