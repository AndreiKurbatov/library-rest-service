package ua.com.foxmineded.library.services.impl;

import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.com.foxmineded.library.dao.BookRepository;
import ua.com.foxmineded.library.dto.BookDto;
import ua.com.foxmineded.library.entities.impl.Book;
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
