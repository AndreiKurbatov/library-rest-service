package ua.com.foxmineded.library.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toCollection;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.com.foxmineded.library.dto.AuthorDto;
import ua.com.foxmineded.library.dto.BookDto;
import ua.com.foxmineded.library.dto.BookRatingDto;
import ua.com.foxmineded.library.dto.BookReaderDto;
import ua.com.foxmineded.library.dto.LocationDto;
import ua.com.foxmineded.library.dto.PublisherDto;
import ua.com.foxmineded.library.entities.impl.Author;
import ua.com.foxmineded.library.entities.impl.Book;
import ua.com.foxmineded.library.entities.impl.BookRating;
import ua.com.foxmineded.library.entities.impl.BookReader;
import ua.com.foxmineded.library.entities.impl.Location;
import ua.com.foxmineded.library.entities.impl.Publisher;

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
		
		TypeMap<Author, AuthorDto>  authorToDtoTypeMap = modelMapper.createTypeMap(Author.class, AuthorDto.class);
		Converter<List<Book>, List<Long>> booksToIds =
			    ctx -> ctx.getSource() == null ? null : ctx.getSource().stream().map(Book::getId).collect(toCollection(ArrayList::new));
		authorToDtoTypeMap.addMappings(mapper -> mapper.using(booksToIds).map(Author::getBooks, AuthorDto::setBookIds));
		
		TypeMap<Book, BookDto> bookToDtoTypeMap = modelMapper.createTypeMap(Book.class, BookDto.class);
		Converter<List<BookRating>, List<Long>> bookRatingsToIds = ctx -> ctx.getSource().stream().map(BookRating::getId).collect(toCollection(ArrayList::new));
		bookToDtoTypeMap.addMappings(mapper -> mapper.using(bookRatingsToIds).map(Book::getBookRatings, BookDto::setBookRatingIds));
		Converter<List<BookReader>, List<Long>> bookReadersToIds = ctx -> ctx.getSource().stream().map(BookReader::getId).collect(toCollection(ArrayList::new));
		bookToDtoTypeMap.addMappings(mapper -> mapper.using(bookReadersToIds).map(Book::getBookReaders, BookDto::setBookReaderIds));
		
		TypeMap<BookReader, BookReaderDto> bookReaderToDto = modelMapper.createTypeMap(BookReader.class, BookReaderDto.class);
		Converter<Set<Location>, Set<Long>> locationsToIds = ctx -> ctx.getSource().stream().map(Location::getId).collect(toCollection(HashSet::new));
		bookReaderToDto.addMappings(mapper -> mapper.using(locationsToIds).map(BookReader::getLocations, BookReaderDto::setLocationIds));
		Converter<List<BookRating>, List<Long>> bookRatingToIds = ctx -> ctx.getSource().stream().map(BookRating::getId).collect(toCollection(ArrayList::new));
		bookReaderToDto.addMappings(mapper -> mapper.using(bookRatingToIds).map(BookReader::getBookRatings, BookReaderDto::setBookRatingIds));
		Converter<List<Book>, List<Long>> booksToIds1 = ctx -> ctx.getSource().stream().map(Book::getId).collect(toCollection(ArrayList::new));
		bookReaderToDto.addMappings(mapper -> mapper.using(booksToIds1).map(BookReader::getBooks, BookReaderDto::setBookIds));
		
		TypeMap<Publisher, PublisherDto> publisherToDtoTypeMap = modelMapper.createTypeMap(Publisher.class, PublisherDto.class);
		Converter<List<Book>, List<Long>> booksToIds2 = ctx -> ctx.getSource().stream().map(Book::getId).collect(toCollection(ArrayList::new));
		publisherToDtoTypeMap.addMappings(mapper -> mapper.using(booksToIds2).map(Publisher::getBooks, PublisherDto::setBookIds));
 		
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
