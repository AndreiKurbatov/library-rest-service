package ua.com.foxmineded.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.com.foxmineded.library.dto.BookReaderDto;
import ua.com.foxmineded.library.services.BookReaderService;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/book-readers")
@RequiredArgsConstructor
public class BookReaderController {
	@Autowired
	private final BookReaderService bookReaderService;
	
	@GetMapping(value = "/search/all")
	Page<BookReaderDto> findAll(@SortDefault(sort = "id") @PageableDefault(page = 10) final Pageable pageable) {
		return bookReaderService.findAll(pageable);
	}
	
	@GetMapping(value = "/search/age/{age}")
	Page<BookReaderDto> findAllByAge(@SortDefault(sort = "id") @PageableDefault(page = 10) final Pageable pageable, @PathVariable Integer age) {
		return bookReaderService.findAllByAge(pageable, age);
	}
	
	@GetMapping(value = "/search/book-reader-id/{bookReaderId}")
	BookReaderDto findByBookReaderId(@PathVariable Long bookReaderId) {
		return bookReaderService.findByBookReaderId(bookReaderId).orElseThrow(() -> {
			String message = "The book reader with book reader id = %d was not found".formatted(bookReaderId);
			log.error(message);
			throw new ResourceNotFoundException(message);
		});
	}
	
	@PostMapping(value = "/creation")
	ResponseEntity<BookReaderDto> create(@RequestBody BookReaderDto bookReaderDto) {
		BookReaderDto result = bookReaderService.save(bookReaderDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}
	
	@PutMapping(value = "/update")
	BookReaderDto update(@RequestBody BookReaderDto bookReaderDto) {
		return bookReaderService.save(bookReaderDto);
	}
	
	@DeleteMapping(value = "/deletion/{id}")
	ResponseEntity<Object> deleteById(@PathVariable Long id) {
		bookReaderService.findById(id).ifPresentOrElse((v) -> bookReaderService.deleteById(id), () -> {
			String message = "The book with id = %d was not found".formatted(id);
			log.error(message);
			throw new ResourceNotFoundException(message);
		});
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
