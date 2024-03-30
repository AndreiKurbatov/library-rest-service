package ua.com.foxmineded.library.utils.impl;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.com.foxmineded.library.csvbeans.impl.PublisherCsv;
import ua.com.foxmineded.library.utils.PublisherCsvImporter;

@SpringBootTest(classes = {PublisherCsvImporterImpl.class})
class PublisherCsvImporterImplTest {
	@Autowired
	PublisherCsvImporter csvImporter;
	
	@Test
	void testImportAllPublishersFromCsvFile() {
		List<PublisherCsv> publishers = csvImporter.read();
		assertNotNull(publishers.get(0).getPublisherName());
		assertEquals(16807, publishers.size());
	}
}
