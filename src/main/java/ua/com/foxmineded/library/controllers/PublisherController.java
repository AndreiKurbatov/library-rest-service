package ua.com.foxmineded.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
import ua.com.foxmineded.library.dto.PublisherDto;
import ua.com.foxmineded.library.services.PublisherService;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/publishers")
@RequiredArgsConstructor
public class PublisherController {
	@Autowired
	private final PublisherService publisherService;

	@GetMapping(value = "/search/all")
	public Page<PublisherDto> findAll(@SortDefault(sort = "id") @PageableDefault(size = 10) final Pageable pageable) {
		return publisherService.findAll(pageable);
	}

	@GetMapping(value = "/search/author-name/{name}")
	public Page<PublisherDto> findAllByAuthorName(
			@SortDefault(sort = "id") @PageableDefault(size = 10) final Pageable pageable, @PathVariable String name) {
		return publisherService.findAllByAuthorName(pageable, name);
	}

	@GetMapping(value = "/search/publisher-name/{name}")
	public PublisherDto findByPublisherName(@PathVariable String name) {
		return publisherService.findByPublisherName(name).orElseThrow(() -> {
			String message = "The publisher with name %s was not found".formatted(name);
			log.error(message);
			throw new ResourceNotFoundException(message);
		});
	}

	@GetMapping(value = "/search/book-title/{bookTitle}")
	public PublisherDto findByBookTitle(@PathVariable String bookTitle) {
		return publisherService.findByBookTitle(bookTitle).orElseThrow(() -> {
			String message = "The publisher by book title %s was not found".formatted(bookTitle);
			log.error(message);
			throw new ResourceNotFoundException(message);
		});
	}

	@GetMapping(value = "/search/isbn/{isbn}")
	public PublisherDto findByIsbn(@PathVariable String isbn) {
		return publisherService.findByIsbn(isbn).orElseThrow(() -> {
			String message = "The publisher by isbn %s was not found".formatted(isbn);
			log.error(message);
			throw new ResourceNotFoundException(message);
		});
	}

	@PostMapping(value = "/creation")
	public ResponseEntity<Object> create(@RequestBody PublisherDto publisherDto) {
		PublisherDto result = publisherService.save(publisherDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	@PutMapping(value = "/update")
	public PublisherDto update(@RequestBody PublisherDto publisherDto) {
		return publisherService.save(publisherDto);
	}

	@DeleteMapping(value = "/deletion/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable Long id) {
		publisherService.findById(id).ifPresentOrElse(value -> publisherService.deleteById(id), () -> {
			String message = "The publisher with id = %d was deleted".formatted(id);
			log.error(message);
			throw new ResourceNotFoundException(message);
		});
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
