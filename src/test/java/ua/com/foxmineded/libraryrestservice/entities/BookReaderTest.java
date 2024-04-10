package ua.com.foxmineded.libraryrestservice.entities;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import static java.util.stream.Collectors.toCollection;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ua.com.foxmineded.libraryrestservice.config.TypeMapConfig;
import ua.com.foxmineded.libraryrestservice.dto.BookReaderDto;
import ua.com.foxmineded.libraryrestservice.entities.impl.BookRating;
import ua.com.foxmineded.libraryrestservice.entities.impl.BookReader;
import ua.com.foxmineded.libraryrestservice.entities.impl.Location;

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
		assertEquals(bookReader.getLocations().stream().map(Location::getId).collect(toCollection(HashSet::new)),
				bookReaderDto.getLocationIds());
		assertEquals(bookReader.getBookRatings().stream().map(BookRating::getId).toList(),
				bookReaderDto.getBookRatingIds());
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
	}

}
