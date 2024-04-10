package ua.com.foxmineded.libraryrestservice.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ua.com.foxmineded.libraryrestservice.config.TypeMapConfig;
import ua.com.foxmineded.libraryrestservice.dto.LocationDto;
import ua.com.foxmineded.libraryrestservice.entities.impl.Location;

@SpringBootTest(classes = TypeMapConfig.class)
class LocationTest {
	@Autowired
	ModelMapper modelMapper;

	@Test
	void testMapLocationToLocationDto() {
		Location location = Instancio.create(Location.class);
		LocationDto locationDto = modelMapper.map(location, LocationDto.class);
		assertEquals(location.getId(), locationDto.getId());
		assertEquals(location.getBookReader().getBookReaderId(), locationDto.getBookReaderId());
		assertEquals(location.getLocationName(), locationDto.getLocationName());
	}

	@Test
	void testMapLocationDtoToLocation() {
		LocationDto locationDto = Instancio.create(LocationDto.class);
		Location location = modelMapper.map(locationDto, Location.class);
		assertEquals(locationDto.getId(), location.getId());
		assertNull(location.getBookReader());
		assertEquals(location.getLocationName(), locationDto.getLocationName());
	}
}
