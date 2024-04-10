package ua.com.foxmineded.libraryrestservice.controllers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.validation.ConstraintViolationException;
import ua.com.foxmineded.libraryrestservice.exceptions.ServiceException;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).header("exceptionMessage", ex.getMessage()).build();
	}

	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<Object> handleServiceException(ServiceException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("exceptionMessage", ex.getMessage()).build();
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("exceptionMessage", ex.getMessage()).build();
	}
}
