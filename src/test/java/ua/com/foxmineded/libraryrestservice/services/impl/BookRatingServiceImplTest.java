package ua.com.foxmineded.libraryrestservice.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.instancio.Select.field;
import java.util.Optional;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ua.com.foxmineded.libraryrestservice.dao.BookRatingRepository;
import ua.com.foxmineded.libraryrestservice.dao.BookReaderRepository;
import ua.com.foxmineded.libraryrestservice.dao.BookRepository;
import ua.com.foxmineded.libraryrestservice.dto.BookRatingDto;
import ua.com.foxmineded.libraryrestservice.entities.impl.BookRating;
import ua.com.foxmineded.libraryrestservice.exceptions.ServiceException;
import ua.com.foxmineded.libraryrestservice.services.BookRatingService;

@SpringBootTest(classes = { BookRatingServiceImpl.class, ModelMapper.class })
class BookRatingServiceImplTest {
	@Autowired
	BookRatingService bookRatingService;
	@Autowired
	ModelMapper modelMapper;
	@MockBean
	BookRatingRepository bookRatingRepository;
	@MockBean
	BookReaderRepository bookReaderRepository;
	@MockBean
	BookRepository bookRepository;

	@Test
	void testSave_AskSaveRatingIfFeedBackAlreadyGivenByUser_ExceptionShouldBeThrown() {
		BookRating bookRating = Instancio.create(BookRating.class);
		BookRatingDto bookRatingDto = Instancio.of(BookRatingDto.class).ignore(field(BookRatingDto::getId)).create();

		String message = "The book reader with id = %d already made feedback for the book with id = %d"
				.formatted(bookRatingDto.getBookReaderId(), bookRatingDto.getBookId());
		when(bookRatingRepository.findByBookReaderIdAndBookId(anyLong(), anyLong()))
				.thenReturn(Optional.of(bookRating));
		Throwable throwable = assertThrows(ServiceException.class, () -> {
			bookRatingService.save(bookRatingDto);
		});
		assertEquals(message, throwable.getMessage());
	}
}
