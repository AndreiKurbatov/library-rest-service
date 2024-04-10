package ua.com.foxmineded.libraryrestservice.services.impl;

import java.util.List;
import java.util.ArrayList;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ua.com.foxmineded.libraryrestservice.csvbeans.impl.LocationCsv;
import ua.com.foxmineded.libraryrestservice.dao.LocationRepository;
import ua.com.foxmineded.libraryrestservice.entities.impl.Location;
import ua.com.foxmineded.libraryrestservice.services.LocationImporterService;
import ua.com.foxmineded.libraryrestservice.utils.LocationCsvImporter;

@Service
@RequiredArgsConstructor
public class LocationImporterServiceImpl implements LocationImporterService {
	private final ModelMapper modelMapper;
	private final LocationRepository locationRepository;
	private final LocationCsvImporter csvReader;

	@Override
	public List<Location> importLocations() {
		List<LocationCsv> locationCsvs = csvReader.read();
		List<Location> locations = new ArrayList<>();
		for (LocationCsv locationCsv : locationCsvs) {
			String[] locationParts = locationCsv.getLocationName().split(", ");
			for (String locationPart : locationParts) {
				Location location = modelMapper.map(locationCsv, Location.class);
				location.setLocationName(locationPart);
				locations.add(location);
			}
		}
		return locationRepository.saveAll(locations);
	}

	@Override
	public Long countAll() {
		return locationRepository.count();
	}
}
