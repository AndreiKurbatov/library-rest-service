package ua.com.foxmineded.library.services.impl;

import java.util.List;
import static java.util.stream.Collectors.toCollection;
import java.util.ArrayList;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ua.com.foxmineded.library.dao.PublisherRepository;
import ua.com.foxmineded.library.entities.impl.Publisher;
import ua.com.foxmineded.library.services.PublisherImporterService;
import ua.com.foxmineded.library.utils.PublisherCsvImporter;

@Service
@RequiredArgsConstructor
public class PublisherImporterServiceImpl implements PublisherImporterService {
	private final ModelMapper modelMapper;
	private final PublisherCsvImporter csvReader;
	private final PublisherRepository publisherRepository;

	@Override
	public List<Publisher> importPublishers() {
		List<Publisher> publishers = csvReader.read().stream().map(value -> modelMapper.map(value, Publisher.class))
				.collect(toCollection(ArrayList::new));
		return publisherRepository.saveAll(publishers);
	}

	@Override
	public Long countAll() {
		return publisherRepository.count();
	}
}
