package ua.com.foxmineded.library.utils.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.com.foxmineded.library.csvbeans.impl.LocationCsv;
import ua.com.foxmineded.library.utils.LocationCsvImporter;

@SpringBootTest(classes = {LocationCsvImporterImpl.class})
class LocationCsvImporterImplTest {
	@Autowired
	LocationCsvImporter csvImporter;

	@Disabled
	@Test
	void testImportAllLocationsFromCsvFile() {
		List<LocationCsv> locations = csvImporter.read();
		assertNotNull(locations.get(0).getBookReaderId());
		assertNotNull(locations.get(0).getLocationName());
		assertEquals(278858, locations.size());
	}

}
