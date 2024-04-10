package ua.com.foxmineded.libraryrestservice.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ua.com.foxmineded.libraryrestservice.config.TypeMapConfig;
import ua.com.foxmineded.libraryrestservice.dto.PublisherDto;
import ua.com.foxmineded.libraryrestservice.entities.impl.Book;
import ua.com.foxmineded.libraryrestservice.entities.impl.Publisher;

@SpringBootTest(classes = TypeMapConfig.class)
class PublisherTest {
	@Autowired
	ModelMapper modelMapper;

	@Test
	void testMapPublisherToPublisherDto() {
		Publisher publisher = Instancio.create(Publisher.class);
		PublisherDto publisherDto = modelMapper.map(publisher, PublisherDto.class);

		assertEquals(publisher.getId(), publisherDto.getId());
		assertEquals(publisher.getPublisherName(), publisherDto.getPublisherName());
		assertEquals(publisher.getBooks().stream().map(Book::getId).toList(), publisherDto.getBookIds());
	}

	@Test
	void testMapPublisherDtoToPublisher() {
		PublisherDto publisherDto = Instancio.create(PublisherDto.class);
		Publisher publisher = modelMapper.map(publisherDto, Publisher.class);

		assertEquals(publisherDto.getId(), publisher.getId());
		assertEquals(publisherDto.getPublisherName(), publisher.getPublisherName());
	}
}
