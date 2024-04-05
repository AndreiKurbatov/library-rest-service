package ua.com.foxmineded.library.services;

import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxmineded.library.entities.impl.Book;
import ua.com.foxmineded.library.entities.impl.BookRating;
import ua.com.foxmineded.library.entities.impl.BookReader;

public interface BookRatingImporterService {
	@Transactional
	void importBookRatings(List<BookRating> bookRatings, Map<Long, BookReader> bookReaders, Map<String, Book> books);
	
	@Transactional(readOnly = true)
	Long countAll();
}
