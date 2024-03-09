package ua.com.foxmineded.library.utils.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.com.foxmineded.library.csvbeans.impl.AuthorCsv;
import ua.com.foxmineded.library.utils.AuthorCsvImporter;

@SpringBootTest(classes = {AuthorCsvImporterImpl.class})
class AuthorCsvImporterImplTest {
	@Autowired
	AuthorCsvImporter authorCsvImporter;

	@Test
	void testImportAllAuthorsFromCsvFile() {
		Set<AuthorCsv> set = authorCsvImporter.read();
		AuthorCsv authorCsv = set.stream().findFirst().get();
		assertNotNull(authorCsv.getAuthorName());
		assertEquals(102028, set.size());
	}
}
