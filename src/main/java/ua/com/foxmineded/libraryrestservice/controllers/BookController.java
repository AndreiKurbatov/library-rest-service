package ua.com.foxmineded.libraryrestservice.controllers;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.com.foxmineded.libraryrestservice.dto.BookDto;
import ua.com.foxmineded.libraryrestservice.exceptions.ServiceException;
import ua.com.foxmineded.libraryrestservice.services.AuthorService;
import ua.com.foxmineded.libraryrestservice.services.BookService;
import ua.com.foxmineded.libraryrestservice.services.PublisherService;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/books")
@RequiredArgsConstructor
public class BookController {
	private final BookService bookService;
	private final PublisherService publisherService;
	private final AuthorService authorService;

	@Operation(summary = "Get all the books")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Books were returned", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Page.class, subTypes = BookDto.class))) })
	@GetMapping(value = "/search/all", params = { "pageable" })
	Page<BookDto> findAll(@RequestParam("pageable") int page,
			@SortDefault(sort = "id") @PageableDefault(size = 10, page = 0) final Pageable pageable) {
		return bookService.findAll(pageable.withPage(page));
	}

	@Operation(summary = "Get all the books by author name")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Books were returned", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Page.class, subTypes = BookDto.class))) })
	@GetMapping(value = "/search/author-name/{name}", params = { "pageable" })
	Page<BookDto> findAllByAuthorName(@RequestParam("pageable") int page,
			@SortDefault(sort = "id") @PageableDefault(size = 10, page = 0) final Pageable pageable,
			@Parameter(description = "The author name") @PathVariable String name) {
		return bookService.findAllByAuthorName(pageable.withPage(page), name);
	}

	@Operation(summary = "Get all the books by publisher name")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Books were returned", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Page.class, subTypes = BookDto.class))) })
	@GetMapping(value = "/search/publisher-name/{publisherName}", params = { "pageable" })
	Page<BookDto> findAllByPublisherName(@RequestParam("pageable") int page,
			@SortDefault(sort = "id") @PageableDefault(size = 10, page = 0) final Pageable pageable,
			@Parameter(description = "The publisher name") @PathVariable String publisherName) {
		return bookService.findAllByPublisherName(pageable.withPage(page), publisherName);
	}

	@Operation(summary = "Get all the books by age range")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Books were returned", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Page.class, subTypes = BookDto.class))) })
	@GetMapping(value = "/search/start-age/{startAge}/end-age/{endAge}", params = { "pageable" })
	Page<BookDto> findAllByAgeRange(@RequestParam("pageable") int page,
			@SortDefault(sort = "id") @PageableDefault(size = 10, page = 0) final Pageable pageable,
			@Parameter(description = "The age to start the search") @PathVariable Integer startAge,
			@Parameter(description = "The age to finish the search") @PathVariable Integer endAge)
			throws ServiceException {
		return bookService.findAllByAgeRange(pageable.withPage(page), startAge, endAge);
	}

	@Operation(summary = "Get all the books by location name")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Books were returned", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Page.class, subTypes = BookDto.class))) })
	@GetMapping(value = "/search/location-name/{locationName}", params = { "pageable" })
	Page<BookDto> findAllByLocationName(@RequestParam("pageable") int page,
			@SortDefault(sort = "id") @PageableDefault(size = 10, page = 0) final Pageable pageable,
			@Parameter(description = "The location name") @PathVariable String locationName) {
		return bookService.findAllByLocationName(pageable.withPage(page), locationName);
	}

	@Operation(summary = "Get top 10 books by age range and location name")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Books were returned", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Page.class, subTypes = BookDto.class))) })
	@GetMapping(value = "/search/location-name/{locationName}/age-start/{ageStart}/age-end/{ageEnd}", params = {
			"pageable" })
	Page<BookDto> findTop10ByLocationAndAgeRange(@RequestParam("pageable") int page,
			@SortDefault(sort = "id") @PageableDefault(size = 10, page = 0) final Pageable pageable,
			@Parameter(description = "The location name") @PathVariable String locationName,
			@Parameter(description = "The age to start the searching") @PathVariable Integer ageStart,
			@Parameter(description = "The age to finish the searching") @PathVariable Integer ageEnd)
			throws ServiceException {
		return bookService.findTop10ByLocationAndAgeRange(pageable.withPage(page), locationName, ageStart, ageEnd);
	}

	@Operation(summary = "Get the book by isbn")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Book was returned", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = BookDto.class))),
			@ApiResponse(responseCode = "404", description = "Book was not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))) })
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
			@ApiResponse(responseCode = "200", description = "Book was returned", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = BookDto.class))),
			@ApiResponse(responseCode = "404", description = "Book was not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))) })
	@GetMapping(value = "/search/book-title/{bookTitle}")
	BookDto findByBookTitle(@Parameter(description = "The book title") @PathVariable String bookTitle) {
		return bookService.findByBookTitle(bookTitle).orElseThrow(() -> {
			String message = "The book with book title %s was not found".formatted(bookTitle);
			log.error(message);
			throw new ResourceNotFoundException(message);
		});
	}

	@Operation(summary = "Create a new book", security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Book was created", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = BookDto.class))),
			@ApiResponse(responseCode = "400", description = "Book was not created", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))),
			@ApiResponse(responseCode = "401", description = "The user is unauthorized", content = @io.swagger.v3.oas.annotations.media.Content) })
	@PostMapping(value = "/creation")
	ResponseEntity<BookDto> create(@Parameter(description = "The new book dto") @RequestBody BookDto book)
			throws ServiceException {
		authorService.findByAuthorName(book.getAuthorName()).orElseThrow(() -> {
			String message = "The author with name %s was not found".formatted(book.getAuthorName());
			log.error(message);
			throw new ResourceNotFoundException(message);
		});
		publisherService.findByPublisherName(book.getPublisherName()).orElseThrow(() -> {
			String message = "The publisher with name %s was not found".formatted(book.getPublisherName());
			log.error(message);
			throw new ResourceNotFoundException(message);
		});
		BookDto result = bookService.save(book);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	@Operation(summary = "Update a book", security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Book was updated", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = BookDto.class))),
			@ApiResponse(responseCode = "400", description = "Book was not updated", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))),
			@ApiResponse(responseCode = "401", description = "The user is unauthorized", content = @io.swagger.v3.oas.annotations.media.Content) })
	@PutMapping(value = "/update")
	BookDto update(@Parameter(description = "The book dto to update") @RequestBody BookDto book)
			throws ServiceException {
		authorService.findByAuthorName(book.getAuthorName()).orElseThrow(() -> {
			String message = "The author with name %s was not found".formatted(book.getAuthorName());
			log.error(message);
			throw new ResourceNotFoundException(message);
		});
		publisherService.findByPublisherName(book.getPublisherName()).orElseThrow(() -> {
			String message = "The publisher with name %s was not found".formatted(book.getPublisherName());
			log.error(message);
			throw new ResourceNotFoundException(message);
		});
		return bookService.save(book);
	}

	@Operation(summary = "Delete a book", security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Book was deleted", content = @io.swagger.v3.oas.annotations.media.Content),
			@ApiResponse(responseCode = "404", description = "Book was not deleted", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))),
			@ApiResponse(responseCode = "401", description = "The user is unauthorized", content = @io.swagger.v3.oas.annotations.media.Content) })
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
