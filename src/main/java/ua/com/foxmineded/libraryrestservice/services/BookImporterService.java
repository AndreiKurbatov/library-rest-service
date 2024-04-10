package ua.com.foxmineded.libraryrestservice.services;

import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxmineded.libraryrestservice.entities.impl.Author;
import ua.com.foxmineded.libraryrestservice.entities.impl.Book;
import ua.com.foxmineded.libraryrestservice.entities.impl.Publisher;

public interface BookImporterService {
	@Transactional
	void importBooks(Map<String, Book> books, Map<String, Author> authors, Map<String, Publisher> publishers);

	@Transactional(readOnly = true)
	Long countAll();
}
