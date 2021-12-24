package com.LibraryService.BusinessLogic;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.LibraryService.Clients.BooksClient;
import com.LibraryService.Clients.UsersClient;
import com.LibraryService.Entity.Book;
import com.LibraryService.Repository.LibraryRepository;

@ExtendWith(MockitoExtension.class)
public class LibraryManagementServiceTest {
	
	@Mock
	LibraryRepository libraryRepo;
	@Mock
	private BooksClient bookClient;
	@Mock
	private UsersClient userClient;
	
	@InjectMocks
	LibraryManagementService libService;

	@Test
	public void Should_ReturnAllBooksSuccessfully() {
		List<Book> allBooks = new ArrayList<>();
		Book book1 = new Book(1, "name", "publisger", "author");
		allBooks.add(book1);
		when(bookClient.getAllBooks()).thenReturn(allBooks);
		Assertions.assertEquals(libService.getAllBooks(),allBooks);
	}
}
