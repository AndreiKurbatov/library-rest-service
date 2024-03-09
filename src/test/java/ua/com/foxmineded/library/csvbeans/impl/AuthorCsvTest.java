package ua.com.foxmineded.library.csvbeans.impl;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.com.foxmineded.library.config.TypeMapConfig;
import ua.com.foxmineded.library.entities.impl.Author;

@SpringBootTest(classes = {AuthorCsv.class, Author.class, TypeMapConfig.class})
class AuthorCsvTest {
	@Autowired
	ModelMapper modelMapper;

	@Test
	void testMapAuthorCsvToAuthor() {
		AuthorCsv authorCsv = new AuthorCsv();
		authorCsv.setAuthorName("book author");
		Author author = modelMapper.map(authorCsv, Author.class);
		assertNull(author.getId());
		assertEquals(authorCsv.getAuthorName(), author.getAuthorName());
	}
}
