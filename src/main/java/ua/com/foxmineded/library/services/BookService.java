package ua.com.foxmineded.library.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxmineded.library.dto.BookDto;
import ua.com.foxmineded.library.exceptions.ServiceException;

public interface BookService {
	@Secured({ "ROLE_ADMINISTRATOR", "ROLE_TEACHER", "ROLE_STUDENT" })
	@Transactional(readOnly = true)
	BookDto findById(Long id) throws ServiceException;
	
	@Secured({ "ROLE_ADMINISTRATOR", "ROLE_TEACHER", "ROLE_STUDENT" })
	@Transactional(readOnly = true)
	BookDto findByIsbn(String isbn) throws ServiceException;
	
	@Secured({ "ROLE_ADMINISTRATOR", "ROLE_TEACHER", "ROLE_STUDENT" })
	@Transactional(readOnly = true)
	BookDto findByBookTitle(String bookTitle) throws ServiceException;
	
	@Secured({ "ROLE_ADMINISTRATOR", "ROLE_TEACHER", "ROLE_STUDENT" })
	@Transactional(readOnly = true)
	Page<BookDto> findAll(Pageable pageable);
	
	@Secured({ "ROLE_ADMINISTRATOR", "ROLE_TEACHER", "ROLE_STUDENT" })
	@Transactional(readOnly = true)
	Page<BookDto> findAllByAuthorName(String name, Pageable pageable);
	
	@Secured({ "ROLE_ADMINISTRATOR", "ROLE_TEACHER", "ROLE_STUDENT" })
	@Transactional(readOnly = true)
	Page<BookDto> findAllByPublisherName(String name, Pageable pageable);
	
	@Secured({ "ROLE_ADMINISTRATOR", "ROLE_TEACHER"})
	@Transactional
	BookDto save(BookDto book);
	
	@Secured({ "ROLE_ADMINISTRATOR", "ROLE_TEACHER"})
	@Transactional
	void deleteById(Long id);
}
