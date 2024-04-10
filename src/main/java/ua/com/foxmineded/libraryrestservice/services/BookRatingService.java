package ua.com.foxmineded.libraryrestservice.services;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;
import ua.com.foxmineded.libraryrestservice.dto.BookRatingDto;
import ua.com.foxmineded.libraryrestservice.exceptions.ServiceException;

public interface BookRatingService {
	@Transactional(readOnly = true)
	Page<BookRatingDto> findAll(Pageable pageable);

	@Transactional(readOnly = true)
	Page<BookRatingDto> findAllByBookId(Pageable pageable, Long id);

	@Transactional(readOnly = true)
	Optional<BookRatingDto> findById(Long id);

	@Transactional
	BookRatingDto save(@Valid BookRatingDto bookRatingDto) throws ServiceException;

	@Transactional
	void deleteById(Long id);
}
