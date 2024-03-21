package ua.com.foxmineded.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
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
import ua.com.foxmineded.library.dto.BookDto;
import ua.com.foxmineded.library.exceptions.ServiceException;
import ua.com.foxmineded.library.services.BookService;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/books")
@RequiredArgsConstructor
public class BookController {
	@Autowired
	private final BookService bookService;
	
	@GetMapping(value = "/search/all")
	Page<BookDto> findAll(@SortDefault(sort = "id") @PageableDefault(size = 10) final Pageable pageable) {
		return bookService.findAll(pageable);
	}
	
	@GetMapping(value = "/search/author-name/{name}")
	Page<BookDto> findAllByAuthorName(@SortDefault(sort = "id") @PageableDefault(size = 10) final Pageable pageable, @PathVariable String name) {
		return bookService.findAllByAuthorName(pageable, name);
	}
	
	@GetMapping(value = "/search/publisher-name/{publisherName}")
	Page<BookDto> findAllByPublisherName(@SortDefault(sort = "id") @PageableDefault(size = 10) final Pageable pageable, @PathVariable String publisherName) {
		return bookService.findAllByPublisherName(pageable, publisherName);
	}
	
	@GetMapping(value = "/search/start-age/{startAge}/end-age/{endAge}")
	Page<BookDto> findAllByAgeRange(@SortDefault(sort = "id") @PageableDefault(size = 10) final Pageable pageable, @PathVariable Integer startAge, @PathVariable Integer endAge) {
		return bookService.findAllByAgeRange(pageable, startAge, endAge);
	}
	
	@GetMapping(value = "/search/location-name/{locationName}")
	Page<BookDto> findAllByLocationName(@SortDefault(sort = "id") @PageableDefault(size = 10) final Pageable pageable, @PathVariable String locationName) {
		return bookService.findAllByLocationName(pageable, locationName);
	}
	
	@GetMapping(value = "/search/location-name/{locationName}/age-start/{ageStart}/age-end/{ageEnd}")
	Page<BookDto> findTop10ByLocationAndAgeRange(@SortDefault(sort = "id") @PageableDefault(size = 10) final Pageable pageable, @PathVariable String locationName, @PathVariable Integer ageStart, @PathVariable Integer ageEnd) {
		return bookService.findTop10ByLocationAndAgeRange(pageable, locationName, ageStart, ageEnd);
	}
	
	@GetMapping(value = "/search/isbn/{isbn}")
	BookDto findByIsbn(@PathVariable String isbn) throws ServiceException {
		return bookService.findByIsbn(isbn).orElseThrow(() -> {
			String message = "The book with isbn %s was not found".formatted(isbn);
			log.error(message);
			throw new ResourceNotFoundException(message);
		});
	}
	
	@GetMapping(value = "/search/book-title/{bookTitle}")
	BookDto findByBookTitle(@PathVariable String bookTitle) throws ServiceException {
		return bookService.findByBookTitle(bookTitle).orElseThrow(() -> {
			String message = "The book with book title %s was not found".formatted(bookTitle);
			log.error(message);
			throw new ResourceNotFoundException(message);
		});
	}
	
	@PostMapping(value = "/creation")
	BookDto create(@RequestBody BookDto book) {
		return bookService.save(book);
	}
	
	@PutMapping(value = "/update")
	BookDto update(@RequestBody BookDto book) {
		return bookService.save(book);
	}
	
	@DeleteMapping(value = "/deletion/{id}")
	void deleteById(@PathVariable Long id) {
		bookService.findById(id).ifPresentOrElse((value) -> bookService.deleteById(id), () -> {
			String message = "The book with id = %d was not found".formatted(id);
			log.error(message);
			throw new ResourceNotFoundException(message);
		});
	}
}
