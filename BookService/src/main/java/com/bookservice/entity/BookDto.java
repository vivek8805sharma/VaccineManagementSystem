package com.bookservice.entity;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class BookDto {

	@NotNull
	private String name;
	@NotNull
	private String publisher;
	@NotNull
	private String author;
}
