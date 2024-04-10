package ua.com.foxmineded.libraryrestservice.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import ua.com.foxmineded.libraryrestservice.dao.AuthorRepository;
import ua.com.foxmineded.libraryrestservice.dao.BookRatingRepository;
import ua.com.foxmineded.libraryrestservice.dao.BookReaderRepository;
import ua.com.foxmineded.libraryrestservice.dao.BookRepository;
import ua.com.foxmineded.libraryrestservice.dao.PublisherRepository;
import ua.com.foxmineded.libraryrestservice.entities.impl.Author;
import ua.com.foxmineded.libraryrestservice.entities.impl.Book;
import ua.com.foxmineded.libraryrestservice.entities.impl.Publisher;
import ua.com.foxmineded.libraryrestservice.services.AuthorImporterService;
import ua.com.foxmineded.libraryrestservice.services.BookImporterService;
import ua.com.foxmineded.libraryrestservice.services.BookRatingImporterService;
import ua.com.foxmineded.libraryrestservice.services.BookReaderImporterService;
import ua.com.foxmineded.libraryrestservice.services.PublisherImporterService;
import ua.com.foxmineded.libraryrestservice.utils.AuthorCsvImporter;
import ua.com.foxmineded.libraryrestservice.utils.BookCsvImporter;
import ua.com.foxmineded.libraryrestservice.utils.BookRatingCsvImporter;
import ua.com.foxmineded.libraryrestservice.utils.BookReaderCsvImporter;
import ua.com.foxmineded.libraryrestservice.utils.LocationCsvImporter;
import ua.com.foxmineded.libraryrestservice.utils.PublisherCsvImporter;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		PublisherRepository.class, PublisherImporterService.class, PublisherCsvImporter.class,
		AuthorImporterService.class, AuthorRepository.class, AuthorCsvImporter.class, BookImporterService.class,
		BookRepository.class, BookCsvImporter.class, BookReaderRepository.class, BookReaderImporterService.class,
		BookReaderCsvImporter.class, LocationCsvImporter.class, BookRatingRepository.class,
		BookRatingImporterService.class, BookRatingCsvImporter.class }))
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
@Sql(scripts = { "/test/sql/clear-tables.sql" })
class BookImporterServiceImplTest {
	@Autowired
	PublisherImporterService publisherImporterService;
	@Autowired
	AuthorImporterService authorImporterService;
	@Autowired
	BookReaderImporterService bookReaderImporterService;
	@Autowired
	BookImporterService bookImporterService;
	@Autowired
	BookRatingImporterService bookRatingImporterService;

	@Test
	void testImportBooks() {
		Map<String, Publisher> publishers = new HashMap<>();
		Map<String, Author> authors = new HashMap<>();
		Map<String, Book> books = new HashMap<>();

		bookImporterService.importBooks(books, authors, publishers);
		for (Book book : books.values()) {
			assertNotNull(book.getId());
			assertNotNull(book.getIsbn());
			assertNotNull(book.getBookTitle());
			assertNotNull(book.getAuthor());
			assertNotNull(book.getAuthor().getAuthorName());
			assertNotNull(book.getAuthor().getId());
			assertNotNull(book.getAuthor().getBooks());
			assertNotNull(book.getPublisher());
			assertNotNull(book.getPublisher().getPublisherName());
			assertNotNull(book.getPublisher().getId());
			assertNotNull(book.getPublisher().getBooks());
			assertNotNull(book.getPublicationYear());
			assertNotNull(book.getImageUrlS());
			assertNotNull(book.getImageUrlM());
			assertNotNull(book.getImageUrlL());
		}
		assertEquals(271377, books.size());
	}
}
