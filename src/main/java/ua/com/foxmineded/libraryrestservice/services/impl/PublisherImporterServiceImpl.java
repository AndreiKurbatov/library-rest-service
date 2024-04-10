package ua.com.foxmineded.libraryrestservice.services.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ua.com.foxmineded.libraryrestservice.csvbeans.impl.PublisherCsv;
import ua.com.foxmineded.libraryrestservice.dao.PublisherRepository;
import ua.com.foxmineded.libraryrestservice.entities.impl.Publisher;
import ua.com.foxmineded.libraryrestservice.services.PublisherImporterService;
import ua.com.foxmineded.libraryrestservice.utils.PublisherCsvImporter;

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
