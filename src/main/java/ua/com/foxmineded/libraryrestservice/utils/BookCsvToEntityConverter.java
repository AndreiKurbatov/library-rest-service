package ua.com.foxmineded.libraryrestservice.utils;

import java.util.Map;

import ua.com.foxmineded.libraryrestservice.entities.impl.Author;
import ua.com.foxmineded.libraryrestservice.entities.impl.Book;
import ua.com.foxmineded.libraryrestservice.entities.impl.Publisher;

public interface BookCsvToEntityConverter {
	void convert(Map<String, Book> books, Map<String, Author> authors, Map<String, Publisher> publishers);
}
