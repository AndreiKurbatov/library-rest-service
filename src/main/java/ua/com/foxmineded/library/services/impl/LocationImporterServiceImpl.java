package ua.com.foxmineded.library.services.impl;

import java.util.List;
import static java.util.stream.Collectors.toCollection;
import java.util.ArrayList;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ua.com.foxmineded.library.dao.LocationRepository;
import ua.com.foxmineded.library.entities.impl.Location;
import ua.com.foxmineded.library.services.LocationImporterService;
import ua.com.foxmineded.library.utils.LocationCsvImporter;

@Service
@RequiredArgsConstructor
public class LocationImporterServiceImpl implements LocationImporterService {
	private final ModelMapper modelMapper;
	private final LocationRepository locationRepository;
	private final LocationCsvImporter csvReader;

	@Override
	public List<Location> importLocations() {
		List<Location> locations = csvReader.read().stream().map(value -> modelMapper.map(value, Location.class)).collect(toCollection(ArrayList::new));
		return locationRepository.saveAll(locations);
	}

	@Override
	public Long countAll() {
		return locationRepository.count();
	}
}
