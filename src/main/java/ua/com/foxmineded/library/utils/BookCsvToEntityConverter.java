package ua.com.foxmineded.library.utils;

import java.util.Map;
import ua.com.foxmineded.library.entities.impl.Author;
import ua.com.foxmineded.library.entities.impl.Book;
import ua.com.foxmineded.library.entities.impl.Publisher;

public interface BookCsvToEntityConverter {
	void convert(Map<String, Book> books, Map<String, Author> authors, Map<String, Publisher> publishers);
}
