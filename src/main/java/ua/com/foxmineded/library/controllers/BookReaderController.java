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
import ua.com.foxmineded.library.dto.BookReaderDto;
import ua.com.foxmineded.library.exceptions.ServiceException;
import ua.com.foxmineded.library.services.BookReaderService;

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
	BookReaderDto findByBookReaderId(@PathVariable Long bookReaderId) throws ServiceException {
		return bookReaderService.findByBookReaderId(bookReaderId);
	}
	
	@PostMapping(value = "/creation")
	BookReaderDto create(@RequestBody BookReaderDto bookReaderDto) {
		return bookReaderService.save(bookReaderDto);
	}
	
	@PutMapping(value = "/update")
	BookReaderDto update(@RequestBody BookReaderDto bookReaderDto) {
		return bookReaderService.save(bookReaderDto);
	}
	
	@DeleteMapping(value = "/deletion/{bookReaderId}")
	void deleteByBookReaderId(@PathVariable Long bookReaderId) {
		bookReaderService.deleteByBookReaderId(bookReaderId);
	}
}
