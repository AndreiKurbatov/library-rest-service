package ua.com.foxmineded.library.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.com.foxmineded.library.csvbeans.impl.BookRatingCsv;
import ua.com.foxmineded.library.csvbeans.impl.BookReaderCsv;
import ua.com.foxmineded.library.csvbeans.impl.LocationCsv;
import ua.com.foxmineded.library.entities.impl.BookRating;
import ua.com.foxmineded.library.entities.impl.BookReader;
import ua.com.foxmineded.library.entities.impl.Location;

@Configuration
public class TypeMapConfig {
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		PropertyMap<BookRatingCsv, BookRating> bookRatingPropertyMap = new PropertyMap<>() {
			@Override
			protected void configure() {
				skip(destination.getId());
			}
		};
		PropertyMap<BookReaderCsv, BookReader> bookReaderPropertyMap = new PropertyMap<>() {
			@Override
			protected void configure() {
				skip(destination.getId());
				skip(destination.getLocations());
			}
		};
		PropertyMap<LocationCsv, Location> locationPropertyMap = new PropertyMap<>() {
			@Override
			protected void configure() {
				skip(destination.getId());
				skip(destination.getBookReader());
			}
		};
		modelMapper.addMappings(bookRatingPropertyMap);
		modelMapper.addMappings(locationPropertyMap);
		modelMapper.addMappings(bookReaderPropertyMap);
		return modelMapper;
	}
}
