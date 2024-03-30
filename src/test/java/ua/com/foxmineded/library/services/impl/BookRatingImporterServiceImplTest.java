package ua.com.foxmineded.library.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
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
@Sql(scripts = { "/test/sql/clear-tables.sql" })
class BookRatingImporterServiceImplTest {
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
	
    Map<Long, Set<Location>> locations = new HashMap<>();
    Map<Long, BookReader> bookReaders = new HashMap<>();
    Map<String, Publisher> publishers = new HashMap<>();
    Map<String, Author> authors = new HashMap<>();
    Map<String, Book> books = new HashMap<>();
    List<BookRating> bookRatings = new ArrayList<>();
	
	@BeforeEach
	void setup() {
		bookImporterService.importBooks(books, authors, publishers);
		log.info("%d books were imported".formatted(books.size()));
		bookReaderImporterService.importBookReaders(bookReaders, locations);
		log.info("%d book readers were imported".formatted(bookReaders.size()));
		log.info("%d locations were imported".formatted(locations.values().stream().flatMap(location -> location.stream().map(entity -> entity.getLocationName())).count()));
		log.info("%d authors were imported".formatted(authorImporterService.countAll()));
		log.info("%d publishers were imported".formatted(publisherImporterService.countAll()));
	}
	
	@Test
	void testImportBookRatings () {
		bookRatingImporterService.importBookRatings(bookRatings, bookReaders, books);
		for (BookRating bookRating : bookRatings) {
			assertNotNull(bookRating.getBookReader());
			assertNotNull(bookRating.getBookReader().getBookReaderId());
			assertNotNull(bookRating.getBookReader().getId());
			assertNotNull(bookRating.getBookReader().getLocations());
			assertNotNull(bookRating.getBook());
			assertNotNull(bookRating.getBook().getAuthor().getAuthorName());
			assertNotNull(bookRating.getBook().getPublisher().getPublisherName());
			assertNotNull(bookRating.getRating());
			assertNotNull(bookRating.getId());
		}
		assertEquals(1031466, bookRatings.size());
	}
}
