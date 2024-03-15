package ua.com.foxmineded.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import ua.com.foxmineded.library.dto.BookDto;
import ua.com.foxmineded.library.exceptions.ServiceException;
import ua.com.foxmineded.library.services.BookService;

@RestController
@RequestMapping(value = "/api/v1/books")
@RequiredArgsConstructor
public class BookController {
	@Autowired
	private final BookService bookService;
	
	@GetMapping(value = "/search/all")
	Page<BookDto> findAll(@SortDefault(sort = "id") @PageableDefault(size = 10) Pageable pageable) {
		return bookService.findAll(pageable);
	}
	
	@GetMapping(value = "/search/author-name/{name}")
	Page<BookDto> findAllByAuthorName(@SortDefault(sort = "id") @PageableDefault(size = 10) Pageable pageable, @PathVariable String name) {
		return bookService.findAllByAuthorName(name, pageable);
	}
	
	@GetMapping(value = "/search/publisher-name/{publisherName}")
	Page<BookDto> findAllByPublisherName(@SortDefault(sort = "id") @PageableDefault(size = 10) Pageable pageable, @PathVariable String publisherName) {
		return bookService.findAllByPublisherName(publisherName, pageable);
	}
	
	@GetMapping(value = "/search/isbn/{isbn}")
	BookDto findByIsbn(@PathVariable String isbn) throws ServiceException {
		return bookService.findByIsbn(isbn);
	}
	
	@GetMapping(value = "/search/book-title/{bookTitle}")
	BookDto findByBookTitle(@PathVariable String bookTitle) throws ServiceException {
		return bookService.findByBookTitle(bookTitle);
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
		bookService.deleteById(id);
	}
}
