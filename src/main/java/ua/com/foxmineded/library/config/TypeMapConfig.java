package ua.com.foxmineded.library.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.com.foxmineded.library.dto.AuthorDto;
import ua.com.foxmineded.library.dto.BookDto;
import ua.com.foxmineded.library.dto.BookRatingDto;
import ua.com.foxmineded.library.dto.BookReaderDto;
import ua.com.foxmineded.library.dto.LocationDto;
import ua.com.foxmineded.library.entities.impl.Author;
import ua.com.foxmineded.library.entities.impl.Book;
import ua.com.foxmineded.library.entities.impl.BookRating;
import ua.com.foxmineded.library.entities.impl.BookReader;
import ua.com.foxmineded.library.entities.impl.Location;

@Configuration
public class TypeMapConfig {
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		PropertyMap<Location, LocationDto> locationToDtoPropertyMap = new PropertyMap<>() {
			@Override
			protected void configure() {
				map().setId(source.getId());
				map().setBookReaderId(source.getBookReader().getBookReaderId());
			}
		};
		
		PropertyMap<LocationDto, Location> locationToEntityPropertyMap = new PropertyMap<>() {
			@Override
			protected void configure() {
				skip(destination.getBookReader());
				map().setId(source.getId());
			}
		};
		
		PropertyMap<BookDto, Book> bookToEntityPropertyMap = new PropertyMap<>() {
			@Override
			protected void configure() {
				skip(destination.getAuthor());
				skip(destination.getPublisher());
				map().setId(source.getId());
			}
		};
		
		PropertyMap<BookReaderDto, BookReader> bookReaderToEntityPropertyMap = new PropertyMap<>() {
			@Override
			protected void configure() {
				map().setId(source.getId());
				map().setBookReaderId(source.getBookReaderId());
				skip(destination.getLocations());
				skip(destination.getBookRatings());
				skip(destination.getBooks());
			}
		};
		
		PropertyMap<BookReader, BookReaderDto> bookReaderToDtoPropertyMap = new PropertyMap<>() {
			@Override
			protected void configure() {
				map().setId(source.getId());
				map().setBookReaderId(source.getBookReaderId());
			}
		};
		
		PropertyMap<BookRatingDto, BookRating> bookRatingToEntityPropertyMap = new PropertyMap<>() {
			@Override
			protected void configure() {
				skip(destination.getBookReader());
				skip(destination.getBook());
			}
		};
		
		PropertyMap<AuthorDto, Author> authorToEntityPropertyMap = new PropertyMap<>() {
			@Override
			protected void configure() {
				skip(destination.getBooks());
			}
		};
		
		
/*
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
		*/
		modelMapper.addMappings(locationToDtoPropertyMap);
		modelMapper.addMappings(locationToEntityPropertyMap);
		modelMapper.addMappings(bookToEntityPropertyMap);
		modelMapper.addMappings(bookReaderToEntityPropertyMap);
		modelMapper.addMappings(bookReaderToDtoPropertyMap);
		modelMapper.addMappings(bookRatingToEntityPropertyMap);
		modelMapper.addMappings(authorToEntityPropertyMap);
		return modelMapper;
	}
}
