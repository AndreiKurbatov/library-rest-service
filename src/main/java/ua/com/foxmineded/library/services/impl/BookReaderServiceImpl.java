package ua.com.foxmineded.library.services.impl;

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
import ua.com.foxmineded.library.exceptions.ServiceException;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookReaderServiceImpl implements BookReaderService{
	private final BookReaderRepository bookReaderRepository;
	private final ModelMapper modelMapper;
	
	@Override
	public Page<BookReaderDto> findAll(Pageable pageable) {
		Page<BookReader> bookReaders = bookReaderRepository.findAll(pageable);
		return bookReaders.map(bookReader -> modelMapper.map(bookReader, BookReaderDto.class));
	}

	@Override
	public Page<BookReaderDto> findAllByAge(Pageable pageable ,Integer age) {
		Page<BookReader> bookReaders = bookReaderRepository.findAllByAge(pageable, age);
		return bookReaders.map(bookReader -> modelMapper.map(bookReaders, BookReaderDto.class));
	}

	/*
	@Override
	public Page<BookReaderDto> findAllByLocationNames(Pageable pageable, String... locationNames) {
		// TODO Auto-generated method stub
		return null;
	}
	
	*/
	@Override
	public BookReaderDto findByBookReaderId(Long bookReaderId) throws ServiceException {
		return bookReaderRepository.findByBookReaderId(bookReaderId).map(v -> modelMapper.map(v, BookReaderDto.class)) 
				.orElseThrow(() -> {
					String message = "The book reader with book reader id = %s was not found".formatted(bookReaderId);
					log.error(message);
					return new ServiceException(message);
				});
	}

	@Override
	public BookReaderDto save(BookReaderDto bookReaderDto) {
		BookReaderDto result = modelMapper.map(bookReaderRepository.save(modelMapper.map(bookReaderDto, BookReader.class)), BookReaderDto.class);
		log.info("The book rating with id = %d was saved".formatted(bookReaderDto.getId()));
		return result;
	}

	@Override
	public void deleteByBookReaderId(Long bookReaderId) {
		bookReaderRepository.deleteByBookReaderId(bookReaderId);
		log.info("The book rating with id = %d was deleted".formatted(bookReaderId));
		
	}
}
