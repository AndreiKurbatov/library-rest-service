package ua.com.foxmineded.library.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ua.com.foxmineded.library.config.TypeMapConfig;
import ua.com.foxmineded.library.dao.PublisherRepository;
import ua.com.foxmineded.library.dto.PublisherDto;
import ua.com.foxmineded.library.entities.impl.Publisher;
import ua.com.foxmineded.library.exceptions.ServiceException;
import ua.com.foxmineded.library.services.PublisherService;

@SpringBootTest(classes = {PublisherServiceImpl.class, TypeMapConfig.class})
class PublisherServiceImplTest {
	@Autowired
	PublisherService publisherService;
	@MockBean 
	PublisherRepository publisherRepository;
	
	@Test
	void testSave_AskSavePublisherIfPublisherWithNameAlreadyExists_ExceptionShouldBeThrown() {
		PublisherDto publisherDto = Instancio.create(PublisherDto.class);
		Publisher publisher = Instancio.create(Publisher.class);
		String message = "The publisher with the name %s already exists".formatted(publisherDto.getPublisherName());
		when(publisherRepository.findByPublisherName(anyString())).thenReturn(Optional.of(publisher));
		Throwable throwable = assertThrows(ServiceException.class, () -> {
			publisherService.save(publisherDto);
		});
		assertEquals(message, throwable.getMessage());
	}
}
