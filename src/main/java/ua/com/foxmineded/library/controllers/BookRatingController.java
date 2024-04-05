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
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

	@Operation(summary = "Get all the book ratings")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Book ratings were returned", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json" ,schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Page.class, subTypes = BookRatingDto.class))) })
	@GetMapping(value = "/search/all")
	public Page<BookRatingDto> findAll(@SortDefault(sort = "id") @PageableDefault(size = 10) final Pageable pageable) {
		return bookRatingService.findAll(pageable);
	}

	@Operation(summary = "Get all the book ratings by book id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Book ratings were returned", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json" ,schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Page.class, subTypes = BookRatingDto.class))) })
	@GetMapping(value = "/search/book-id/{id}")
	public Page<BookRatingDto> findAllByBookId(
			@SortDefault(sort = "id") @PageableDefault(size = 10) final Pageable pageable, @Parameter(description = "The book id") @PathVariable Long id) {
		return bookRatingService.findAllByBookId(pageable, id);
	}

	@Operation(summary = "Create a new book rating", security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "The book rating was created", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = BookRatingDto.class))),
			@ApiResponse(responseCode = "400", description = "The book rating was not created", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))),
			@ApiResponse(responseCode = "401", description = "The user is unauthorized", content = @io.swagger.v3.oas.annotations.media.Content) 
	})
	@PostMapping(value = "/creation")
	public ResponseEntity<BookRatingDto> create(@Parameter(description = "The new book rating dto") @RequestBody BookRatingDto bookRatingDto) throws ServiceException {
		BookRatingDto result = bookRatingService.save(bookRatingDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	@Operation(summary = "Update a book rating", security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "The book rating was updated", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = BookRatingDto.class))),
			@ApiResponse(responseCode = "400", description = "The book rating was not updated", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))),
			@ApiResponse(responseCode = "401", description = "The user is unauthorized", content = @io.swagger.v3.oas.annotations.media.Content) 
	})
	@PutMapping(value = "/update")
	public BookRatingDto update(@Parameter(description = "The book rating dto to update") @RequestBody BookRatingDto bookRatingDto) throws ServiceException {
		return bookRatingService.save(bookRatingDto);
	}

	@Operation(summary = "Delete a book rating", security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "The book rating was deleted", content = @io.swagger.v3.oas.annotations.media.Content),
			@ApiResponse(responseCode = "404", description = "The book rating was not deleted", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))),
			@ApiResponse(responseCode = "401", description = "The user is unauthorized", content = @io.swagger.v3.oas.annotations.media.Content) 
	})
	@DeleteMapping(value = "/deletion/{id}")
	public ResponseEntity<Object> deleteById(@Parameter(description = "The id of the book rating to delete") @PathVariable Long id) {
		bookRatingService.findById(id).ifPresentOrElse((v) -> bookRatingService.deleteById(id), () -> {
			String message = "The book rating with id = %d was not found".formatted(id);
			log.error(message);
			throw new ResourceNotFoundException(message);
		});
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
