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
import ua.com.foxmineded.library.dto.BookDto;
import ua.com.foxmineded.library.exceptions.ServiceException;
import ua.com.foxmineded.library.services.BookService;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/books")
@RequiredArgsConstructor
public class BookController {
	private final BookService bookService;

	@Operation(summary = "Get all the books")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Books were returned", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json" ,schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Page.class, subTypes = BookDto.class))) })
	@GetMapping(value = "/search/all")
	Page<BookDto> findAll(@SortDefault(sort = "id") @PageableDefault(size = 10) final Pageable pageable) {
		return bookService.findAll(pageable);
	}

	@Operation(summary = "Get all the books by author name")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Books were returned", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json" ,schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Page.class, subTypes = BookDto.class))) })
	@GetMapping(value = "/search/author-name/{name}")
	Page<BookDto> findAllByAuthorName(@SortDefault(sort = "id") @PageableDefault(size = 10) final Pageable pageable,
			@Parameter(description = "The author name") @PathVariable String name) {
		return bookService.findAllByAuthorName(pageable, name);
	}
	
	@Operation(summary = "Get all the books by publisher name")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Books were returned", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json" ,schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Page.class, subTypes = BookDto.class))) })
	@GetMapping(value = "/search/publisher-name/{publisherName}")
	Page<BookDto> findAllByPublisherName(@SortDefault(sort = "id") @PageableDefault(size = 10) final Pageable pageable,
			@Parameter(description = "The publisher name") @PathVariable String publisherName) {
		return bookService.findAllByPublisherName(pageable, publisherName);
	}

	@Operation(summary = "Get all the books by age range")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Books were returned", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json" ,schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Page.class, subTypes = BookDto.class))) })
	@GetMapping(value = "/search/start-age/{startAge}/end-age/{endAge}")
	Page<BookDto> findAllByAgeRange(@SortDefault(sort = "id") @PageableDefault(size = 10) final Pageable pageable,
			@Parameter(description = "The age to start the search") @PathVariable Integer startAge, @Parameter(description = "The age to finish the search") @PathVariable Integer endAge) {
		return bookService.findAllByAgeRange(pageable, startAge, endAge);
	}

	@Operation(summary = "Get all the books by location name")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Books were returned", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json" ,schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Page.class, subTypes = BookDto.class))) })
	@GetMapping(value = "/search/location-name/{locationName}")
	Page<BookDto> findAllByLocationName(@SortDefault(sort = "id") @PageableDefault(size = 10) final Pageable pageable,
			@Parameter(description = "The location name") @PathVariable String locationName) {
		return bookService.findAllByLocationName(pageable, locationName);
	}

	@Operation(summary = "Get top 10 books by age range and location name")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Books were returned", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json" ,schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Page.class, subTypes = BookDto.class))) })
	@GetMapping(value = "/search/location-name/{locationName}/age-start/{ageStart}/age-end/{ageEnd}")
	Page<BookDto> findTop10ByLocationAndAgeRange(
			@SortDefault(sort = "id") @PageableDefault(size = 10) final Pageable pageable,
			@Parameter(description = "The location name") @PathVariable String locationName, @Parameter(description = "The age to start the searching") @PathVariable Integer ageStart, @Parameter(description = "The age to finish the searching") @PathVariable Integer ageEnd) {
		return bookService.findTop10ByLocationAndAgeRange(pageable, locationName, ageStart, ageEnd);
	}

	@Operation(summary = "Get the book by isbn")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Book was returned", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json" ,schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = BookDto.class))),
			@ApiResponse(responseCode = "404", description = "Book was not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json" , schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)) ) })
	@GetMapping(value = "/search/isbn/{isbn}")
	BookDto findByIsbn(@Parameter(description = "The book isbn") @PathVariable String isbn) {
		return bookService.findByIsbn(isbn).orElseThrow(() -> {
			String message = "The book with isbn %s was not found".formatted(isbn);
			log.error(message);
			throw new ResourceNotFoundException(message);
		});
	}

	@Operation(summary = "Get the book by book title")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Book was returned", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json" ,schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = BookDto.class))),
			@ApiResponse(responseCode = "404", description = "Book was not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json" , schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)) ) })
	@GetMapping(value = "/search/book-title/{bookTitle}")
	BookDto findByBookTitle(@Parameter(description = "The book title") @PathVariable String bookTitle) {
		return bookService.findByBookTitle(bookTitle).orElseThrow(() -> {
			String message = "The book with book title %s was not found".formatted(bookTitle);
			log.error(message);
			throw new ResourceNotFoundException(message);
		});
	}

	@Operation(summary = "Create a new book")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Book was created", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = BookDto.class))),
			@ApiResponse(responseCode = "400", description = "Book was not created", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)) ) })
	@PostMapping(value = "/creation")
	ResponseEntity<BookDto> create(@Parameter(description = "The new book dto") @RequestBody BookDto book) throws ServiceException {
		BookDto result = bookService.save(book);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	@Operation(summary = "Update a book")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Book was updated", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = BookDto.class))),
			@ApiResponse(responseCode = "400", description = "Book was not updated", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)) ) })
	@PutMapping(value = "/update")
	BookDto update(@Parameter(description = "The book dto to update") @RequestBody BookDto book) throws ServiceException {
		return bookService.save(book);
	}

	@Operation(summary = "Delete a book")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Book was deleted", content = @io.swagger.v3.oas.annotations.media.Content),
			@ApiResponse(responseCode = "404", description = "Book was not deleted", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)) ) })
	@DeleteMapping(value = "/deletion/{id}")
	ResponseEntity<Object> deleteById(@Parameter(description = "The id of the book to delete") @PathVariable Long id) {
		bookService.findById(id).ifPresentOrElse(value -> bookService.deleteById(id), () -> {
			String message = "The book with id = %d was not found".formatted(id);
			log.error(message);
			throw new ResourceNotFoundException(message);
		});
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
