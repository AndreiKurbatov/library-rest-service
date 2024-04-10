package ua.com.foxmineded.libraryrestservice.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ua.com.foxmineded.libraryrestservice.config.TypeMapConfig;
import ua.com.foxmineded.libraryrestservice.dto.BookDto;
import ua.com.foxmineded.libraryrestservice.entities.impl.Book;
import ua.com.foxmineded.libraryrestservice.entities.impl.BookRating;

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
		assertEquals(book.getAuthor().getAuthorName(), bookDto.getAuthorName());
		assertEquals(book.getPublisher().getPublisherName(), bookDto.getPublisherName());
		assertEquals(book.getBookRatings().stream().map(BookRating::getId).toList(), bookDto.getBookRatingIds());
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
