package ua.com.foxmineded.library.services;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxmineded.library.dto.AuthorDto;

public interface AuthorService {
	@Transactional(readOnly = true)
	Optional<AuthorDto> findById(Long id);
	
	@Transactional(readOnly = true)
	Optional<AuthorDto> findByAuthorName(String name);
	
	@Transactional(readOnly = true)
	Optional<AuthorDto> findByIsbn(String isbn);
	
	@Transactional(readOnly = true)
	Optional<AuthorDto> findByBookTitle (String bookTitle);
	
	@Transactional(readOnly = true) 
	Page<AuthorDto> findAll(Pageable pageable);
	
	@Transactional(readOnly = true)
	Page<AuthorDto> findAllByPublisherName(String publisherName, Pageable pageable);
	
	//@Secured({"ROLE_ADMINISTRATOR"})
	@Transactional
	AuthorDto save(AuthorDto authorDto);
	
	//@Secured({"ROLE_ADMINISTRATOR"})
	@Transactional
	void deleteById(Long id);
}
