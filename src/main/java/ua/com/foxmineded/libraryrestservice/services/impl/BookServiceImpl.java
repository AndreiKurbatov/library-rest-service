package ua.com.foxmineded.libraryrestservice.services.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toCollection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.com.foxmineded.libraryrestservice.dao.AuthorRepository;
import ua.com.foxmineded.libraryrestservice.dao.BookRepository;
import ua.com.foxmineded.libraryrestservice.dao.PublisherRepository;
import ua.com.foxmineded.libraryrestservice.dto.BookDto;
import ua.com.foxmineded.libraryrestservice.entities.impl.Author;
import ua.com.foxmineded.libraryrestservice.entities.impl.Book;
import ua.com.foxmineded.libraryrestservice.entities.impl.BookRating;
import ua.com.foxmineded.libraryrestservice.entities.impl.Publisher;
import ua.com.foxmineded.libraryrestservice.exceptions.ServiceException;
import ua.com.foxmineded.libraryrestservice.services.BookService;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
	private final ModelMapper modelMapper;
	private final BookRepository bookRepository;
	private final AuthorRepository authorRepository;
	private final PublisherRepository publisherRepository;

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
			Integer ageEnd) throws ServiceException {
		if (ageEnd < ageStart) {
			String message = "The start age parameter and end age parameter are in the wrong positions";
			log.error(message);
			throw new ServiceException(message);
		}
		List<Book> booksByLocation = bookRepository
				.findAllByLocationName(PageRequest.of(0, Integer.MAX_VALUE), locationName).getContent();
		List<Book> booksByAge = bookRepository.findAllByAgeRange(PageRequest.of(0, Integer.MAX_VALUE), ageStart, ageEnd)
				.getContent();
		Set<Book> commonElements = booksByLocation.stream().filter(booksByAge::contains)
				.collect(toCollection(HashSet::new));

		Map<Book, Double> booksByRating = new HashMap<>();
		for (Book book : commonElements) {
			Long sumOfRatings = book.getBookRatings().stream().mapToLong(BookRating::getRating).sum();
			Long amountOfRatings = book.getBookRatings().stream().count();
			double averageRating = (double) sumOfRatings / amountOfRatings;
			booksByRating.put(book, averageRating);
		}

		Map<Book, Double> sortedMap = booksByRating.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(
				toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

		List<Book> result = sortedMap.keySet().stream().limit(10).collect(toCollection(ArrayList::new));

		return new PageImpl<Book>(result).map(v -> modelMapper.map(v, BookDto.class));
	}

	@Override
	public Page<BookDto> findAllByAgeRange(Pageable pageable, Integer startAge, Integer endAge)
			throws ServiceException {
		if (endAge < startAge) {
			String message = "The start age parameter and end age parameter are in the wrong positions";
			log.error(message);
			throw new ServiceException(message);
		}
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
	public BookDto save(@Valid BookDto bookDto) throws ServiceException {
		if (Objects.nonNull(bookDto.getId())) {
			Book book = bookRepository.findById(bookDto.getId()).get();
			if (!Objects.equals(bookDto.getIsbn(), book.getIsbn())) {
				String message = "The book isbn cannot be updated";
				log.error(message);
				throw new ServiceException(message);
			}
		}
		if (Objects.isNull(bookDto.getId()) && (bookRepository.findByIsbn(bookDto.getIsbn()).isPresent())) {
			String message = "The book with isbn = %s already exists".formatted(bookDto.getIsbn());
			log.error(message);
			throw new ServiceException(message);

		}
		Author author = authorRepository.findByAuthorName(bookDto.getAuthorName()).get();
		Publisher publisher = publisherRepository.findByPublisherName(bookDto.getPublisherName()).get();
		Book book = modelMapper.map(bookDto, Book.class);
		book.setAuthor(author);
		book.setPublisher(publisher);
		BookDto result = modelMapper.map(bookRepository.save(book), BookDto.class);
		log.info("The book with id = %d was saved".formatted(result.getId()));
		return result;
	}

	@Override
	public void deleteById(Long id) {
		bookRepository.deleteById(id);
		log.info("The book with id = %d was deleted".formatted(id));
	}
}
