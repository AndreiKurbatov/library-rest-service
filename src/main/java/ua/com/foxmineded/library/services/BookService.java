package ua.com.foxmineded.library.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxmineded.library.dto.BookDto;
import ua.com.foxmineded.library.exceptions.ServiceException;

public interface BookService {
	@Transactional(readOnly = true)
	BookDto findById(Long id) throws ServiceException;
	
	@Transactional(readOnly = true)
	BookDto findByIsbn(String isbn) throws ServiceException;
	
	@Transactional(readOnly = true)
	BookDto findByBookTitle(String bookTitle) throws ServiceException;
	
	@Transactional(readOnly = true)
	Page<BookDto> findAll(Pageable pageable);
	
	@Transactional(readOnly = true)
	Page<BookDto> findAllByAuthorName(String name, Pageable pageable);
	
	@Transactional(readOnly = true)
	Page<BookDto> findAllByPublisherName(String name, Pageable pageable);
	
	//@Secured({ "ROLE_ADMINISTRATOR"})
	@Transactional
	BookDto save(BookDto book);
	
	//@Secured({ "ROLE_ADMINISTRATOR"})
	@Transactional
	void deleteById(Long id);
}
