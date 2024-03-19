package ua.com.foxmineded.library.services;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import ua.com.foxmineded.library.dto.BookRatingDto;
import ua.com.foxmineded.library.exceptions.ServiceException;

public interface BookRatingService {
	List<BookRatingDto> findAll(Pageable pageable);
	
	Optional<BookRatingDto> findById(Long id);
	
	BookRatingDto save(BookRatingDto bookRatingDto) throws ServiceException;
	
	void deleteById(Long id);
}
