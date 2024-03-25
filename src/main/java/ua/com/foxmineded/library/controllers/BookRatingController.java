package ua.com.foxmineded.library.controllers;

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
import ua.com.foxmineded.library.dto.BookRatingDto;
import ua.com.foxmineded.library.exceptions.ServiceException;
import ua.com.foxmineded.library.services.BookRatingService;

@Slf4j
@RestController
@RequestMapping(value = "api/v1/book-ratings")
@RequiredArgsConstructor
public class BookRatingController {
	private final BookRatingService bookRatingService;

	@GetMapping(value = "/search/all")
	public Page<BookRatingDto> findAll(@SortDefault(sort = "id") @PageableDefault(size = 10) final Pageable pageable) {
		return bookRatingService.findAll(pageable);
	}

	@GetMapping(value = "/search/book-id/{id}")
	public Page<BookRatingDto> findAllByBookId(
			@SortDefault(sort = "id") @PageableDefault(size = 10) final Pageable pageable, @PathVariable Long id) {
		return bookRatingService.findAllByBookId(pageable, id);
	}

	@PostMapping(value = "/creation")
	public ResponseEntity<BookRatingDto> create(@RequestBody BookRatingDto bookRatingDto) throws ServiceException {
		BookRatingDto result = bookRatingService.save(bookRatingDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	@PutMapping(value = "/update")
	public BookRatingDto update(@RequestBody BookRatingDto bookRatingDto) throws ServiceException {
		return bookRatingService.save(bookRatingDto);
	}

	@DeleteMapping(value = "/deletion/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable Long id) {
		bookRatingService.findById(id).ifPresentOrElse((v) -> bookRatingService.deleteById(id), () -> {
			String message = "The book rating with id = %d was not found".formatted(id);
			log.error(message);
			throw new ResourceNotFoundException(message);
		});
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
