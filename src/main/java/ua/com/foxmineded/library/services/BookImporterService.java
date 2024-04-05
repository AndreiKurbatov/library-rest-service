package ua.com.foxmineded.library.services;

import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxmineded.library.entities.impl.Author;
import ua.com.foxmineded.library.entities.impl.Book;
import ua.com.foxmineded.library.entities.impl.BookRating;
import ua.com.foxmineded.library.entities.impl.BookReader;
import ua.com.foxmineded.library.entities.impl.Publisher;

public interface BookImporterService {
	@Transactional
	void importBooks(Map<String, Book> books, Map<String, Author> authors, Map<String, Publisher> publishers);
	
	@Transactional
	void createBookToBookReaderRelationship(List<BookRating> bookRatings ,Map<String, Book> books, Map<Long, BookReader> bookReaders);
	
	@Transactional(readOnly = true)
	Long countAll();
}
