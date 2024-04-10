package ua.com.foxmineded.libraryrestservice.entities;

import static org.junit.jupiter.api.Assertions.*;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import ua.com.foxmineded.libraryrestservice.config.TypeMapConfig;
import ua.com.foxmineded.libraryrestservice.dao.BookRatingRepository;
import ua.com.foxmineded.libraryrestservice.dto.BookRatingDto;
import ua.com.foxmineded.libraryrestservice.entities.impl.BookRating;
import ua.com.foxmineded.libraryrestservice.exceptions.ServiceException;
import ua.com.foxmineded.libraryrestservice.services.BookRatingService;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = { TypeMapConfig.class,
		BookRatingService.class, BookRatingRepository.class }))
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
@Sql(scripts = "/test/sql/clear-tables.sql")
class BookRatingTest {
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	BookRatingService bookRatingService;
	@Autowired
	BookRatingRepository bookRatingRepository;

	@Test
	void testMapBookRatingToBookRatingDto() {
		BookRating bookRating = Instancio.create(BookRating.class);
		BookRatingDto bookRatingDto = modelMapper.map(bookRating, BookRatingDto.class);
		assertEquals(bookRating.getBookReader().getId(), bookRatingDto.getBookReaderId());
		assertEquals(bookRating.getBook().getId(), bookRatingDto.getBookId());
		assertEquals(bookRating.getRating(), bookRatingDto.getRating());
	}

	@Test
	@Sql(scripts = "/test/sql/book-rating-entity/script-0.sql")
	void testMapBookRatingDtoToBookRating() throws ServiceException {
		BookRatingDto bookRatingDto = new BookRatingDto(null, 10001L, 10002L, 10);
		BookRatingDto bookRatingDtoSaved = bookRatingService.save(bookRatingDto);
		BookRating bookRating = bookRatingRepository.findAll().get(0);
		assertNotNull(bookRatingDtoSaved.getId());
		assertEquals(bookRatingDtoSaved.getRating(), bookRating.getRating());
		assertEquals(10002L, bookRating.getBook().getId());
		assertEquals(10001L, bookRating.getBookReader().getId());

		assertEquals("1234567890", bookRating.getBook().getIsbn());
		assertEquals("Example Book Title", bookRating.getBook().getBookTitle());
		assertEquals(30, bookRating.getBookReader().getAge());
	}
}
