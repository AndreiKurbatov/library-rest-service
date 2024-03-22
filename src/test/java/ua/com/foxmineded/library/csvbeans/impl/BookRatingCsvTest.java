package ua.com.foxmineded.library.csvbeans.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.com.foxmineded.library.config.TypeMapConfig;
import ua.com.foxmineded.library.entities.impl.BookRating;

@SpringBootTest(classes = {BookRating.class, BookRatingCsv.class, TypeMapConfig.class})
class BookRatingCsvTest {
	@Autowired
	ModelMapper modelMapper;
	
	@Disabled
	@Test
	void testMapBookRatingCsvToBookRating() {
		BookRatingCsv bookRatingCsv = new BookRatingCsv();
		bookRatingCsv.setBookReaderId(1L);
		bookRatingCsv.setIsbn("1111122222");
		bookRatingCsv.setBookRating(10);
		
		BookRating bookRating = modelMapper.map(bookRatingCsv, BookRating.class);
		assertNull(bookRating.getId());
		assertEquals(bookRatingCsv.getBookReaderId(), bookRating.getBookReader().getBookReaderId());
		assertEquals(bookRatingCsv.getIsbn(), bookRating.getBook().getIsbn());
		assertEquals(bookRatingCsv.getBookRating(), bookRating.getRating());
	}
}
