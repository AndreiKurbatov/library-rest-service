package ua.com.foxmineded.libraryrestservice.services.impl;

import java.util.Objects;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.com.foxmineded.libraryrestservice.dao.BookRatingRepository;
import ua.com.foxmineded.libraryrestservice.dao.BookReaderRepository;
import ua.com.foxmineded.libraryrestservice.dao.BookRepository;
import ua.com.foxmineded.libraryrestservice.dto.BookRatingDto;
import ua.com.foxmineded.libraryrestservice.entities.impl.Book;
import ua.com.foxmineded.libraryrestservice.entities.impl.BookRating;
import ua.com.foxmineded.libraryrestservice.entities.impl.BookReader;
import ua.com.foxmineded.libraryrestservice.exceptions.ServiceException;
import ua.com.foxmineded.libraryrestservice.services.BookRatingService;

@Slf4j
@RequiredArgsConstructor
@Service
@Validated
public class BookRatingServiceImpl implements BookRatingService {
	private final ModelMapper modelMapper;
	private final BookRatingRepository bookRatingRepository;
	private final BookReaderRepository bookReaderRepository;
	private final BookRepository bookRepository;

	@Override
	public Page<BookRatingDto> findAll(Pageable pageable) {
		return bookRatingRepository.findAll(pageable).map(entity -> modelMapper.map(entity, BookRatingDto.class));
	}

	@Override
	public Page<BookRatingDto> findAllByBookId(Pageable pageable, Long id) {
		return bookRatingRepository.findAllByBookId(pageable, id)
				.map(entity -> modelMapper.map(entity, BookRatingDto.class));
	}

	@Override
	public Optional<BookRatingDto> findById(Long id) {
		return bookRatingRepository.findById(id).map(dto -> modelMapper.map(dto, BookRatingDto.class));
	}

	@Override
	public BookRatingDto save(@Valid BookRatingDto bookRatingDto) throws ServiceException {
		if (Objects.isNull(bookRatingDto.getId()) && (bookRatingRepository
				.findByBookReaderIdAndBookId(bookRatingDto.getBookReaderId(), bookRatingDto.getBookId()).isPresent())) {
			String message = "The book reader with id = %d already made feedback for the book with id = %d"
					.formatted(bookRatingDto.getBookReaderId(), bookRatingDto.getBookId());
			log.error(message);
			throw new ServiceException(message);
		}
		BookRating bookRating = modelMapper.map(bookRatingDto, BookRating.class);
		Book book = bookRepository.findById(bookRatingDto.getBookId()).get();
		BookReader bookReader = bookReaderRepository.findById(bookRatingDto.getBookReaderId()).get();
		bookRating.setBook(book);
		bookRating.setBookReader(bookReader);
		BookRatingDto saved = modelMapper.map(bookRatingRepository.save(bookRating), BookRatingDto.class);
		log.info("The book rating with id = %d was saved".formatted(saved.getId()));
		return saved;
	}

	@Override
	public void deleteById(Long id) {
		bookRatingRepository.deleteById(id);
		log.info("The book rating with id = %d was deleted".formatted(id));
	}
}
