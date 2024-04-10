package ua.com.foxmineded.libraryrestservice.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import ua.com.foxmineded.libraryrestservice.config.TypeMapConfig;
import ua.com.foxmineded.libraryrestservice.dao.PublisherRepository;
import ua.com.foxmineded.libraryrestservice.dto.PublisherDto;
import ua.com.foxmineded.libraryrestservice.entities.impl.Publisher;
import ua.com.foxmineded.libraryrestservice.exceptions.ServiceException;
import ua.com.foxmineded.libraryrestservice.services.PublisherService;

@SpringBootTest(classes = { PublisherServiceImpl.class, TypeMapConfig.class })
class PublisherServiceImplTest {
	@Autowired
	PublisherService publisherService;
	@MockBean
	PublisherRepository publisherRepository;

	@Test
	void testCreate_AskCreatePublisherIfPublisherWithNameAlreadyExists_ExceptionShouldBeThrown() {
		PublisherDto publisherDto = Instancio.of(PublisherDto.class).ignore(field(PublisherDto::getId)).create();
		Publisher publisher = Instancio.create(Publisher.class);
		String message = "The publisher with the name %s already exists".formatted(publisherDto.getPublisherName());
		when(publisherRepository.findByPublisherName(anyString())).thenReturn(Optional.of(publisher));
		Throwable throwable = assertThrows(ServiceException.class, () -> {
			publisherService.save(publisherDto);
		});
		assertEquals(message, throwable.getMessage());
	}

	@Test
	void testUpdate_AskUpdatePublisherIfPublisherWithNameAlreadyExists_ExceptionShouldBeThrown() {
		PublisherDto publisherDto = Instancio.of(PublisherDto.class)
				.set(field(PublisherDto::getPublisherName), "The Name 1").create();
		Publisher publisher = Instancio.of(Publisher.class).set(field(Publisher::getPublisherName), "The Name 2")
				.create();
		String message = "The publisher name cannot be updated. Sorry:(".formatted(publisherDto.getPublisherName());
		when(publisherRepository.findById(anyLong())).thenReturn(Optional.of(publisher));
		when(publisherRepository.findByPublisherName(anyString())).thenReturn(Optional.of(publisher));
		Throwable throwable = assertThrows(ServiceException.class, () -> {
			publisherService.save(publisherDto);
		});
		assertEquals(message, throwable.getMessage());
	}
}
