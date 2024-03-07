package ua.com.foxmineded.library.services;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxmineded.library.entities.impl.Publisher;

public interface PublisherGeneratorService {
	@Transactional
	List<Publisher> generatePublishers();
	
	@Transactional(readOnly = true)
	Long countAll();
}
