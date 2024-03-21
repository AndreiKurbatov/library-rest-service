package ua.com.foxmineded.library.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ua.com.foxmineded.library.config.TypeMapConfig;
import ua.com.foxmineded.library.dto.BookReaderDto;
import ua.com.foxmineded.library.entities.impl.BookReader;

@SpringBootTest(classes = TypeMapConfig.class)
class BookReaderTest {
	@Autowired
	ModelMapper modelMapper;

	@Test
	void testMapBookReaderToBookReaderDto() {
		BookReader bookReader = Instancio.create(BookReader.class);
		BookReaderDto bookReaderDto = modelMapper.map(bookReader, BookReaderDto.class);
		assertEquals(bookReader.getId(), bookReaderDto.getId());
		assertEquals(bookReader.getBookReaderId(), bookReaderDto.getBookReaderId());
		assertEquals(bookReader.getAge(), bookReaderDto.getAge());
	}
	
	@Test
	void testMapBookReaderDtoToBookReader() {
		BookReaderDto bookReaderDto = Instancio.create(BookReaderDto.class);
		BookReader bookReader = modelMapper.map(bookReaderDto, BookReader.class);
		assertEquals(bookReaderDto.getId(), bookReader.getId());
		assertEquals(bookReaderDto.getBookReaderId(), bookReader.getBookReaderId());
		assertEquals(bookReaderDto.getAge(), bookReader.getAge());
		assertNull(bookReader.getLocations());
		assertNull(bookReader.getBookRatings());
		assertNull(bookReader.getBooks());
	}

}
