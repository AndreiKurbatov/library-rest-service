package ua.com.foxmineded.library.dao;

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
import ua.com.foxmineded.library.entities.impl.Book;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {BookRepository.class}))
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
@Sql(scripts = { "/test/sql/clear_tables.sql" })
class BookRepositoryTest {
	@Autowired 
	BookRepository bookRepository;
	
	@Test
	@Sql(scripts = { "/test/sql/bookrepository/script0.sql" })
	void testFindAllByAuthorName_AskFindAllByAuthorName_AllBooksShouldBeFound() {
		String authorName = "Alice Johnson";
		Long bookId = 3L;
		Page<Book> books = bookRepository.findAllByAuthorName(authorName, Pageable.ofSize(1));
		assertEquals(1, books.getContent().size());
		assertEquals(bookId, books.getContent().get(0).getId());
	}
	
	@Test
	@Sql(scripts = { "/test/sql/bookrepository/script0.sql" })
	void testFindAllByPublisherName_AskFindAllByPublisherName_AllBooksShouldBeFound() {
		String publisherName = "ABC Publishing";
		Long bookId = 1L;
		Page<Book> books = bookRepository.findAllByPublisherName(publisherName, Pageable.ofSize(1));
		assertEquals(1, books.getContent().size());
		assertEquals(bookId, books.getContent().get(0).getId());
	}
}
