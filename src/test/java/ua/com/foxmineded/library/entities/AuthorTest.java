package ua.com.foxmineded.library.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.com.foxmineded.library.config.TypeMapConfig;
import ua.com.foxmineded.library.dto.AuthorDto;
import ua.com.foxmineded.library.entities.impl.Author;
import ua.com.foxmineded.library.entities.impl.Book;

@SpringBootTest(classes = TypeMapConfig.class)
class AuthorTest {
	@Autowired
	ModelMapper modelMapper;
	
	@Test
	void testMapAuthorToAuthorDto() {
		Author author = Instancio.create(Author.class);
		List<Book> books = Instancio.ofList(Book.class).size(10).create();
		author.setBooks(books);
		AuthorDto authorDto = modelMapper.map(author, AuthorDto.class);
		assertEquals(author.getId(), authorDto.getId());
		assertEquals(author.getAuthorName(), authorDto.getAuthorName());
	}
	
	@Test
	void testMapAuthorDtoToAuthor() {
		AuthorDto authorDto = Instancio.create(AuthorDto.class);
		Author author = modelMapper.map(authorDto, Author.class);
		assertEquals(authorDto.getId(), author.getId());
		assertEquals(authorDto.getAuthorName(), author.getAuthorName());
		assertNull(author.getBooks());
	}

}
