package com.userService.ControllerTest;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.userService.Controller.UserController;
import com.userService.Entity.User;
import com.userService.Entity.UserDto;
import com.userService.dao.UserDatabase;
import com.userService.helper.ErrorDetails;
import com.userService.helper.MyException;

@AutoConfigureMockMvc
@WebMvcTest(value = UserController.class)
public class UserControllerTest {

	@Autowired
	MockMvc mvc;

	@MockBean
	UserDatabase userDb;

	@Test
	public void Should_ReturnAllUsersSuccessfully() throws Exception {
		List<User> allUsers = new ArrayList<>();
		User user1 = new User("Vivek", "viveksharma22", "viveksharma222@gmail");
		allUsers.add(user1);
		String userJson = new ObjectMapper().writeValueAsString(allUsers);
		when(userDb.getAllUsers()).thenReturn(allUsers);
		String expected = "[{\"name\":\"Vivek\",\"userName\":\"viveksharma22\",\"email\":\"viveksharma222@gmail\"}]";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(requestBuilder).andReturn();

		JSONAssert.assertEquals(userJson, result.getResponse().getContentAsString(), false);

	}

	@Test
	public void Should_GetUserSuccessfully() throws Exception {
		User user1 = new User("Vivek", "viveksharma22", "viveksharma222@gmail");
		String expected = new ObjectMapper().writeValueAsString(user1);
		when(userDb.getUser(Mockito.anyString())).thenReturn(user1);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/viveksharma22")
				.accept(MediaType.APPLICATION_JSON);
		mvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().string(expected));

	}

	@Test
	public void Should_AddNewUserSuccessfully() throws Exception {
		User user1 = new User("Vivek", "viveksharma22", "viveksharma222@gmail");
		String userJson = new ObjectMapper().writeValueAsString(user1);
		when(userDb.addUser(Mockito.any(User.class))).thenReturn(user1);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users").accept(MediaType.APPLICATION_JSON)
				.content(userJson).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	public void Should_UpdateUserSuccessfully() throws Exception {
		User user1 = new User("Vivek", "viveksharma22", "viveksharma222@gmail");
		String userJson = new ObjectMapper().writeValueAsString(user1);
		when(userDb.updateUser(Mockito.any(UserDto.class), Mockito.anyString())).thenReturn(user1);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users/viveksharma22")
				.accept(MediaType.APPLICATION_JSON).content(userJson).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	public void Should_DeleteUserSuccessfully() throws Exception {
		doNothing().when(userDb).deleteUser(Mockito.anyString());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/users/viveksharma22")
				.accept(MediaType.APPLICATION_JSON);
		mvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	public void Should_ThrowMyException() throws Exception {
		ErrorDetails err = new ErrorDetails(new Date().toString(), HttpStatus.BAD_REQUEST.name(), null,
				"uri=/users/viveksharma22");
		String expected = new ObjectMapper().writeValueAsString(err);
		when(userDb.getUser(Mockito.anyString())).thenThrow(MyException.class);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/viveksharma22")
				.accept(MediaType.APPLICATION_JSON);
		mvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(content().string(expected));

	}

	@Test
	public void Should_ThrowExceptionWhenArgumentInvalid() throws Exception {
		User user1 = new User("Vivek", "viveksharma22", "viveksharma222@");
		String userJson = new ObjectMapper().writeValueAsString(user1);
		// when(userDb.addUser(Mockito.any(User.class))).thenThrow(MethodArgumentNotValidException.class);

		ErrorDetails err = new ErrorDetails(new Date().toString(), HttpStatus.BAD_REQUEST.name(), "Invalid Email id\n",
				"uri=/users");
		String expected = new ObjectMapper().writeValueAsString(err);
		System.out.println(err.getPath() + "  " + err.getError());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users").accept(MediaType.APPLICATION_JSON)
				.content(userJson).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(content().string(expected));

	}

}
