package ua.com.foxmineded.library.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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
import ua.com.foxmineded.library.entities.impl.Location;
import ua.com.foxmineded.library.services.BookReaderImporterService;
import ua.com.foxmineded.library.utils.BookReaderCsvImporter;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {BookReaderRepository.class, BookReaderImporterService.class, BookReaderCsvImporter.class, TypeMapConfig.class}))
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
@Sql(scripts = { "/test/sql/clear-tables.sql" })
class BookReaderImporterServiceImplTest {
	@Autowired
	BookReaderImporterService bookReaderImporterService;
	
	Map<Long, BookReader> bookReaders = new HashMap<>();
	Map<Long, Set<Location>> locations = new HashMap<>();
	
	@Test
	void testImportBookReaders() {
		assertDoesNotThrow(() -> {
			bookReaderImporterService.importBookReaders(bookReaders, locations);
			for (BookReader bookReader : bookReaders.values()) {
				assertNotNull(bookReader.getId());
				assertNotNull(bookReader.getBookReaderId());
				assertNotNull(bookReader.getLocations());
				for (Location location : bookReader.getLocations()) {
					assertNotNull(location.getBookReader());
				}
			}
			assertEquals(278858, bookReaders.size());
		});
	}
}
