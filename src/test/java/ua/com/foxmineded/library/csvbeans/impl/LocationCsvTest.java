package ua.com.foxmineded.library.csvbeans.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.com.foxmineded.library.config.TypeMapConfig;
import ua.com.foxmineded.library.entities.impl.Location;

@SpringBootTest(classes = {Location.class, LocationCsv.class, TypeMapConfig.class})
public class LocationCsvTest {
	@Autowired
	ModelMapper modelMapper;
	
	void testMapLocationCsvToLocation() {
		LocationCsv locationCsv = new LocationCsv();
		locationCsv.setBookReaderId(1L);
		locationCsv.setLocationName("location");
		
		Location location = modelMapper.map(locationCsv, Location.class);
		assertNull(location.getId());
		assertEquals(locationCsv.getBookReaderId(), location.getBookReader().getBookReaderId());
		assertEquals(locationCsv.getLocationName(), location.getLocationName());
	}
}
