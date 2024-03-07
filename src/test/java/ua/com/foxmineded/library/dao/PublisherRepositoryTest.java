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
import ua.com.foxmineded.library.entities.impl.Publisher;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {PublisherRepository.class}))
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
@Sql(scripts = { "/test/sql/clear_tables.sql" })
class PublisherRepositoryTest {
	@Autowired
	PublisherRepository publisherRepository;
	
	@Test
	@Sql(scripts = { "/test/sql/publisherrepository/script0.sql" })
	void testFindByBookTitle_AskFindPublisherByBookTitle_PublisherShouldBeFound() {
		String bookTitle = "Sample Book 1";
		Long publisherId = 1L;
		Publisher publisher = publisherRepository.findByBookTitle(bookTitle).get();
		assertEquals(publisherId, publisher.getId());
	}
	
	@Test
	@Sql(scripts = { "/test/sql/publisherrepository/script0.sql" })
	void testFindByIsbn_AskFindPublisherByIsbn_PublisherShouldBeFound() {
		String isbn = "0987654321";
		Long publisherId = 2L;
		Publisher publisher = publisherRepository.findByIsbn(isbn).get();
		assertEquals(publisherId, publisher.getId());
	}
	
	@Test
	@Sql(scripts = { "/test/sql/publisherrepository/script0.sql" })
	void testFindByAuthorName_AskFindPublisherByAuthorName_PublisherShouldBeFound() {
		String authorName = "Jane Smith";
		Long publisherId = 2L;
		Page<Publisher> publishers = publisherRepository.findAllByAuthorName(authorName, Pageable.ofSize(1));
		assertEquals(1, publishers.getContent().size());
		assertEquals(publisherId, publishers.getContent().get(0).getId());
	}
}
