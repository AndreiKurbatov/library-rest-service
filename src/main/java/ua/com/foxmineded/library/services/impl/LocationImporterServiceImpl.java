package ua.com.foxmineded.library.services.impl;

import java.util.List;
import java.util.ArrayList;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ua.com.foxmineded.library.csvbeans.impl.LocationCsv;
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
		List<LocationCsv> locations = csvReader.read();
		List<Location> result = new ArrayList<>();
		for (LocationCsv locationCsv : locations) {
			String[] locationParts = locationCsv.getLocationName().split(", ");
			for (String locationPart : locationParts) {
				Location location = modelMapper.map(locationCsv, Location.class);
				location.setLocationName(locationPart);
				result.add(location);
			}
		}
		return locationRepository.saveAll(result);
	}

	@Override
	public Long countAll() {
		return locationRepository.count();
	}
}
