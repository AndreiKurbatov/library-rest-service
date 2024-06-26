package ua.com.foxmineded.libraryrestservice.services;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;
import ua.com.foxmineded.libraryrestservice.dto.BookDto;
import ua.com.foxmineded.libraryrestservice.exceptions.ServiceException;

public interface BookService {
	@Transactional(readOnly = true)
	Page<BookDto> findAll(Pageable pageable);

	@Transactional(readOnly = true)
	Page<BookDto> findAllByAuthorName(Pageable pageable, String name);

	@Transactional(readOnly = true)
	Page<BookDto> findAllByPublisherName(Pageable pageable, String name);

	@Transactional(readOnly = true)
	Page<BookDto> findAllByAgeRange(Pageable pageable, Integer startAge, Integer endAge) throws ServiceException;

	@Transactional(readOnly = true)
	Page<BookDto> findAllByLocationName(Pageable pageable, String locationName);

	@Transactional(readOnly = true)
	Page<BookDto> findTop10ByLocationAndAgeRange(Pageable pageable, String locationName, Integer ageStart,
			Integer ageEnd) throws ServiceException;

	@Transactional(readOnly = true)
	Optional<BookDto> findById(Long id);

	@Transactional(readOnly = true)
	Optional<BookDto> findByIsbn(String isbn);

	@Transactional(readOnly = true)
	Optional<BookDto> findByBookTitle(String bookTitle);

	@Transactional
	BookDto save(@Valid BookDto book) throws ServiceException;

	@Transactional
	void deleteById(Long id);
}
