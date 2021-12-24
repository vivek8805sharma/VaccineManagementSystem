package com.bookservice.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookservice.entity.Book;
import com.bookservice.entity.BookDto;
import com.bookservice.helper.MyException;
import com.bookservice.repository.BookRepository;

@Component
public class BookDatabase {

	@Autowired
	private BookRepository bookRepo;

	public Book addBook(Book newBook) throws MyException {
		if (bookRepo.findById(newBook.getId()).isPresent()) {
			throw new MyException("Book already exists");
		}
		bookRepo.save(newBook);
		return newBook;
	}

	public Book getBook(int id) throws MyException {
		Optional<Book> book = bookRepo.findById(id);
		if (book.isEmpty()) {
			throw new MyException("Book not found");
		}
		return book.get();
	}

	public List<Book> getAllBooks() {
		return (List<Book>) bookRepo.findAll();
	}

	public void deleteBook(int id) throws MyException {
		if (bookRepo.findById(id).isEmpty()) {
			throw new MyException("Book not found");
		}
		bookRepo.deleteById(id);
	}

	public Book updateBook(BookDto book, int id) throws MyException {
		if (bookRepo.findById(id).isEmpty()) {
			throw new MyException("Book not found");
		}
		Book updatedBook = new Book(id, book.getName(), book.getPublisher(), book.getAuthor());
		bookRepo.save(updatedBook);
		return updatedBook;
	}

}
