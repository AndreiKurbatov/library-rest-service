package ua.com.foxmineded.libraryrestservice.services;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxmineded.libraryrestservice.entities.impl.Location;

public interface LocationImporterService {
	@Transactional
	List<Location> importLocations();

	@Transactional(readOnly = true)
	Long countAll();
}
