package ua.com.foxmineded.library.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxmineded.library.dto.AuthorDto;
import ua.com.foxmineded.library.exceptions.ServiceException;

public interface AuthorService {
	@Transactional(readOnly = true)
	AuthorDto findById(Long id) throws ServiceException;
	
	@Transactional(readOnly = true)
	AuthorDto findByAuthorName(String name) throws ServiceException;
	
	@Transactional(readOnly = true)
	AuthorDto findByIsbn(String isbn) throws ServiceException;
	
	@Transactional(readOnly = true)
	AuthorDto findByBookTitle (String bookTitle) throws ServiceException;
	
	@Transactional(readOnly = true) 
	Page<AuthorDto> findAll(Pageable pageable);
	
	@Transactional(readOnly = true)
	Page<AuthorDto> findAllByPublisherName(String publisherName, Pageable pageable);
	
	@Secured({"ROLE_ADMINISTRATOR"})
	@Transactional
	AuthorDto save(AuthorDto authorDto);
	
	@Secured({"ROLE_ADMINISTRATOR"})
	@Transactional
	void deleteById(Long id);
}
