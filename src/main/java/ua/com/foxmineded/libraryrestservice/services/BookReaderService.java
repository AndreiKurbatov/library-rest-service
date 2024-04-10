package ua.com.foxmineded.libraryrestservice.services;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;
import ua.com.foxmineded.libraryrestservice.dto.BookReaderDto;
import ua.com.foxmineded.libraryrestservice.exceptions.ServiceException;

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
	BookReaderDto save(@Valid BookReaderDto bookReaderDto) throws ServiceException;

	@Transactional
	void deleteById(Long id);
}
