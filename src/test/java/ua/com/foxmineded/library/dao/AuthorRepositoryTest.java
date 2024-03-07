package ua.com.foxmineded.library.dao;

import static org.junit.jupiter.api.Assertions.*;
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
import ua.com.foxmineded.library.entities.impl.Author;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {AuthorRepository.class}))
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
@Sql(scripts = { "/test/sql/clear_tables.sql" })
class AuthorRepositoryTest {
	@Autowired
	AuthorRepository authorRepository;

	@Test
	@Sql(scripts = { "/test/sql/authorrepository/script0.sql" })
	void testFindByIsbn_AskfindAuthorByIsbn_AuthorShouldBeFound () {
		String isbn = "1234567890";
		Long authorId = 1L;
		Author author = authorRepository.findByIsbn(isbn).get();
		assertEquals(authorId, author.getId());
	}
	
	@Test 
	@Sql(scripts = { "/test/sql/authorrepository/script0.sql" })
	void testFindByBookTitle_AskFindAuthorByBookTitle_AuthorShouldBeFound() {
		String bookTitle = "Sample Book 1";
		Long authorId = 1L;
		Author author = authorRepository.findByBookTitle(bookTitle).get();
		assertEquals(authorId, author.getId());
	}

	@Test
	@Sql(scripts = { "/test/sql/authorrepository/script0.sql" })
	void testFindAllByPublisherName_AskFindAllAuthorsByPublisherName_AuthorsShouldBeFound() {
		String publisherName = "ABC Publishing";
		Long authorId = 1L;
		Page<Author> authors = authorRepository.findAllByPublisherName(publisherName, Pageable.ofSize(1));
		assertEquals(1, authors.getContent().size());
		assertEquals(authorId, authors.getContent().get(0).getId());
	}
}
