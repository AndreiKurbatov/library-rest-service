package ua.com.foxmineded.library.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
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
import ua.com.foxmineded.library.config.TypeMapConfig;
import ua.com.foxmineded.library.dao.AuthorRepository;
import ua.com.foxmineded.library.dao.BookRatingRepository;
import ua.com.foxmineded.library.dao.BookReaderRepository;
import ua.com.foxmineded.library.dao.BookRepository;
import ua.com.foxmineded.library.dao.LocationRepository;
import ua.com.foxmineded.library.dao.PublisherRepository;
import ua.com.foxmineded.library.entities.impl.BookRating;
import ua.com.foxmineded.library.services.BookImporterService;
import ua.com.foxmineded.library.services.BookRatingImporterService;
import ua.com.foxmineded.library.services.BookReaderImporterService;
import ua.com.foxmineded.library.services.LocationImporterService;
import ua.com.foxmineded.library.services.PublisherImporterService;
import ua.com.foxmineded.library.utils.AuthorCsvImporter;
import ua.com.foxmineded.library.utils.BookCsvImporter;
import ua.com.foxmineded.library.utils.BookRatingCsvImporter;
import ua.com.foxmineded.library.utils.BookReaderCsvImporter;
import ua.com.foxmineded.library.utils.LocationCsvImporter;
import ua.com.foxmineded.library.utils.PublisherCsvImporter;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		PublisherRepository.class, PublisherImporterService.class, PublisherCsvImporter.class, TypeMapConfig.class,
		AuthorImporterService.class, AuthorRepository.class, AuthorCsvImporter.class,
		BookImporterService.class, BookRepository.class, BookCsvImporter.class,
		BookReaderRepository.class, BookReaderImporterService.class, BookReaderCsvImporter.class,
		LocationRepository.class, LocationImporterService.class, LocationCsvImporter.class,
		BookRatingRepository.class, BookRatingImporterService.class, BookRatingCsvImporter.class
		}))
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
@Sql(scripts = { "/test/sql/clear_tables.sql" })
class BookRatingImporterServiceImplTest {
	@Autowired
	PublisherImporterService publisherImporterService;
	@Autowired
	AuthorImporterService authorImporterService;
	@Autowired
	BookReaderImporterService bookReaderImporterService;
	@Autowired
	LocationImporterService locationImporterService;
	@Autowired
	BookImporterService bookImporterService;
	@Autowired
	BookRatingImporterService bookRatingImporterService;
	
	@BeforeEach
	void setup() {
		publisherImporterService.importPublishers();
		authorImporterService.importAuthors();
		bookReaderImporterService.importBookReaders();
		locationImporterService.importLocations();
		bookImporterService.importBooks();
	}

	@Test
	void testImportBookRatings () {
		List<BookRating> bookRatings = bookRatingImporterService.importBookRatings();
		for (BookRating bookRating : bookRatings) {
			assertNotNull(bookRating.getBookReader());
			assertNotNull(bookRating.getBookReader().getBookReaderId());
			assertNotNull(bookRating.getBookReader().getId());
			assertNotNull(bookRating.getBookReader().getLocations());
			assertNotNull(bookRating.getBook());
			assertNotNull(bookRating.getBook().getAuthorName());
			assertNotNull(bookRating.getBook().getPublisherName());
			assertNotNull(bookRating.getBookRating());
			assertNotNull(bookRating.getId());
		}
		System.out.println(bookRatings.size());
	}
}
