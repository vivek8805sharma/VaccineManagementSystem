package com.bookservice.helper;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class MyExceptionHandler {

	@ExceptionHandler(MyException.class)
	public ResponseEntity<ErrorDetails> handleMyException(MyException ex, WebRequest req) {
		ErrorDetails err = new ErrorDetails(new Date().toString(), HttpStatus.BAD_REQUEST.name(), ex.getMessage(),
				req.getDescription(false));
		return ResponseEntity.badRequest().body(err);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDetails> handleMethodArguments(MethodArgumentNotValidException ex, WebRequest req) {
		StringBuilder messages = new StringBuilder();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			messages.append(error.getDefaultMessage()).append('\n');
		});
		ErrorDetails err = new ErrorDetails(new Date().toString(), HttpStatus.BAD_REQUEST.name(), messages.toString(),
				req.getDescription(false));
		return ResponseEntity.badRequest().body(err);
	}

}
