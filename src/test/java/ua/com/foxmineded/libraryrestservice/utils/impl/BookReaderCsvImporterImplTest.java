package ua.com.foxmineded.libraryrestservice.utils.impl;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ua.com.foxmineded.libraryrestservice.csvbeans.impl.BookReaderCsv;
import ua.com.foxmineded.libraryrestservice.utils.BookReaderCsvImporter;

@SpringBootTest(classes = { BookReaderCsvImporterImpl.class })
class BookReaderCsvImporterImplTest {
	@Autowired
	BookReaderCsvImporter bookReaderCsvImporter;

	@Test
	void testImportAllBookReadersFromCsvFile() {
		List<BookReaderCsv> bookReaders = bookReaderCsvImporter.read();
		assertNotNull(bookReaders.get(0).getBookReaderId());
		assertNull(bookReaders.get(0).getAge());
		assertEquals(278858, bookReaders.size());
	}

}
