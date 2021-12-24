package com.userService.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class ErrorDetails {

	private String timestamp;
	private String status;
	private String error;
	private String path;
	
}
