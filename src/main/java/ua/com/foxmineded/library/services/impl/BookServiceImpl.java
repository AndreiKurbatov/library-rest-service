package ua.com.foxmineded.library.services.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toCollection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.com.foxmineded.library.dao.BookRepository;
import ua.com.foxmineded.library.dto.BookDto;
import ua.com.foxmineded.library.entities.impl.Book;
import ua.com.foxmineded.library.entities.impl.BookRating;
import ua.com.foxmineded.library.entities.impl.Location;
import ua.com.foxmineded.library.services.BookService;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
	private final BookRepository bookRepository;
	private final ModelMapper modelMapper;
	
	@Override
	public Page<BookDto> findAll(Pageable pageable) {
		Page<Book> books = bookRepository.findAll(pageable);
		return books.map(book -> modelMapper.map(book, BookDto.class));
	}

	@Override
	public Page<BookDto> findAllByAuthorName(Pageable pageable, String name) {
		Page<Book> books = bookRepository.findAllByAuthorName(pageable, name);
		return books.map(book -> modelMapper.map(book, BookDto.class));
	}

	@Override
	public Page<BookDto> findAllByPublisherName(Pageable pageable, String name) {
		Page<Book> books = bookRepository.findAllByPublisherName(pageable, name);
		return books.map(book -> modelMapper.map(book, BookDto.class));
	}
	
	@Override
	public Page<BookDto> findTop10ByLocationAndAgeRange(Pageable pageable, String locationName, Integer ageStart,
			Integer ageEnd) {
		List<Book> booksByLocation = bookRepository.findAllByLocationName(PageRequest.of(0, Integer.MAX_VALUE), locationName).getContent();
		List<Book> booksByAge = bookRepository.findAllByAgeRange(PageRequest.of(0, Integer.MAX_VALUE), ageStart, ageEnd).getContent();
		List<Book> commonElements = booksByLocation.stream().filter(booksByAge::contains).collect(toCollection(ArrayList::new));
		Map<Book, Integer> booksByRating = new HashMap<>();
		for (Book book : commonElements) {
			List<BookRating> validRatings = book.getBookRatings().stream() 
					.filter(v -> {
						Integer age = v.getBookReader().getAge();
						Set<String> locationNames = v.getBookReader().getLocations().stream().map(Location::getLocationName).collect(toCollection(HashSet::new));
						return locationNames.contains(locationName) && age >= ageStart && age <= ageEnd;
					}).collect(toCollection(ArrayList::new));
			Integer averageRating = validRatings.stream().map(BookRating::getBookRating).mapToInt(v -> v).sum()/validRatings.size();
			booksByRating.put(book, averageRating);
		}
		List<Map.Entry<Book, Integer>> entryList = new ArrayList<>(booksByRating.entrySet());
		Collections.sort(entryList, new Comparator<Map.Entry<Book, Integer>>() {
			@Override
			public int compare(Entry<Book, Integer> o1, Entry<Book, Integer> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		});
		List<Book> top10 = new ArrayList<>();
		for (Map.Entry<Book, Integer> entry : entryList) {
			top10.add(entry.getKey());
		}
		List<BookDto> top10Dtos = top10.stream().map(v -> modelMapper.map(v, BookDto.class)).collect(toCollection(ArrayList::new));
		return new PageImpl<>(top10Dtos);
	}
	
	@Override
	public Page<BookDto> findAllByAgeRange(Pageable pageable, Integer startAge, Integer endAge) {
		return bookRepository.findAllByAgeRange(pageable, startAge, endAge).map(v -> modelMapper.map(v, BookDto.class));
	}

	@Override
	public Page<BookDto> findAllByLocationName(Pageable pageable, String locationName) {
		return bookRepository.findAllByLocationName(pageable, locationName).map(v -> modelMapper.map(v, BookDto.class));
	}

	@Override
	public Optional<BookDto> findById(Long id) {
		return bookRepository.findById(id).map(value -> modelMapper.map(value, BookDto.class));
	}

	@Override
	public Optional<BookDto> findByIsbn(String isbn) {
		return bookRepository.findByIsbn(isbn).map(value -> modelMapper.map(value, BookDto.class));
	}

	@Override
	public Optional<BookDto> findByBookTitle(String bookTitle) {
		return bookRepository.findByBookTitle(bookTitle).map(value -> modelMapper.map(value, BookDto.class)); 
	}

	@Override
	public BookDto save(BookDto book) {
		BookDto result =  modelMapper.map(bookRepository.save(modelMapper.map(book, Book.class)), BookDto.class);
		log.info("The book with id = %d was saved".formatted(book.getId()));
		return result;
	}

	@Override
	public void deleteById(Long id) {
		bookRepository.deleteById(id);
		log.info("The book with id = %d was deleted".formatted(id));
	}
}
