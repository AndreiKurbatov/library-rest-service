package ua.com.foxmineded.library.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxmineded.library.dto.BookReaderDto;
import ua.com.foxmineded.library.exceptions.ServiceException;

public interface BookReaderService {
	@Transactional(readOnly = true)
	Page<BookReaderDto> findAll(Pageable pageable);
	
	@Transactional(readOnly = true)
	Page<BookReaderDto> findAllByAge(Pageable pageable, Integer age);
	
	@Transactional(readOnly = true)
	BookReaderDto findByBookReaderId(Long bookReaderId) throws ServiceException;
	
	//Page<BookReaderDto> findAllByLocationNames(Pageable pageable, String... locationNames);
	
	//@Secured("ROLE_ADMINISTRATOR")
	@Transactional
	BookReaderDto save(BookReaderDto bookReaderDto);
	
	//@Secured("ROLE_ADMINISTRATOR")
	@Transactional
	void deleteByBookReaderId(Long bookReaderId);
	
}
