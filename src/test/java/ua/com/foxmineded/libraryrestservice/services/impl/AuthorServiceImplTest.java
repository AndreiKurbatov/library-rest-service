package ua.com.foxmineded.libraryrestservice.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import java.util.Optional;

import org.instancio.Instancio;
import static org.instancio.Select.field;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import ua.com.foxmineded.libraryrestservice.config.TypeMapConfig;
import ua.com.foxmineded.libraryrestservice.dao.AuthorRepository;
import ua.com.foxmineded.libraryrestservice.dto.AuthorDto;
import ua.com.foxmineded.libraryrestservice.entities.impl.Author;
import ua.com.foxmineded.libraryrestservice.exceptions.ServiceException;
import ua.com.foxmineded.libraryrestservice.services.AuthorService;

@SpringBootTest(classes = { AuthorServiceImpl.class, TypeMapConfig.class })
@ActiveProfiles("test")
class AuthorServiceImplTest {
	@Autowired
	AuthorService authorService;
	@MockBean
	AuthorRepository authorRepository;

	@Test
	void testCreate_AskSaveAuthorIfAuthorNameAlreadyExists_ExceptionShouldBeThrown() {
		Author author = Instancio.of(Author.class).set(field(Author::getAuthorName), "Author Name").create();
		AuthorDto authorDto = Instancio.of(AuthorDto.class).set(field(AuthorDto::getAuthorName), "Author Name")
				.create();
		String message = "The author with the name %s already exists".formatted(authorDto.getAuthorName());

		when(authorRepository.findByAuthorName(anyString())).thenReturn(Optional.of(author));
		Throwable throwable = assertThrows(ServiceException.class, () -> {
			authorService.save(authorDto);
		});
		assertEquals(message, throwable.getMessage());
	}

	@Test
	void testUpdate_AskUpdateAuthorUsingAuthorName_ExceptionShouldBeThrown() {
		Author author = Instancio.of(Author.class).set(field(Author::getId), 10L)
				.set(field(Author::getAuthorName), "Author Name1").create();
		AuthorDto authorDto = Instancio.of(AuthorDto.class).set(field(AuthorDto::getId), 20L)
				.set(field(AuthorDto::getAuthorName), "Author Name2").create();
		String message = "The author name cannot be updated";

		when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));
		Throwable throwable = assertThrows(ServiceException.class, () -> {
			authorService.save(authorDto);
		});
		assertEquals(message, throwable.getMessage());
	}

}
