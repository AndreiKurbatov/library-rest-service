package ua.com.foxmineded.library.services.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ua.com.foxmineded.library.csvbeans.impl.PublisherCsv;
import ua.com.foxmineded.library.dao.PublisherRepository;
import ua.com.foxmineded.library.entities.impl.Publisher;
import ua.com.foxmineded.library.services.PublisherImporterService;
import ua.com.foxmineded.library.utils.PublisherCsvImporter;

@Service
@RequiredArgsConstructor
public class PublisherImporterServiceImpl implements PublisherImporterService {
	private final PublisherCsvImporter csvReader;
	private final PublisherRepository publisherRepository;

	@Override
	public void importPublishers(Map<String, Publisher> publishers) {
		List<PublisherCsv> publisherCsvs = csvReader.read();
		for (PublisherCsv publisherCsv : publisherCsvs) {
			String publisherName = publisherCsv.getPublisherName();
			Publisher publisher = new Publisher(null, publisherName, null);
			publishers.put(publisherName, publisher);
		}
		publisherRepository.saveAll(publishers.values());
		publisherRepository.flush();
	}

	@Override
	public Long countAll() {
		return publisherRepository.count();
	}
}
