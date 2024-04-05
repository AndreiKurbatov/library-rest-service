package ua.com.foxmineded.library.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import java.util.Optional;
import ua.com.foxmineded.library.entities.impl.Author;
import ua.com.foxmineded.library.exceptions.ServiceException;
import org.instancio.Instancio;
import static org.instancio.Select.field;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ua.com.foxmineded.library.config.TypeMapConfig;
import ua.com.foxmineded.library.dao.AuthorRepository;
import ua.com.foxmineded.library.dto.AuthorDto;
import ua.com.foxmineded.library.services.AuthorService;

@SpringBootTest(classes = {AuthorServiceImpl.class, TypeMapConfig.class})
@ActiveProfiles("test")
class AuthorServiceImplTest {
	@Autowired
	AuthorService authorService;
	@MockBean
	AuthorRepository authorRepository;
	
	@Test
	void testSave_AskSaveAuthorIfAuthorNameAlreadyExists_ExceptionShouldBeThrown() {
		Author author = Instancio.of(Author.class) 
				.set(field(Author::getAuthorName), "Author Name") 
				.create();
		AuthorDto authorDto = Instancio.of(AuthorDto.class) 
				.set(field(AuthorDto::getAuthorName), "Author Name") 
				.create();
		String message = "The author with the name %s already exists".formatted(authorDto.getAuthorName());
		
		when(authorRepository.findByAuthorName(anyString())).thenReturn(Optional.of(author));
		Throwable throwable = assertThrows(ServiceException.class, () -> {
			authorService.save(authorDto);
		});
		assertEquals(message, throwable.getMessage());
	}
}
