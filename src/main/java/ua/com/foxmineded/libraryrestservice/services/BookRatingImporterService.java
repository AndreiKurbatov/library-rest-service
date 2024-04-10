package ua.com.foxmineded.libraryrestservice.services;

import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxmineded.libraryrestservice.entities.impl.Book;
import ua.com.foxmineded.libraryrestservice.entities.impl.BookRating;
import ua.com.foxmineded.libraryrestservice.entities.impl.BookReader;

public interface BookRatingImporterService {
	@Transactional
	void importBookRatings(List<BookRating> bookRatings, Map<Long, BookReader> bookReaders, Map<String, Book> books);

	@Transactional(readOnly = true)
	Long countAll();
}
