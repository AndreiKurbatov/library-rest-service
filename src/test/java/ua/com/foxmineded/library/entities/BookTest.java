package ua.com.foxmineded.library.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.com.foxmineded.library.config.TypeMapConfig;
import ua.com.foxmineded.library.dto.BookDto;
import ua.com.foxmineded.library.entities.impl.Book;
import ua.com.foxmineded.library.entities.impl.BookRating;

@SpringBootTest(classes = TypeMapConfig.class)
class BookTest {
	@Autowired
	ModelMapper modelMapper;

	@Test
	void testMapBookToBookDto() {
		Book book = Instancio.create(Book.class);
		List<BookRating> bookRatings = Instancio.ofList(BookRating.class).size(10).create();
		book.setBookRatings(bookRatings);
		
		BookDto bookDto = modelMapper.map(book, BookDto.class);
		
		assertEquals(book.getIsbn(), bookDto.getIsbn());
		assertEquals(book.getBookTitle(), bookDto.getBookTitle());
		assertEquals(book.getAuthor().getId(), bookDto.getAuthorId());
		assertEquals(book.getPublisher().getId(), bookDto.getPublisherId());
		assertEquals(book.getImageUrlS(), bookDto.getImageUrlS());
		assertEquals(book.getImageUrlM(), bookDto.getImageUrlM());
		assertEquals(book.getImageUrlL(), bookDto.getImageUrlL());
	}
	
	@Test
	void testMapBookDtoToBook() {
		BookDto bookDto = Instancio.create(BookDto.class);
		Book book = modelMapper.map(bookDto, Book.class);
		
		assertEquals(bookDto.getIsbn(), book.getIsbn());
		assertEquals(bookDto.getBookTitle(), book.getBookTitle());
		assertNull(book.getAuthor());
		assertNull(book.getPublisher());
		assertEquals(bookDto.getImageUrlS(), book.getImageUrlS());
		assertEquals(bookDto.getImageUrlM(), book.getImageUrlM());
		assertEquals(bookDto.getImageUrlL(), book.getImageUrlL());
	}

}
