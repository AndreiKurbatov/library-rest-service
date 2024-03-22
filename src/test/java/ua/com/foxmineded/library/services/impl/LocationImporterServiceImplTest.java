package ua.com.foxmineded.library.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import lombok.extern.slf4j.Slf4j;
import ua.com.foxmineded.library.config.TypeMapConfig;
import ua.com.foxmineded.library.dao.BookReaderRepository;
import ua.com.foxmineded.library.dao.LocationRepository;
import ua.com.foxmineded.library.entities.impl.BookReader;
import ua.com.foxmineded.library.entities.impl.Location;
import ua.com.foxmineded.library.services.BookReaderImporterService;
import ua.com.foxmineded.library.services.LocationImporterService;
import ua.com.foxmineded.library.utils.BookReaderCsvImporter;
import ua.com.foxmineded.library.utils.LocationCsvImporter;

@Slf4j
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		LocationRepository.class, LocationImporterService.class, LocationCsvImporter.class, TypeMapConfig.class,
		BookReaderRepository.class, BookReaderImporterService.class, BookReaderCsvImporter.class}))
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
@Sql(scripts = { "/test/sql/clear-tables.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_CLASS)
class LocationImporterServiceImplTest {
	@Autowired
	LocationImporterService locationImporterService;
	@Autowired
	BookReaderImporterService bookReaderImporterService;
	@Disabled
	@BeforeEach
	void setup() {
		List<BookReader> bookReaders = bookReaderImporterService.importBookReaders();
		log.info(bookReaders.size() + " book readers were imported");
	}
	@Disabled
	@Test
	void testImportLocations() {
		List<Location> locations = locationImporterService.importLocations();
		for (Location location : locations) {
			assertNotNull(location.getId());
			assertNotNull(location.getLocationName());
			assertNotNull(location.getBookReader());
			assertNotNull(location.getBookReader().getId());
			assertNotNull(location.getBookReader().getBookReaderId());
		}
		assertEquals(833085, locations.size());
	}
}
