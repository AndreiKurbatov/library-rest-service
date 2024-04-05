package ua.com.foxmineded.library.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxmineded.library.dao.PublisherRepository;
import ua.com.foxmineded.library.entities.impl.Publisher;
import ua.com.foxmineded.library.services.PublisherImporterService;
import ua.com.foxmineded.library.utils.PublisherCsvImporter;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		PublisherRepository.class, PublisherImporterService.class, PublisherCsvImporter.class}))
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
@Sql(scripts = { "/test/sql/clear-tables.sql" })
class PublisherImporterServiceImplTest {
	@Autowired
	PublisherImporterService publisherImporterService;
	
	Map<String, Publisher> publishers = new HashMap<>();

	@Test
	void testImportPublishers () {
		publisherImporterService.importPublishers(publishers);
		for (Publisher publisher : publishers.values()) {
			assertNotNull(publisher.getPublisherName());
			assertNull(publisher.getBooks());
		}
		assertEquals(16807, publishers.size());
	}
}
