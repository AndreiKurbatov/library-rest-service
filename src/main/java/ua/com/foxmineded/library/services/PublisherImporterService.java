package ua.com.foxmineded.library.services;

import java.util.Map;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxmineded.library.entities.impl.Publisher;

public interface PublisherImporterService {
	@Transactional
	void importPublishers(Map<String, Publisher> publishers);
	
	@Transactional(readOnly = true)
	Long countAll();
}
