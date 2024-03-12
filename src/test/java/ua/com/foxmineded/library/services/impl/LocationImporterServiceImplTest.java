package ua.com.foxmineded.library.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
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
import ua.com.foxmineded.library.dao.LocationRepository;
import ua.com.foxmineded.library.entities.impl.Location;
import ua.com.foxmineded.library.services.BookReaderImporterService;
import ua.com.foxmineded.library.services.LocationImporterService;
import ua.com.foxmineded.library.utils.BookReaderCsvImporter;
import ua.com.foxmineded.library.utils.LocationCsvImporter;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		LocationRepository.class, LocationImporterService.class, LocationCsvImporter.class, TypeMapConfig.class,
		BookReaderRepository.class, BookReaderImporterService.class, BookReaderCsvImporter.class}))
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
@Sql(scripts = { "/test/sql/clear_tables.sql" })
class LocationImporterServiceImplTest {
	@Autowired
	LocationImporterService locationImporterService;
	@Autowired
	BookReaderImporterService bookReaderImporterService;
	
	@BeforeEach
	void setup() {
		bookReaderImporterService.importBookReaders();
	}
	
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
