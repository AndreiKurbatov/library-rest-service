package ua.com.foxmineded.library.entities;

import static org.junit.jupiter.api.Assertions.*;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.com.foxmineded.library.config.TypeMapConfig;
import ua.com.foxmineded.library.dto.BookRatingDto;
import ua.com.foxmineded.library.entities.impl.BookRating;

@SpringBootTest(classes =  TypeMapConfig.class)
class BookRatingTest {
	@Autowired
	ModelMapper modelMapper;

	@Test
	void testMapBookRatingToBookRatingDto() {
		BookRating bookRating = Instancio.create(BookRating.class);
		BookRatingDto bookRatingDto = modelMapper.map(bookRating, BookRatingDto.class);
		assertEquals(bookRating.getBookReader().getId(), bookRatingDto.getBookReaderId());
		assertEquals(bookRating.getBook().getId(), bookRatingDto.getBookId());
		assertEquals(bookRating.getRating(), bookRatingDto.getRating());
	}
	
	@Test
	void testMapBookRatingDtoToBookRating() {
		BookRatingDto bookRatingDto = Instancio.create(BookRatingDto.class);
		BookRating bookRating = modelMapper.map(bookRatingDto, BookRating.class);
		assertEquals(bookRatingDto.getId(), bookRating.getId());
		assertEquals(bookRatingDto.getRating(), bookRating.getRating());
		assertNull(bookRating.getBook());
		assertNull(bookRating.getBookReader());
	}

}
