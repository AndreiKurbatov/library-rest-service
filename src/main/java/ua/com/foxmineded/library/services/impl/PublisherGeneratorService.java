package ua.com.foxmineded.library.services.impl;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxmineded.universitycms.entities.impl.Publisher;

public interface PublisherGeneratorService {
	@Transactional
	List<Publisher> generatePublishers();
	
	@Transactional(readOnly = true)
	Long countAll();
}
