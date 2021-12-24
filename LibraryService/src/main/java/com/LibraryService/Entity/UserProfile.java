package com.LibraryService.Entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfile {
	
	private User user;
	private List<Book> books;

}
