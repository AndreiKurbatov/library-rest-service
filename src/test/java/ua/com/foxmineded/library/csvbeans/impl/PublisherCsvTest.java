package ua.com.foxmineded.library.csvbeans.impl;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.com.foxmineded.library.config.TypeMapConfig;
import ua.com.foxmineded.library.entities.impl.Publisher;

@SpringBootTest(classes = {Publisher.class, PublisherCsv.class, TypeMapConfig.class})
class PublisherCsvTest {
	@Autowired
	ModelMapper modelMapper;
	
	@Test
	void testMapPublisherCsvToPublisher() {
		PublisherCsv publisherCsv = new PublisherCsv();
		publisherCsv.setPublisherName("publisher");
		
		Publisher publisher = modelMapper.map(publisherCsv, Publisher.class);
		assertNull(publisher.getId());
		assertEquals(publisherCsv.getPublisherName(), publisher.getPublisherName());
	}
}
