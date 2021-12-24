package com.bookservice.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.bookservice.dao.BookDatabase;
import com.bookservice.entity.Book;
import com.bookservice.entity.BookDto;
import com.bookservice.helper.ErrorDetails;
import com.bookservice.helper.MyException;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@WebMvcTest(value = BookController.class)
public class BookControllerTest {

	@Autowired
	MockMvc mvc;

	@MockBean
	private BookDatabase bookDb;

	@Test
	public void Should_GetAllBooksSuccessfully() throws Exception {
		List<Book> allBooks = new ArrayList<>();
		Book book1 = new Book(1, "name", "publisger", "author");
		allBooks.add(book1);
		String expected = new ObjectMapper().writeValueAsString(allBooks);
		when(bookDb.getAllBooks()).thenReturn(allBooks);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books").accept(MediaType.APPLICATION_JSON);
		mvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().string(expected));

	}

	@Test
	public void Should_GetSingleBookSuccessfully() throws Exception {
		Book book1 = new Book(1, "name", "publisger", "author");
		String expected = new ObjectMapper().writeValueAsString(book1);
		when(bookDb.getBook(Mockito.anyInt())).thenReturn(book1);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books/1").accept(MediaType.APPLICATION_JSON);
		mvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().string(expected));

	}

	@Test
	public void Should_AddNewBookSuccessfully() throws Exception {
		Book book1 = new Book(1, "name", "publisger", "author");
		String bookJson = new ObjectMapper().writeValueAsString(book1);
		when(bookDb.addBook(Mockito.any(Book.class))).thenReturn(book1);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/books").accept(MediaType.APPLICATION_JSON)
				.content(bookJson).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	public void Should_DeleteBookSuccessfully() throws Exception {
		doNothing().when(bookDb).deleteBook(Mockito.anyInt());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/books/1").accept(MediaType.APPLICATION_JSON);

		mvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	public void Should_UpdateBookSuccessfully() throws Exception {
		Book book1 = new Book(1, "name", "publisger", "author");
		BookDto book = new BookDto("name", "publisger", "author");
		String bookJson = new ObjectMapper().writeValueAsString(book);
		when(bookDb.updateBook(Mockito.any(BookDto.class), Mockito.anyInt())).thenReturn(book1);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/books/1").accept(MediaType.APPLICATION_JSON)
				.content(bookJson).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	public void Should_ThrowMyException() throws Exception {
		ErrorDetails err = new ErrorDetails(new Date().toString(), HttpStatus.BAD_REQUEST.name(), null,
				"uri=/books/12");
		String expected = new ObjectMapper().writeValueAsString(err);
		when(bookDb.getBook(Mockito.anyInt())).thenThrow(MyException.class);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books/12").accept(MediaType.APPLICATION_JSON);
		mvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(content().string(expected));

	}

	@Test
	public void Should_ThrowExceptionWhenArgumentInvalid() throws Exception {
		Book book1 = new Book(1, null, "publisger", "author");
		String bookJson = new ObjectMapper().writeValueAsString(book1);

		ErrorDetails err = new ErrorDetails(new Date().toString(), HttpStatus.BAD_REQUEST.name(), "Invalid  id\n",
				"uri=/books");
		String expected = new ObjectMapper().writeValueAsString(err);
		System.out.println(err.getPath() + "  " + err.getError());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/books").accept(MediaType.APPLICATION_JSON)
				.content(bookJson).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(content().string(expected));

	}

}
