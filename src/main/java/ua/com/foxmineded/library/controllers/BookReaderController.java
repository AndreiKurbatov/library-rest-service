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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.com.foxmineded.library.dto.BookReaderDto;
import ua.com.foxmineded.library.services.BookReaderService;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/book-readers")
@RequiredArgsConstructor
public class BookReaderController {
	private final BookReaderService bookReaderService;

	@Operation(summary = "Get all the book readers")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Book readers were returned", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json" ,schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Page.class, subTypes = BookReaderDto.class))) })
	@GetMapping(value = "/search/all")
	Page<BookReaderDto> findAll(@SortDefault(sort = "id") @PageableDefault(page = 10) final Pageable pageable) {
		return bookReaderService.findAll(pageable);
	}

	@Operation(summary = "Get all the book readers by age")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Book readers were returned", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json" ,schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Page.class, subTypes = BookReaderDto.class))) })
	@GetMapping(value = "/search/age/{age}")
	Page<BookReaderDto> findAllByAge(@SortDefault(sort = "id") @PageableDefault(page = 10) final Pageable pageable,
			@Parameter(description = "The book reader age") @PathVariable Integer age) {
		return bookReaderService.findAllByAge(pageable, age);
	}

	@Operation(summary = "Get the book reader by book isbn")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Book reader was returned", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json" ,schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = BookReaderDto.class))),
			@ApiResponse(responseCode = "404", description = "Book reader was not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json" , schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)) ) })
	@GetMapping(value = "/search/book-reader-id/{bookReaderId}")
	BookReaderDto findByBookReaderId(@Parameter(description = "The book reader id") @PathVariable Long bookReaderId) {
		return bookReaderService.findByBookReaderId(bookReaderId).orElseThrow(() -> {
			String message = "The book reader with book reader id = %d was not found".formatted(bookReaderId);
			log.error(message);
			throw new ResourceNotFoundException(message);
		});
	}

	@Operation(summary = "Create a new book reader")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Book reader was created", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = BookReaderDto.class))),
			@ApiResponse(responseCode = "400", description = "Book reader was not created", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)) ) })
	@PostMapping(value = "/creation")
	ResponseEntity<BookReaderDto> create(@Parameter(description = "The new book reader dto") @RequestBody BookReaderDto bookReaderDto) {
		BookReaderDto result = bookReaderService.save(bookReaderDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	@Operation(summary = "Update a book reader")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Book reader was updated", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = BookReaderDto.class))),
			@ApiResponse(responseCode = "400", description = "Book reader was not updated", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)) ) })
	@PutMapping(value = "/update")
	BookReaderDto update(@Parameter(description = "The book reader dto to update") @RequestBody BookReaderDto bookReaderDto) {
		return bookReaderService.save(bookReaderDto);
	}

	@Operation(summary = "Delete a book reader")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Book reader was deleted", content = @io.swagger.v3.oas.annotations.media.Content),
			@ApiResponse(responseCode = "404", description = "Book reader was not deleted", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)) ) })
	@DeleteMapping(value = "/deletion/{id}")
	ResponseEntity<Object> deleteById(@Parameter(description = "The id of the book reader to delete") @PathVariable Long id) {
		bookReaderService.findById(id).ifPresentOrElse((v) -> bookReaderService.deleteById(id), () -> {
			String message = "The book with id = %d was not found".formatted(id);
			log.error(message);
			throw new ResourceNotFoundException(message);
		});
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
