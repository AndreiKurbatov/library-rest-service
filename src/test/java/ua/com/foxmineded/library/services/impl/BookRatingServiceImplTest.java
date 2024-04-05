package ua.com.foxmineded.library.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ua.com.foxmineded.library.dao.BookRatingRepository;
import ua.com.foxmineded.library.dto.BookRatingDto;
import ua.com.foxmineded.library.entities.impl.BookRating;
import ua.com.foxmineded.library.exceptions.ServiceException;
import ua.com.foxmineded.library.services.BookRatingService;

@SpringBootTest(classes = {BookRatingServiceImpl.class, ModelMapper.class})
class BookRatingServiceImplTest {
	@Autowired
	BookRatingService bookRatingService;
	@Autowired
	ModelMapper modelMapper;
	@MockBean
	BookRatingRepository bookRatingRepository;
	
	@Test
	void testSave_AskSaveRatingIfFeedBackAlreadyGivenByUser_ExceptionShouldBeThrown () {
		BookRating bookRating = Instancio.create(BookRating.class);
		BookRatingDto bookRatingDto = modelMapper.map(bookRating, BookRatingDto.class);
		String message = "The book reader with id = %d already made feedback for the book with id = %d".formatted(bookRatingDto.getBookReaderId(), bookRatingDto.getBookId());
		when(bookRatingRepository.findByBookReaderIdAndBookId(anyLong(), anyLong())).thenReturn(Optional.of(bookRating));
		Throwable throwable = assertThrows(ServiceException.class, () -> {
			bookRatingService.save(bookRatingDto);
		});
		assertEquals(message, throwable.getMessage());
	}
}
