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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import ua.com.foxmineded.library.dto.PublisherDto;
import ua.com.foxmineded.library.exceptions.ServiceException;
import ua.com.foxmineded.library.services.PublisherService;

@RestController
@RequestMapping(value = "/api/v1/books")
@RequiredArgsConstructor
public class PublisherController {
	@Autowired
	private final PublisherService publisherService;
	
	@GetMapping(value = "/search/all")
	public Page<PublisherDto> findAll(@SortDefault(sort = "age") @PageableDefault(size = 10) final Pageable pageable){
		return publisherService.findAll(pageable);
	}
	
	@GetMapping(value = "/search/author-name/{name}")
	public Page<PublisherDto> findAllByAuthorName(@SortDefault(sort = "age") @PageableDefault(size = 10) final Pageable pageable, @PathVariable String name) {
		return publisherService.findAllByAuthorName(name, pageable);
	}
	
	@GetMapping(value = "/search/publisher-name/{name}")
	public PublisherDto findByPublisherName(@PathVariable String name) throws ServiceException {
		return publisherService.findByPublisherName(name);
	}

	@GetMapping(value = "/search/book-title/{bookTitle}")
	public PublisherDto findByBookTitle(@PathVariable String bookTitle) throws ServiceException {
		return publisherService.findByBookTitle(bookTitle);
	}

	@GetMapping(value = "/search/isbn/{isbn}")
	public PublisherDto findByIsbn(@PathVariable String isbn) throws ServiceException {
		return publisherService.findByIsbn(isbn);
	}

	@PostMapping(value = "/creation")
	public PublisherDto create(@RequestBody PublisherDto publisherDto) {
		return null;
	}
	
	@PutMapping(value = "/update")
	public PublisherDto update(@RequestBody PublisherDto publisherDto) {
		return null;
	}

	@DeleteMapping(value = "/deletion/{id}")
	public void deleteById(@PathVariable Long id) {
		
	}
}
