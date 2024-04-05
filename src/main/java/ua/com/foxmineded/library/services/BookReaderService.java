package ua.com.foxmineded.library.services;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxmineded.library.dto.BookReaderDto;

public interface BookReaderService {
	@Transactional(readOnly = true)
	Page<BookReaderDto> findAll(Pageable pageable);
	
	@Transactional(readOnly = true)
	Page<BookReaderDto> findAllByAge(Pageable pageable, Integer age);
	
	@Transactional(readOnly = true)
	Optional<BookReaderDto> findByBookReaderId(Long bookReaderId);
	
	@Transactional(readOnly = true) 
	Optional<BookReaderDto> findById(Long id);
	
	@Transactional
	BookReaderDto save(BookReaderDto bookReaderDto);
	
	@Transactional
	void deleteById(Long id);
}
