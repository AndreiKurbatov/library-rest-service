package ua.com.foxmineded.library.services.impl;

import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.com.foxmineded.library.dao.BookRatingRepository;
import ua.com.foxmineded.library.dto.BookRatingDto;
import ua.com.foxmineded.library.entities.impl.BookRating;
import ua.com.foxmineded.library.exceptions.ServiceException;
import ua.com.foxmineded.library.services.BookRatingService;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookRatingServiceImpl implements BookRatingService {
	@Autowired
	private final ModelMapper modelMapper;
	@Autowired
	private final BookRatingRepository bookRatingRepository;

	@Override
	public Page<BookRatingDto> findAll(Pageable pageable) {
		return bookRatingRepository.findAll(pageable).map(entity -> modelMapper.map(entity, BookRatingDto.class));
	}
	
	@Override
	public Page<BookRatingDto> findAllByBookId(Pageable pageable, Long id) {
		return bookRatingRepository.findAllByBookId(pageable, id).map(entity -> modelMapper.map(entity, BookRatingDto.class));
	}

	@Override
	public Optional<BookRatingDto> findById(Long id) {
		return bookRatingRepository.findById(id).map(dto -> modelMapper.map(dto, BookRatingDto.class));
	}
	
	@Override
	public BookRatingDto save(BookRatingDto bookRatingDto) throws ServiceException {
		if (bookRatingRepository.findByBookReaderIdAndBookId(bookRatingDto.getBookReaderId(), bookRatingDto.getBookId()).isPresent()) {
			String message = "The book reader with id = %d already made feedback for the book with id = %d".formatted(bookRatingDto.getBookReaderId(), bookRatingDto.getBookId());
			log.error(message);
			throw new ServiceException(message);
		}
		BookRatingDto saved = modelMapper.map(bookRatingRepository.save(modelMapper.map(bookRatingDto, BookRating.class)), BookRatingDto.class);
		log.info("The book rating with id = %d was saved".formatted(saved.getId()));
		return saved;
	}

	@Override
	public void deleteById(Long id) {
		bookRatingRepository.deleteById(id);
		log.info("The book rating with id = %d was deleted".formatted(id));
	}
}
