package ua.com.foxmineded.libraryrestservice.utils.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static java.util.stream.Collectors.toCollection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ua.com.foxmineded.libraryrestservice.csvbeans.impl.AuthorCsv;
import ua.com.foxmineded.libraryrestservice.utils.AuthorCsvImporter;

@SpringBootTest(classes = { AuthorCsvImporterImpl.class })
class AuthorCsvImporterImplTest {
	@Autowired
	AuthorCsvImporter authorCsvImporter;

	@Test
	void testImportAllAuthorsFromCsvFile() {
		List<AuthorCsv> list = authorCsvImporter.read();
		Set<String> namesSet = list.stream().map(author -> author.getAuthorName()).collect(toCollection(HashSet::new));
		AuthorCsv authorCsv = list.stream().findFirst().get();
		assertNotNull(authorCsv.getAuthorName());
		assertEquals(namesSet.size(), list.size());
		assertEquals(102028, list.size());
	}
}
