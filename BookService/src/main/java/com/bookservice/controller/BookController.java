package com.bookservice.controller;

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

import com.bookservice.dao.BookDatabase;
import com.bookservice.entity.Book;
import com.bookservice.entity.BookDto;
import com.bookservice.helper.MyException;

@RestController
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookDatabase bookDb;

	@GetMapping
	public ResponseEntity<List<Book>> getAllBooks() {
		List<Book> allBooks = bookDb.getAllBooks();
		return ResponseEntity.ok().body(allBooks);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Book> getBook(@PathVariable @Valid int id) throws MyException {
		Book book = bookDb.getBook(id);
		return ResponseEntity.ok().body(book);
	}

	@PostMapping
	public ResponseEntity<?> addBook(@RequestBody @Valid Book newBook) throws MyException {
		bookDb.addBook(newBook);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteBook(@PathVariable @Valid int id) throws MyException {
		bookDb.deleteBook(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateBook(@RequestBody @Valid BookDto book, @PathVariable int id) throws MyException {
		bookDb.updateBook(book, id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
