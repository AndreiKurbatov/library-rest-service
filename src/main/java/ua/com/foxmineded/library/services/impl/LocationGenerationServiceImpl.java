package ua.com.foxmineded.library.services.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ua.com.foxmineded.universitycms.dao.LocationRepository;
import ua.com.foxmineded.universitycms.entities.impl.Location;
import ua.com.foxmineded.universitycms.services.LocationGenerationService;
import ua.com.foxmineded.universitycms.utils.impl.LocationsCsvReaderImpl;

@Service
@RequiredArgsConstructor
public class LocationGenerationServiceImpl implements LocationGenerationService {
	private final LocationRepository locationRepository;
	private final LocationsCsvReaderImpl csvReader;

	@Override
	public List<Location> generateLocations() {
		List<Location> locations = csvReader.readCsv();
		return locationRepository.saveAll(locations);
	}

	@Override
	public Long countAll() {
		return locationRepository.count();
	}
}
