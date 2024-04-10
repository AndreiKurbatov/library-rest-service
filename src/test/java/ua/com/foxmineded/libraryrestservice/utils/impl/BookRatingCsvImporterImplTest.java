package ua.com.foxmineded.libraryrestservice.utils.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ua.com.foxmineded.libraryrestservice.csvbeans.impl.BookRatingCsv;
import ua.com.foxmineded.libraryrestservice.utils.BookRatingCsvImporter;

@SpringBootTest(classes = { BookRatingCsvImporterImpl.class })
class BookRatingCsvImporterImplTest {
	@Autowired
	BookRatingCsvImporter bookRatingCsvImporter;

	@Test
	void testImportAllBookRatingsFromCsvFile() {
		List<BookRatingCsv> bookRatings = bookRatingCsvImporter.read();
		assertNotNull(bookRatings.get(0).getBookReaderId());
		assertNotNull(bookRatings.get(0).getIsbn());
		assertNotNull(bookRatings.get(0).getBookRating());
		assertEquals(1149780, bookRatings.size());
	}
}
