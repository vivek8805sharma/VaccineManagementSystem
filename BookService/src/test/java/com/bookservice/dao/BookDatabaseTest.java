package com.bookservice.dao;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bookservice.entity.Book;
import com.bookservice.entity.BookDto;
import com.bookservice.helper.MyException;
import com.bookservice.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
public class BookDatabaseTest {

	@Mock
	private BookRepository bookRepo;

	@InjectMocks
	private BookDatabase bookDb;

	@Test
	public void Should_AddBookSuccessfully() throws MyException {
		Book book1 = new Book(1, "name", "publisger", "author");
		when(bookRepo.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		Assertions.assertEquals(bookDb.addBook(book1), book1);
	}

	@Test
	public void Should_ThrowExceptionWhenGetExists() throws MyException {
		Book book1 = new Book(1, "name", "publisger", "author");
		when(bookRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(book1));
		Exception exception = Assertions.assertThrows(MyException.class, () -> bookDb.addBook(book1));
		Assertions.assertEquals(exception.getMessage(), "Book already exists");

	}

	@Test
	public void Should_GetBookSuccessfully() throws MyException {
		Book book1 = new Book(1, "name", "publisger", "author");
		when(bookRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(book1));
		Assertions.assertEquals(bookDb.getBook(1), book1);
	}

	@Test
	public void Should_ThrowExceptionWhenBookNotFound() throws MyException {
		Book book1 = new Book(1, "name", "publisger", "author");
		when(bookRepo.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		Exception exception = Assertions.assertThrows(MyException.class, () -> bookDb.getBook(1));
		Assertions.assertEquals(exception.getMessage(), "Book not found");

	}

	@Test
	public void Should_ReturnAllBooksSuccessfully() throws MyException {
		List<Book> allBooks = new ArrayList<>();
		Book book1 = new Book(1, "name", "publisger", "author");
		allBooks.add(book1);
		when(bookRepo.findAll()).thenReturn(allBooks);
		Assertions.assertEquals(bookDb.getAllBooks(), allBooks);
	}

	@Test
	public void Should_DeleteBookSuccessfully() throws Exception {
		Book book1 = new Book(1, "name", "publisger", "author");
		when(bookRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(book1));
		doNothing().when(bookRepo).deleteById(Mockito.anyInt());
		bookDb.deleteBook(1);
		verify(bookRepo, times(1)).findById(Mockito.anyInt());
		verify(bookRepo, times(1)).deleteById(Mockito.anyInt());
	}

	@Test
	public void Should_ThrowExceptionWhenDeleteBookNotFound() throws MyException {
		when(bookRepo.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		Exception exception = Assertions.assertThrows(MyException.class, () -> bookDb.deleteBook(1));
		Assertions.assertEquals(exception.getMessage(), "Book not found");

	}

	@Test
	public void Should_UpdateBookSuccessfully() throws Exception {
		Book book = new Book(1, "name", "publisger", "author");
		BookDto book1 = new BookDto("name", "publisger", "author");
		when(bookRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(book));
		when(bookRepo.save(Mockito.any(Book.class))).thenReturn(book);
		Book updatedBook = bookDb.updateBook(book1, 1);
		Assertions.assertEquals(updatedBook.getAuthor(), book.getAuthor());
		Assertions.assertEquals(updatedBook.getName(), book.getName());
		Assertions.assertEquals(updatedBook.getPublisher(), book.getPublisher());
		verify(bookRepo, times(1)).findById(Mockito.anyInt());
		verify(bookRepo, times(1)).save(Mockito.any(Book.class));
	}

	@Test
	public void Should_ThrowExceptionWhenUpdateBookNotFound() throws MyException {
		BookDto book1 = new BookDto("name", "publisger", "author");
		when(bookRepo.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		Exception exception = Assertions.assertThrows(MyException.class, () -> bookDb.updateBook(book1, 1));
		Assertions.assertEquals(exception.getMessage(), "Book not found");

	}
}
