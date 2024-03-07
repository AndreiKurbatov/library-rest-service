package ua.com.foxmineded.library.services;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxmineded.library.entities.impl.Location;

public interface LocationGenerationService {
	@Transactional
	List<Location> generateLocations();
	
	@Transactional(readOnly = true)
	Long countAll();
}
