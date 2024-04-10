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
import ua.com.foxmineded.libraryrestservice.dao.BookReaderRepository;
import ua.com.foxmineded.libraryrestservice.dto.BookReaderDto;
import ua.com.foxmineded.libraryrestservice.entities.impl.BookReader;
import ua.com.foxmineded.libraryrestservice.exceptions.ServiceException;
import ua.com.foxmineded.libraryrestservice.services.BookReaderService;

@Slf4j
@RequiredArgsConstructor
@Service
@Validated
public class BookReaderServiceImpl implements BookReaderService {
	private final BookReaderRepository bookReaderRepository;
	private final ModelMapper modelMapper;

	@Override
	public Page<BookReaderDto> findAll(Pageable pageable) {
		Page<BookReader> bookReaders = bookReaderRepository.findAll(pageable);
		return bookReaders.map(bookReader -> modelMapper.map(bookReader, BookReaderDto.class));
	}

	@Override
	public Page<BookReaderDto> findAllByAge(Pageable pageable, Integer age) {
		Page<BookReader> bookReaders = bookReaderRepository.findAllByAge(pageable, age);
		return bookReaders.map(bookReader -> modelMapper.map(bookReader, BookReaderDto.class));
	}

	@Override
	public Optional<BookReaderDto> findById(Long id) {
		return bookReaderRepository.findById(id).map(v -> modelMapper.map(v, BookReaderDto.class));
	}

	@Override
	public Optional<BookReaderDto> findByBookReaderId(Long bookReaderId) {
		return bookReaderRepository.findByBookReaderId(bookReaderId).map(v -> modelMapper.map(v, BookReaderDto.class));
	}

	@Override
	public BookReaderDto save(@Valid BookReaderDto bookReaderDto) throws ServiceException {
		if (Objects.isNull(bookReaderDto.getId())
				&& bookReaderRepository.findByBookReaderId(bookReaderDto.getBookReaderId()).isPresent()) {
			String message = "The book reader with book reader id = %d already exists"
					.formatted(bookReaderDto.getBookReaderId());
			log.error(message);
			throw new ServiceException(message);
		}
		if (Objects.nonNull(bookReaderDto.getId())) {
			BookReader bookReader = bookReaderRepository.findById(bookReaderDto.getId()).get();
			if (!Objects.equals(bookReaderDto.getBookReaderId(), bookReader.getBookReaderId())) {
				String message = "The book reader id cannot be updated";
				log.error(message);
				throw new ServiceException(message);
			}
		}

		BookReaderDto result = modelMapper
				.map(bookReaderRepository.save(modelMapper.map(bookReaderDto, BookReader.class)), BookReaderDto.class);
		log.info("The book rating with id = %d was saved".formatted(result.getId()));
		return result;
	}

	@Override
	public void deleteById(Long id) {
		bookReaderRepository.deleteById(id);
		log.info("The book rating with id = %d was deleted".formatted(id));
	}
}
