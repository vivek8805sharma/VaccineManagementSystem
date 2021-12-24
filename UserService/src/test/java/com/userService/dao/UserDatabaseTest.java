package com.userService.dao;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
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

import com.userService.Entity.User;
import com.userService.Entity.UserDto;
import com.userService.Repository.UserRepository;
import com.userService.helper.MyException;

@ExtendWith(MockitoExtension.class)
public class UserDatabaseTest {
	@Mock
	private UserRepository userRepo;
	@InjectMocks
	UserDatabase userDb;

	@Test
	public void Should_AddUserSuccessfully() throws MyException {
		User user1 = new User("Vivek", "viveksharma22", "viveksharma222@gmail");
		when(userRepo.findById(Mockito.anyString())).thenReturn(Optional.empty());
		Assertions.assertEquals(userDb.addUser(user1), user1);
	}

	@Test
	public void Should_ThrowExceptionWhenUserExists() throws MyException {
		User user1 = new User("Vivek", "viveksharma22", "viveksharma222@gmail");
		when(userRepo.findById(Mockito.anyString())).thenReturn(Optional.of(user1));
		Exception exception = Assertions.assertThrows(MyException.class, () -> userDb.addUser(user1));
		Assertions.assertEquals(exception.getMessage(), "User already exists");

	}

	@Test
	public void Should_GetUserSuccessfully() throws MyException {
		User user1 = new User("Vivek", "viveksharma22", "viveksharma222@gmail");
		when(userRepo.findById(Mockito.anyString())).thenReturn(Optional.of(user1));
		Assertions.assertEquals(userDb.getUser("viveksharma22"), user1);
	}

	@Test
	public void Should_ThrowExceptionWhenUserNotFound() throws MyException {
		User user1 = new User("Vivek", "viveksharma22", "viveksharma222@gmail");
		when(userRepo.findById(Mockito.anyString())).thenReturn(Optional.empty());
		Exception exception = Assertions.assertThrows(MyException.class, () -> userDb.getUser("viveksharma22"));
		Assertions.assertEquals(exception.getMessage(), "User not found");

	}

	@Test
	public void Should_ReturnAllUserSuccessfully() throws MyException {
		List<User> allUsers = new ArrayList<>();
		User user1 = new User("Vivek", "viveksharma22", "viveksharma222@gmail");
		allUsers.add(user1);
		when(userRepo.findAll()).thenReturn(allUsers);
		Assertions.assertEquals(userDb.getAllUsers(), allUsers);
	}

	@Test
	public void Should_DeleteUserSuccessfully() throws Exception {
		User user1 = new User("Vivek", "viveksharma22", "viveksharma222@gmail");
		when(userRepo.findById(Mockito.anyString())).thenReturn(Optional.of(user1));
		doNothing().when(userRepo).deleteById(Mockito.anyString());
		userDb.deleteUser("viveksharma22");
		verify(userRepo, times(1)).findById(Mockito.anyString());
		verify(userRepo, times(1)).deleteById(Mockito.anyString());
	}

	@Test
	public void Should_ThrowExceptionWhenDeleteUserNotFound() throws MyException {
		User user1 = new User("Vivek", "viveksharma22", "viveksharma222@gmail");
		when(userRepo.findById(Mockito.anyString())).thenReturn(Optional.empty());
		Exception exception = Assertions.assertThrows(MyException.class, () -> userDb.deleteUser("viveksharma22"));
		Assertions.assertEquals(exception.getMessage(), "User not found");

	}

	@Test
	public void Should_UpdateUserSuccessfully() throws Exception {
		User user = new User("Vivek", "viveksharma22", "viveksharma222@gmail.com");
		UserDto user1 = new UserDto();
		user1.setEmail("viveksharma222@gmail.com");
		user1.setName("Vivek");
		when(userRepo.findById(Mockito.anyString())).thenReturn(Optional.of(user));
		when(userRepo.save(Mockito.any(User.class))).thenReturn(user);
		User updatedUser = userDb.updateUser(user1, "viveksharma22");
		Assertions.assertEquals(updatedUser.getEmail(), user.getEmail());
		Assertions.assertEquals(updatedUser.getName(), user.getName());
		Assertions.assertEquals(updatedUser.getUserName(), user.getUserName());
		verify(userRepo, times(1)).findById(Mockito.anyString());
		verify(userRepo, times(1)).save(Mockito.any(User.class));
	}
	
	@Test
	public void Should_ThrowExceptionWhenUpdateUserNotFound() throws MyException {
		UserDto user1 = new UserDto();
		when(userRepo.findById(Mockito.anyString())).thenReturn(Optional.empty());
		Exception exception = Assertions.assertThrows(MyException.class, () -> userDb.updateUser(user1,"viveksharma22"));
		Assertions.assertEquals(exception.getMessage(), "User not found");

	}

}
