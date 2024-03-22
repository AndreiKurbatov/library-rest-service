package ua.com.foxmineded.library.services.impl;

import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.com.foxmineded.library.dao.BookReaderRepository;
import ua.com.foxmineded.library.dto.BookReaderDto;
import ua.com.foxmineded.library.entities.impl.BookReader;
import ua.com.foxmineded.library.services.BookReaderService;

@Slf4j
@RequiredArgsConstructor
@Service
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
		return bookReaders.map(bookReader -> modelMapper.map(bookReaders, BookReaderDto.class));
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
	public BookReaderDto save(BookReaderDto bookReaderDto) {
		BookReaderDto result = modelMapper
				.map(bookReaderRepository.save(modelMapper.map(bookReaderDto, BookReader.class)), BookReaderDto.class);
		log.info("The book rating with id = %d was saved".formatted(bookReaderDto.getId()));
		return result;
	}

	@Override
	public void deleteById(Long id) {
		bookReaderRepository.deleteById(id);
		log.info("The book rating with id = %d was deleted".formatted(id));
	}
}
