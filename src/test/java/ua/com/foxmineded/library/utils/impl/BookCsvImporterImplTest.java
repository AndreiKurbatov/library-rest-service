package ua.com.foxmineded.library.utils.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.com.foxmineded.library.csvbeans.impl.BookCsv;
import ua.com.foxmineded.library.utils.BookCsvImporter;

@SpringBootTest(classes = {BookCsvImporterImpl.class})
class BookCsvImporterImplTest {
	@Autowired
	BookCsvImporter bookCsvImporter;

	@Disabled
	@Test
	void testImportAllBooksFromCsvFile() {
		List<BookCsv> books = bookCsvImporter.read();
		assertNotNull(books.get(0).getIsbn());
		assertNotNull(books.get(0).getBookTitle());
		assertNotNull(books.get(0).getAuthorName());
		assertNotNull(books.get(0).getYearOfPublication());
		assertNotNull(books.get(0).getPublisherName());
		assertNotNull(books.get(0).getImageUrlS());
		assertNotNull(books.get(0).getImageUrlM());
		assertNotNull(books.get(0).getImageUrlL());
		assertEquals(271379,books.size());
	}
}

