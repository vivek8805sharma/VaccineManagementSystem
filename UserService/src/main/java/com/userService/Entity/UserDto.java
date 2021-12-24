package com.userService.Entity;

import javax.validation.constraints.Email;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

	@NotNull
	private String name;
	@Email(message = "Invalid Email id")
	private String email;
}
