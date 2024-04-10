package ua.com.foxmineded.libraryrestservice.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxmineded.libraryrestservice.entities.impl.Book;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		BookRepository.class }))
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
@Sql(scripts = { "/test/sql/clear-tables.sql" })
class BookRepositoryTest {
	@Autowired
	BookRepository bookRepository;

	@Test
	@Sql(scripts = { "/test/sql/book-repository/script-0.sql" })
	void testFindAllByAuthorName_AskFindAllByAuthorName_AllBooksShouldBeFound() {
		String authorName = "Alice Johnson";
		Long bookId = 3L;
		Page<Book> books = bookRepository.findAllByAuthorName(Pageable.ofSize(1), authorName);
		assertEquals(1, books.getContent().size());
		assertEquals(bookId, books.getContent().get(0).getId());
	}

	@Test
	@Sql(scripts = { "/test/sql/book-repository/script-0.sql" })
	void testFindAllByPublisherName_AskFindAllByPublisherName_AllBooksShouldBeFound() {
		String publisherName = "ABC Publishing";
		Long bookId = 1L;
		Page<Book> books = bookRepository.findAllByPublisherName(Pageable.ofSize(1), publisherName);
		assertEquals(1, books.getContent().size());
		assertEquals(bookId, books.getContent().get(0).getId());
	}

	@Test
	@Sql(scripts = { "/test/sql/book-repository/script-1.sql" })
	void testFindAllBooksByAgeRange_AskFindAllBooksByAgeRange_AllBooksShouldBeFound() {
		Page<Book> booksFrom0To10 = bookRepository.findAllByAgeRange(Pageable.ofSize(10), 0, 10);
		assertEquals(1, booksFrom0To10.getContent().size());
		assertEquals("ISBN1", booksFrom0To10.getContent().get(0).getIsbn());

		Page<Book> booksFrom0To20 = bookRepository.findAllByAgeRange(Pageable.ofSize(10), 0, 20);
		assertEquals(2, booksFrom0To20.getContent().size());
		assertEquals("ISBN2", booksFrom0To20.getContent().get(0).getIsbn());
		assertEquals("ISBN1", booksFrom0To20.getContent().get(1).getIsbn());

		Page<Book> booksFrom0To30 = bookRepository.findAllByAgeRange(Pageable.ofSize(10), 0, 30);
		assertEquals(3, booksFrom0To30.getContent().size());
		assertEquals("ISBN3", booksFrom0To30.getContent().get(0).getIsbn());
		assertEquals("ISBN2", booksFrom0To30.getContent().get(1).getIsbn());
		assertEquals("ISBN1", booksFrom0To30.getContent().get(2).getIsbn());
	}

	@Test
	@Sql(scripts = { "/test/sql/book-repository/script-2.sql" })
	void testFindAllByLocationName_AskFindAllBooksByLocationName_AllBooksShouldBeFound() {
		Page<Book> sampleBook3 = bookRepository.findAllByLocationName(Pageable.ofSize(10), "location1");
		assertEquals(3, sampleBook3.getContent().size());

		Page<Book> sampleBook2 = bookRepository.findAllByLocationName(Pageable.ofSize(10), "location2");
		assertEquals(2, sampleBook2.getContent().size());

		Page<Book> sampleBook1 = bookRepository.findAllByLocationName(Pageable.ofSize(10), "location3");
		assertEquals(1, sampleBook1.getContent().size());
	}
}
