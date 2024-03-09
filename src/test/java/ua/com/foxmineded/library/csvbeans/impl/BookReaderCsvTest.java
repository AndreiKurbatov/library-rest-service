package ua.com.foxmineded.library.csvbeans.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.com.foxmineded.library.config.TypeMapConfig;
import ua.com.foxmineded.library.entities.impl.BookReader;

@SpringBootTest(classes = { BookReader.class, BookReaderCsv.class, TypeMapConfig.class })
public class BookReaderCsvTest {
	@Autowired
	ModelMapper modelMapper;

	void testMapBookReaderCsvToBookReader() {
		BookReaderCsv bookReaderCsv = new BookReaderCsv();
		bookReaderCsv.setBookReaderId(1L);
		bookReaderCsv.setAge(12);

		BookReader bookReader = modelMapper.map(bookReaderCsv, BookReader.class);
		assertNull(bookReader.getId());
		assertEquals(bookReaderCsv.getBookReaderId(), bookReader.getBookReaderId());
		assertEquals(bookReaderCsv.getAge(), bookReader.getAge());
	}
}
