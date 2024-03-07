package ua.com.foxmineded.library.services;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxmineded.library.entities.impl.BookRating;

public interface BookRatingGeneratorService {
	@Transactional
	List<BookRating> generateBookRatings();
	
	@Transactional(readOnly = true)
	Long countAll();
}
