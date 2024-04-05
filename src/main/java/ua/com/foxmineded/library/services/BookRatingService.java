package ua.com.foxmineded.library.services;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxmineded.library.dto.BookRatingDto;
import ua.com.foxmineded.library.exceptions.ServiceException;

public interface BookRatingService {
	@Transactional(readOnly = true)
	Page<BookRatingDto> findAll(Pageable pageable);
	
	@Transactional(readOnly = true)
	Page<BookRatingDto> findAllByBookId(Pageable pageable,  Long id);
	
	@Transactional(readOnly = true)
	Optional<BookRatingDto> findById(Long id);
	
	@Transactional
	BookRatingDto save(BookRatingDto bookRatingDto) throws ServiceException;
	
	@Transactional
	void deleteById(Long id);
}
