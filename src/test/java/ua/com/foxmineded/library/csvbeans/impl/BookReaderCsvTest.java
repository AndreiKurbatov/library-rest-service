package ua.com.foxmineded.library.csvbeans.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.com.foxmineded.library.config.TypeMapConfig;
import ua.com.foxmineded.library.entities.impl.BookReader;

@SpringBootTest(classes = { BookReader.class, BookReaderCsv.class, TypeMapConfig.class })
class BookReaderCsvTest {
	@Autowired
	ModelMapper modelMapper;

	@Test
	void testMapBookReaderCsvToBookReader() {
		BookReaderCsv bookReaderCsv = new BookReaderCsv();
		bookReaderCsv.setBookReaderId(1L);
		bookReaderCsv.setAge(12);

		BookReader bookReader = modelMapper.map(bookReaderCsv, BookReader.class);
		assertNull(bookReader.getId());
		assertNull(bookReader.getLocations());
		assertEquals(bookReaderCsv.getBookReaderId(), bookReader.getBookReaderId());
		assertEquals(bookReaderCsv.getAge(), bookReader.getAge());
	}
}
