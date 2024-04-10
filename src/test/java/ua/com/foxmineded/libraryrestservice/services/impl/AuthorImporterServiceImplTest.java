package ua.com.foxmineded.libraryrestservice.services.impl;

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

import ua.com.foxmineded.libraryrestservice.config.TypeMapConfig;
import ua.com.foxmineded.libraryrestservice.dao.AuthorRepository;
import ua.com.foxmineded.libraryrestservice.entities.impl.Author;
import ua.com.foxmineded.libraryrestservice.services.AuthorImporterService;
import ua.com.foxmineded.libraryrestservice.utils.AuthorCsvImporter;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		AuthorImporterService.class, AuthorRepository.class, TypeMapConfig.class, AuthorCsvImporter.class }))
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
@Sql(scripts = { "/test/sql/clear-tables.sql" })
class AuthorImporterServiceImplTest {
	@Autowired
	AuthorImporterService authorImporterService;

	Map<String, Author> authors = new HashMap<>();

	@Test
	void testImportAuthors() {
		assertDoesNotThrow(() -> {
			authorImporterService.importAuthors(authors);
			for (Author author : authors.values()) {
				assertNotNull(author.getId());
				assertNotNull(author.getAuthorName());
				assertNull(author.getBooks());
			}
			assertEquals(102028, authors.size());
		});
	}
}
