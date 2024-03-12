package ua.com.foxmineded.library.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxmineded.library.config.TypeMapConfig;
import ua.com.foxmineded.library.dao.BookReaderRepository;
import ua.com.foxmineded.library.entities.impl.BookReader;
import ua.com.foxmineded.library.services.BookReaderImporterService;
import ua.com.foxmineded.library.utils.BookReaderCsvImporter;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {BookReaderRepository.class, BookReaderImporterService.class, BookReaderCsvImporter.class, TypeMapConfig.class}))
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
@Sql(scripts = { "/test/sql/clear_tables.sql" })
class BookReaderImporterServiceImplTest {
	@Autowired
	BookReaderImporterService bookReaderImporterService;
	
	@Test
	void testImportBookReaders() {
		assertDoesNotThrow(() -> {
			List<BookReader> list = bookReaderImporterService.importBookReaders();
			System.out.println(list.get(1));
			for (BookReader bookReader : list) {
				assertNotNull(bookReader.getId());
				assertNotNull(bookReader.getBookReaderId());
				assertNull(bookReader.getLocations());
			}
			assertEquals(278858, list.size());
		});
	}
}
