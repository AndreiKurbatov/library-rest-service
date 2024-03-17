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
import ua.com.foxmineded.library.dto.AuthorDto;
import ua.com.foxmineded.library.exceptions.ServiceException;
import ua.com.foxmineded.library.services.AuthorService;

@RestController
@RequestMapping(value = "/api/v1/authors")
@RequiredArgsConstructor
public class AuthorController {
	@Autowired
	private final AuthorService authorService;
	
	@GetMapping(value = "/search/all")
	public Page<AuthorDto> findAll(@SortDefault(sort = "id") @PageableDefault(size = 10) final Pageable pageable) {
		return authorService.findAll(pageable);
	}
	
	@GetMapping(value = "/search/publisher-name/{name}")
	public Page<AuthorDto> findAllByPublisherName(@SortDefault(sort = "id") @PageableDefault(size = 10) final Pageable pageable, @PathVariable String name) {
		return authorService.findAllByPublisherName(name, pageable);
	}
	
	@GetMapping(value = "/search/author-name/{name}")
	public AuthorDto findByAuthorName(@PathVariable String name) throws ServiceException {
		return authorService.findByAuthorName(name);
	}
	
	@GetMapping(value = "/search/isbn/{isbn}")
	public AuthorDto findByIsbn(@PathVariable String isbn) throws ServiceException {
		return authorService.findByIsbn(isbn);
	}
	
	@GetMapping(value = "/search/book-title/{bookTitle}")
	public AuthorDto findByBookTitle (@PathVariable String bookTitle) throws ServiceException {
		return authorService.findByBookTitle(bookTitle);
	}
	
	@PostMapping(value = "/creation")
	public AuthorDto create(@RequestBody AuthorDto authorDto) {
		return authorService.save(authorDto);
	}

	@PutMapping(value = "/update")
	public AuthorDto update(@RequestBody AuthorDto authorDto) {
		return authorService.save(authorDto);
	}
	
	@DeleteMapping(value = "/deletion/{id}")
	public void deleteById(@PathVariable Long id) {
		authorService.deleteById(id);
	}
}
