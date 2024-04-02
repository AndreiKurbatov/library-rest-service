package ua.com.foxmineded.library.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
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
import ua.com.foxmineded.library.dto.AuthorDto;
import ua.com.foxmineded.library.exceptions.ServiceException;
import ua.com.foxmineded.library.services.AuthorService;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/authors")
@RequiredArgsConstructor
public class AuthorController {
	private final AuthorService authorService;

	@Operation(summary = "Get all the authors")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Authors were returned ", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json" ,schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Page.class, subTypes = AuthorDto.class))) })
	@GetMapping(value = "/search/all")
	public Page<AuthorDto> findAll(@SortDefault(sort = "id") @PageableDefault(size = 10) final Pageable pageable) {
		return authorService.findAll(pageable);
	}

	@Operation(summary = "Get all the authors by publisher name")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Authors were returned ", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json" ,schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Page.class, subTypes = AuthorDto.class))) })
	@GetMapping(value = "/search/publisher-name/{name}")
	public Page<AuthorDto> findAllByPublisherName(
			@SortDefault(sort = "id") @PageableDefault(size = 10) final Pageable pageable, @Parameter(description = "The name of the publisher")  @PathVariable String name) {
		return authorService.findAllByPublisherName(name, pageable);
	}

	@Operation(summary = "Get the author by author name")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Author was returned", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json" ,schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = AuthorDto.class))),
			@ApiResponse(responseCode = "404", description = "Author was not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json" ,schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)) ) })
	@GetMapping(value = "/search/author-name/{name}")
	public AuthorDto findByAuthorName(@Parameter(description = "The author name") @PathVariable String name) {
		return authorService.findByAuthorName(name).orElseThrow(() -> {
			String message = "The author with name %s was not found".formatted(name);
			log.error(message);
			throw new ResourceNotFoundException(message);
		});
	}

	@Operation(summary = "Get the author by isbn")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Author was returned", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json" ,schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = AuthorDto.class))),
			@ApiResponse(responseCode = "404", description = "Author was not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json" , schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)) ) })
	@GetMapping(value = "/search/isbn/{isbn}")
	public AuthorDto findByIsbn(@Parameter(description = "The book isbn") @PathVariable String isbn) {
		return authorService.findByIsbn(isbn).orElseThrow(() -> {
			String message = "The author with isbn %s was not found".formatted(isbn);
			log.error(message);
			throw new ResourceNotFoundException(message);
		});
	}

	@Operation(summary = "Get the author by book title")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Author was returned", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = AuthorDto.class))),
			@ApiResponse(responseCode = "404", description = "Author was not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)) ) })
	@GetMapping(value = "/search/book-title/{bookTitle}")
	public AuthorDto findByBookTitle(@Parameter(description = "The book title")  @PathVariable String bookTitle) {
		return authorService.findByBookTitle(bookTitle).orElseThrow(() -> {
			String message = "The author with book title %s was not found".formatted(bookTitle);
			log.error(message);
			throw new ResourceNotFoundException(message);
		});
	}

	@Operation(summary = "Create a new author")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Author was created", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = AuthorDto.class))),
			@ApiResponse(responseCode = "400", description = "Author was not created", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)) ) })
	@PostMapping(value = "/creation")
	public ResponseEntity<AuthorDto> create(@Parameter(description = "The new author dto") @RequestBody AuthorDto authorDto) throws ServiceException {
		AuthorDto result = authorService.save(authorDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	@Operation(summary = "Update an author")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Author was updated", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = AuthorDto.class))),
			@ApiResponse(responseCode = "400", description = "Author was not updated", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)) ) })
	@PutMapping(value = "/update")
	public AuthorDto update(@Parameter(description = "The author dto to update") @RequestBody AuthorDto authorDto) throws ServiceException {
		return authorService.save(authorDto);
	}

	@Operation(summary = "Delete an author")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Author was deleted", content = @io.swagger.v3.oas.annotations.media.Content),
			@ApiResponse(responseCode = "404", description = "Author was not deleted", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)) ) })
	@DeleteMapping(value = "/deletion/{id}")
	public ResponseEntity<Object> deleteById(@Parameter(description = "The id of the author to delete") @PathVariable Long id) {
		authorService.findById(id).ifPresentOrElse((value) -> authorService.deleteById(id), () -> {
			String message = "The author with id = %d was not found".formatted(id);
			log.error(message);
			throw new ResourceNotFoundException(message);
		});
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
