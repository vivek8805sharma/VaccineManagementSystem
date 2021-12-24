package com.LibraryService.ControllersTest;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import com.LibraryService.BusinessLogic.LibraryManagementService;
import com.LibraryService.Controllers.Controller;
import com.LibraryService.Entity.Book;
import com.LibraryService.Entity.Library;
import com.LibraryService.Entity.User;
import com.LibraryService.Entity.UserProfile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@WebMvcTest(value = Controller.class)
public class ControllersTest {
	@Autowired
	MockMvc mvc;

	@MockBean
	LibraryManagementService libraryManagementService;

	@Test
	public void Should_GetAllBooksSuccessfully() throws Exception {
		List<Book> allBooks = new ArrayList<>();
		Book book1 = new Book(1, "name", "publisger", "author");
		allBooks.add(book1);
		String expected = new ObjectMapper().writeValueAsString(allBooks);
		when(libraryManagementService.getAllBooks()).thenReturn(allBooks);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/library/books").accept(MediaType.APPLICATION_JSON);
		mvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().string(expected));

	}

	@Test
	public void Should_GetSingleBookSuccessfully() throws Exception {
		ResponseEntity<Book> book1 = ResponseEntity.ok().body(new Book(1, "name", "publisger", "author"));
		String expected = new ObjectMapper().writeValueAsString(book1);
		when(libraryManagementService.getBook(Mockito.anyInt())).thenReturn(book1);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/library/books/1")
				.accept(MediaType.APPLICATION_JSON);
		mvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().string(expected));

	}

	@Test
	public void Should_AddNewBookSuccessfully() throws Exception {
		Book book1 = new Book(1, "name", "publisger", "author");
		String bookJson = new ObjectMapper().writeValueAsString(book1);
		doNothing().when(libraryManagementService).addNewBook(Mockito.any(Book.class));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/library/books").accept(MediaType.APPLICATION_JSON)
				.content(bookJson).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	public void Should_DeleteBookSuccessfully() throws Exception {
		doNothing().when(libraryManagementService).deleteBook(Mockito.anyInt());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/library/books/1")
				.accept(MediaType.APPLICATION_JSON);

		mvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	public void Should_GetAllUsersSuccessfully() throws Exception {
		List<User> allUsers = new ArrayList<>();
		User user1 = new User("Vivek", "viveksharma22", "viveksharma222@gmail");
		allUsers.add(user1);
		String expected = new ObjectMapper().writeValueAsString(allUsers);
		when(libraryManagementService.getAllUsers()).thenReturn(allUsers);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/library/users").accept(MediaType.APPLICATION_JSON);
		mvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().string(expected));

	}

	@Test
	public void Should_GetUserSuccessfully() throws Exception {
		User user1 = new User("Vivek", "viveksharma22", "viveksharma222@gmail");
		List<Book> allBooks = new ArrayList<>();
		Book book1 = new Book(1, "name", "publisger", "author");
		allBooks.add(book1);
		UserProfile profile = new UserProfile();
		profile.setUser(user1);
		profile.setBooks(allBooks);
		String expected = new ObjectMapper().writeValueAsString(profile);
		when(libraryManagementService.getUserProfile(Mockito.anyString())).thenReturn(profile);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/library/users/viveksharma22")
				.accept(MediaType.APPLICATION_JSON);
		mvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().string(expected));

	}

	@Test
	public void Should_AddNewUserSuccessfully() throws Exception {
		User user1 = new User("Vivek", "viveksharma22", "viveksharma222@gmail");
		String userJson = new ObjectMapper().writeValueAsString(user1);
		doNothing().when(libraryManagementService).addNewUser(Mockito.any(User.class));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/library/users").accept(MediaType.APPLICATION_JSON)
				.content(userJson).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	public void Should_ReleaseBookSuccessfully() throws Exception {
		doNothing().when(libraryManagementService).releaseBook(Mockito.anyString(), Mockito.anyInt());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/library/users/vivekSharma350/books/1")
				.accept(MediaType.APPLICATION_JSON);

		mvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	public void Should_UpdateUserSuccessfully() throws Exception {
		User user1 = new User("Vivek", "viveksharma22", "viveksharma222@gmail");
		String userJson = new ObjectMapper().writeValueAsString(user1);
		doNothing().when(libraryManagementService).updateUser(Mockito.any(User.class));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/library/users/viveksharma22")
				.accept(MediaType.APPLICATION_JSON).content(userJson).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	public void Should_IssueBookSuccessfully() throws Exception {
		Library newLib = new Library("viveksharma22", 1);
		String newLibJson = new ObjectMapper().writeValueAsString(newLib);
		when(libraryManagementService.issueBook(Mockito.anyString(), Mockito.anyInt())).thenReturn(newLib);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/library/users/viveksharma22/books/1")
				.accept(MediaType.APPLICATION_JSON);
		mvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().string(newLibJson));

	}

	@Test
	public void Should_ReleaseAllBooksSuccessfully() throws Exception {
		doNothing().when(libraryManagementService).releaseAllBook(Mockito.anyString());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/library/users/vivekSharma350")
				.accept(MediaType.APPLICATION_JSON);

		mvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk());

	}

}
