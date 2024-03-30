package ua.com.foxmineded.library.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import lombok.extern.slf4j.Slf4j;
import ua.com.foxmineded.library.dao.AuthorRepository;
import ua.com.foxmineded.library.dao.BookRatingRepository;
import ua.com.foxmineded.library.dao.BookReaderRepository;
import ua.com.foxmineded.library.dao.BookRepository;
import ua.com.foxmineded.library.dao.PublisherRepository;
import ua.com.foxmineded.library.entities.impl.Author;
import ua.com.foxmineded.library.entities.impl.Book;
import ua.com.foxmineded.library.entities.impl.BookRating;
import ua.com.foxmineded.library.entities.impl.BookReader;
import ua.com.foxmineded.library.entities.impl.Location;
import ua.com.foxmineded.library.entities.impl.Publisher;
import ua.com.foxmineded.library.services.AuthorImporterService;
import ua.com.foxmineded.library.services.BookImporterService;
import ua.com.foxmineded.library.services.BookRatingImporterService;
import ua.com.foxmineded.library.services.BookReaderImporterService;
import ua.com.foxmineded.library.services.PublisherImporterService;
import ua.com.foxmineded.library.utils.AuthorCsvImporter;
import ua.com.foxmineded.library.utils.BookCsvImporter;
import ua.com.foxmineded.library.utils.BookRatingCsvImporter;
import ua.com.foxmineded.library.utils.BookReaderCsvImporter;
import ua.com.foxmineded.library.utils.LocationCsvImporter;
import ua.com.foxmineded.library.utils.PublisherCsvImporter;

@Slf4j
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		PublisherRepository.class, PublisherImporterService.class, PublisherCsvImporter.class,
		AuthorImporterService.class, AuthorRepository.class, AuthorCsvImporter.class,
		BookImporterService.class, BookRepository.class, BookCsvImporter.class,
		BookReaderRepository.class, BookReaderImporterService.class, BookReaderCsvImporter.class,
		LocationCsvImporter.class,
		BookRatingRepository.class, BookRatingImporterService.class, BookRatingCsvImporter.class
		}))
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
@Sql(scripts = { "/test/sql/clear-tables.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_CLASS)
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
	
	@Disabled
	@Test
	void testPersistAllEntitiesAndCreateRelationBetweenThem() {
	    Map<Long, Set<Location>> locations = new HashMap<>();
	    Map<Long, BookReader> bookReaders = new HashMap<>();
	    Map<String, Publisher> publishers = new HashMap<>();
	    Map<String, Author> authors = new HashMap<>();
	    Map<String, Book> books = new HashMap<>();
	    List<BookRating> bookRatings = new ArrayList<>();
	    
		publisherImporterService.importPublishers(publishers);
		log.info("%d publishers were imported".formatted(publishers.size()));
		authorImporterService.importAuthors(authors);
		log.info("%d authors were imported".formatted(authors.size()));
		bookImporterService.importBooks(books, authors, publishers);
		log.info("%d books were imported".formatted(books.size()));
		bookReaderImporterService.importBookReaders(bookReaders, locations);
		log.info("%d book readers were imported".formatted(bookReaders.size()));
		log.info("%d locations were imported".formatted(locations.values().stream().flatMap(location -> location.stream().map(entity -> entity.getLocationName())).count()));
		bookRatingImporterService.importBookRatings(bookRatings, bookReaders, books);
		log.info("%d book ratings were imported".formatted(bookRatings.size()));
	
		bookImporterService.createBookToBookReaderRelationship(bookRatings, books, bookReaders);
		
		for (Book book : books.values()) {
			assertNotNull(book.getBookReaders());
		}
		for (BookReader bookReader : bookReaders.values()) {
			assertNotNull(bookReader.getBooks());
		}
	}
}
