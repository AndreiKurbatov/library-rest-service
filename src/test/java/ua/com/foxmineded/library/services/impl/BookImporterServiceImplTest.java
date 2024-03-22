package ua.com.foxmineded.library.services.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
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

import ua.com.foxmineded.library.config.TypeMapConfig;
import ua.com.foxmineded.library.dao.AuthorRepository;
import ua.com.foxmineded.library.dao.BookRepository;
import ua.com.foxmineded.library.dao.PublisherRepository;
import ua.com.foxmineded.library.entities.impl.Book;
import ua.com.foxmineded.library.services.AuthorImporterService;
import ua.com.foxmineded.library.services.BookImporterService;
import ua.com.foxmineded.library.services.PublisherImporterService;
import ua.com.foxmineded.library.utils.AuthorCsvImporter;
import ua.com.foxmineded.library.utils.BookCsvImporter;
import ua.com.foxmineded.library.utils.PublisherCsvImporter;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		PublisherRepository.class, PublisherImporterService.class, PublisherCsvImporter.class, TypeMapConfig.class,
		AuthorImporterService.class, AuthorRepository.class, AuthorCsvImporter.class,
		BookImporterService.class, BookRepository.class, BookCsvImporter.class}))
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
@Sql(scripts = { "/test/sql/clear-tables.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_CLASS)
class BookImporterServiceImplTest {
	@Autowired
	BookImporterService bookImporterService;
	@Autowired
	AuthorImporterService authorImporterService;
	@Autowired
	PublisherImporterService publisherImporterService;
	@Disabled
	@BeforeEach
	void setup() {
		authorImporterService.importAuthors();
		publisherImporterService.importPublishers();
	}
	
	@Disabled
	@Test
	void testImportBooks() {
		List<Book> books = bookImporterService.importBooks();
		for (Book book : books) {
			assertNotNull(book.getId());
			assertNotNull(book.getIsbn());
			assertNotNull(book.getBookTitle());
			assertNotNull(book.getAuthor());
			assertNotNull(book.getAuthor().getAuthorName());
			assertNotNull(book.getAuthor().getId());
			assertNotNull(book.getPublisher());
			assertNotNull(book.getPublisher().getPublisherName());
			assertNotNull(book.getPublisher().getId());
			assertNotNull(book.getPublicationYear());
			assertNotNull(book.getImageUrlS());
			assertNotNull(book.getImageUrlM());
			assertNotNull(book.getImageUrlL());
		}
		assertEquals(271379, books.size());
	}
}
