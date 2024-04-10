package ua.com.foxmineded.libraryrestservice.services.impl;

import org.instancio.Instancio;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ua.com.foxmineded.libraryrestservice.config.TypeMapConfig;
import ua.com.foxmineded.libraryrestservice.dao.BookReaderRepository;
import ua.com.foxmineded.libraryrestservice.dto.BookReaderDto;
import ua.com.foxmineded.libraryrestservice.entities.impl.BookReader;
import ua.com.foxmineded.libraryrestservice.exceptions.ServiceException;
import ua.com.foxmineded.libraryrestservice.services.BookReaderService;

@SpringBootTest(classes = { BookReaderServiceImpl.class, TypeMapConfig.class })
class BookReaderServiceImplTest {
	@Autowired
	BookReaderService bookReaderService;
	@MockBean
	BookReaderRepository bookReaderRepository;

	@Test
	void testCreate_AskCreateIfBookReaderWithSuchIdAlreadyExists_ExceptionShouldBeThrown() {
		BookReaderDto bookReaderDto = Instancio.of(BookReaderDto.class).ignore(field(BookReaderDto::getId)).create();
		BookReader bookReader = Instancio.of(BookReader.class)
				.set(field(BookReader::getBookReaderId), bookReaderDto.getBookReaderId()).create();

		when(bookReaderRepository.findByBookReaderId(anyLong())).thenReturn(Optional.of(bookReader));

		String message = "The book reader with book reader id = %d already exists"
				.formatted(bookReaderDto.getBookReaderId());

		Throwable throwable = assertThrows(ServiceException.class, () -> {
			bookReaderService.save(bookReaderDto);
		});
		assertEquals(message, throwable.getMessage());
	}

	@Test
	void testUpdate_AskUpdateBookReaderIfBookReaderIdIsGoingToBeUpdated_ExceptionShouldBeThrown() {
		BookReaderDto bookReaderDto = Instancio.create(BookReaderDto.class);
		BookReader bookReader = Instancio.create(BookReader.class);

		when(bookReaderRepository.findById(anyLong())).thenReturn(Optional.of(bookReader));

		String message = "The book reader id cannot be updated";

		Throwable throwable = assertThrows(ServiceException.class, () -> {
			bookReaderService.save(bookReaderDto);
		});
		assertEquals(message, throwable.getMessage());
	}

}
