package ua.com.foxmineded.library.services.impl;

import java.util.function.Function;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.com.foxmineded.universitycms.dao.BookRepository;
import ua.com.foxmineded.universitycms.dto.BookDto;
import ua.com.foxmineded.universitycms.entities.impl.Book;
import ua.com.foxmineded.universitycms.services.BookService;
import ua.com.foxmineded.universitycms.exceptions.ServiceException;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{
	private final BookRepository bookRepository;
	private final ModelMapper modelMapper;
	
	@Override
	public BookDto findById(Long id) throws ServiceException {
		return bookRepository.findById(id).map(value -> modelMapper.map(value, BookDto.class)) 
				.orElseThrow(() -> {
					String message = "The book with id = %d was not found".formatted(id);
					log.error(message);
					return new ServiceException(message);
				});
	}
	@Override
	public BookDto findByIsbn(String isbn) throws ServiceException {
		return bookRepository.findByIsbn(isbn).map(value -> modelMapper.map(value, BookDto.class)) 
				.orElseThrow(() -> {
					String message = "The book with isbn = %s was not found".formatted(isbn);
					log.error(message);
					return new ServiceException(message);
				});
	}
	@Override
	public BookDto findByBookTitle(String bookTitle) throws ServiceException {
		return bookRepository.findByBookTitle(bookTitle) 
				.map(value -> modelMapper.map(value, BookDto.class)) 
				.orElseThrow(() -> {
					String message = "The book with book title = %s  was not found".formatted(bookTitle);
					log.error(message);
					return new ServiceException(message);
				});
	}
	
	@Override
	public Page<BookDto> findAll(Pageable pageable) {
		Page<Book> books = bookRepository.findAll(pageable);
		return books.map(new Function<Book, BookDto>() {
			@Override
			public BookDto apply(Book t) {
				return modelMapper.map(t, BookDto.class);
			}
		});
	}
	
	@Override
	public Page<BookDto> findAllByAuthorName(String name, Pageable pageable) {
		Page<Book> books = bookRepository.findAllByAuthorName(name, pageable);
		return books.map(new Function<Book, BookDto>() {
			@Override
			public BookDto apply(Book t) {
				return modelMapper.map(t, BookDto.class);
			}
		});
	}
	
	@Override
	public Page<BookDto> findAllByPublisherName(String name, Pageable pageable) {
		Page<Book> books = bookRepository.findAllByPublisherName(name, pageable);
		return books.map(new Function<Book, BookDto>() {
			@Override
			public BookDto apply(Book t) {
				return modelMapper.map(t, BookDto.class);
			}
		});
	}
	
	@Override
	public BookDto save(BookDto book) {
		return modelMapper.map(bookRepository.save(modelMapper.map(book, Book.class)), BookDto.class);
	}
	
	@Override
	public void deleteById(Long id) {
		bookRepository.deleteById(id);
	}
}
