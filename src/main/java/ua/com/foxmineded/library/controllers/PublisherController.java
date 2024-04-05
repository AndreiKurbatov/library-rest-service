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
import ua.com.foxmineded.library.dto.PublisherDto;
import ua.com.foxmineded.library.exceptions.ServiceException;
import ua.com.foxmineded.library.services.PublisherService;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/publishers")
@RequiredArgsConstructor
public class PublisherController {
	private final PublisherService publisherService;

	@Operation(summary = "Get all the publishers")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Publishers were returned", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json" ,schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Page.class, subTypes = PublisherDto.class))) })
	@GetMapping(value = "/search/all")
	public Page<PublisherDto> findAll(@SortDefault(sort = "id") @PageableDefault(size = 10) final Pageable pageable) {
		return publisherService.findAll(pageable);
	}

	@Operation(summary = "Get all the publishers by author name")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Publishers were returned", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json" ,schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Page.class, subTypes = PublisherDto.class))) })
	@GetMapping(value = "/search/author-name/{name}")
	public Page<PublisherDto> findAllByAuthorName(
			@SortDefault(sort = "id") @PageableDefault(size = 10) final Pageable pageable, @Parameter(description = "The author name") @PathVariable String name) {
		return publisherService.findAllByAuthorName(pageable, name);
	}

	@Operation(summary = "Get the publisher by publisher name")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Publisher was returned", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json" ,schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = PublisherDto.class))),
			@ApiResponse(responseCode = "404", description = "Publisher was not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json" , schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)) ) })
	@GetMapping(value = "/search/publisher-name/{name}")
	public PublisherDto findByPublisherName(@Parameter(description = "The publisher name") @PathVariable String name) {
		return publisherService.findByPublisherName(name).orElseThrow(() -> {
			String message = "The publisher with name %s was not found".formatted(name);
			log.error(message);
			throw new ResourceNotFoundException(message);
		});
	}

	@Operation(summary = "Get the publisher by book title")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Publisher was returned", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json" ,schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = PublisherDto.class))),
			@ApiResponse(responseCode = "404", description = "Publisher was not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json" , schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)) ) })
	@GetMapping(value = "/search/book-title/{bookTitle}")
	public PublisherDto findByBookTitle(@Parameter(description = "The book title") @PathVariable String bookTitle) {
		return publisherService.findByBookTitle(bookTitle).orElseThrow(() -> {
			String message = "The publisher by book title %s was not found".formatted(bookTitle);
			log.error(message);
			throw new ResourceNotFoundException(message);
		});
	}

	@Operation(summary = "Get the publisher by book isbn")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Publisher was returned", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json" ,schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = PublisherDto.class))),
			@ApiResponse(responseCode = "404", description = "Publisher was not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json" , schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)) ) })
	@GetMapping(value = "/search/isbn/{isbn}")
	public PublisherDto findByIsbn(@Parameter(description = "The book isbn") @PathVariable String isbn) {
		return publisherService.findByIsbn(isbn).orElseThrow(() -> {
			String message = "The publisher by isbn %s was not found".formatted(isbn);
			log.error(message);
			throw new ResourceNotFoundException(message);
		});
	}

	@Operation(summary = "Create a new publisher", security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Publisher was created", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = PublisherDto.class))),
			@ApiResponse(responseCode = "400", description = "Publisher was not created", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))),
			@ApiResponse(responseCode = "401", description = "The user is unauthorized", content = @io.swagger.v3.oas.annotations.media.Content) 
	})
	@PostMapping(value = "/creation")
	public ResponseEntity<Object> create(@Parameter(description = "The new publisher dto") @RequestBody PublisherDto publisherDto) throws ServiceException {
		PublisherDto result = publisherService.save(publisherDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	@Operation(summary = "Update a publisher", security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Publisher was updated", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = PublisherDto.class))),
			@ApiResponse(responseCode = "400", description = "Publisher was not updated", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))),
			@ApiResponse(responseCode = "401", description = "The user is unauthorized", content = @io.swagger.v3.oas.annotations.media.Content) 	
	})
	@PutMapping(value = "/update")
	public PublisherDto update(@Parameter(description = "The publisher dto to update") @RequestBody PublisherDto publisherDto) throws ServiceException {
		return publisherService.save(publisherDto);
	}

	@Operation(summary = "Delete a publisher", security = @SecurityRequirement(name = "bearerAuth"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Publisher was deleted", content = @io.swagger.v3.oas.annotations.media.Content),
			@ApiResponse(responseCode = "404", description = "Publisher was not deleted", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))),
			@ApiResponse(responseCode = "401", description = "The user is unauthorized", content = @io.swagger.v3.oas.annotations.media.Content) 
	})
	@DeleteMapping(value = "/deletion/{id}")
	public ResponseEntity<Object> deleteById(@Parameter(description = "The id of the publisher to delete") @PathVariable Long id) {
		publisherService.findById(id).ifPresentOrElse(value -> publisherService.deleteById(id), () -> {
			String message = "The publisher with id = %d was deleted".formatted(id);
			log.error(message);
			throw new ResourceNotFoundException(message);
		});
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
