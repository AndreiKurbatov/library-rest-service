package ua.com.foxmineded.library.dao;

import static org.junit.Assert.assertNotNull;
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
import ua.com.foxmineded.library.entities.impl.BookRating;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		BookRatingRepository.class
}))
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test") 
@Sql(scripts = { "/test/sql/clear_tables.sql" })
class BookRatingRepositoryTest {
	@Autowired
	BookRatingRepository bookRatingRepository;
	
	@Test
	@Sql(scripts = { "/test/sql/book-rating-repository/script-0.sql" })
	void testFindAllRatingsByIsbn_AskFindAllBookRatingsByIsbn_AllRelatedRatingsShouldBeFound() {
		Page<BookRating> bookRatings = bookRatingRepository.findAllByBookId(Pageable.ofSize(10), 10001L);
		assertEquals(3, bookRatings.getContent().size());
		for (BookRating bookRating : bookRatings.getContent()) {
			assertNotNull(bookRating.getId());
			assertNotNull(bookRating.getBookReader());
			assertNotNull(bookRating.getBook());
			assertNotNull(bookRating.getBookRating());
		}
	}
	
	@Test
	@Sql(scripts = { "/test/sql/book-rating-repository/script-0.sql" })
	void testFindByBookReaderIdAndIsbn_AskFindAllBookRatingsByIsbn_AllRelatedRatingsShouldBeFound() {
		BookRating bookRating = bookRatingRepository.findByBookReaderIdAndIsbn(10001L, 10001L).get();
		assertNotNull(bookRating.getId());
		assertNotNull(bookRating.getBookReader());
		assertNotNull(bookRating.getBook());
		assertNotNull(bookRating.getBookRating());
	}
}
