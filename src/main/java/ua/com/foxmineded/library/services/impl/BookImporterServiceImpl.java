package ua.com.foxmineded.library.services.impl;

import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toCollection;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ua.com.foxmineded.library.dao.BookRepository;
import ua.com.foxmineded.library.entities.impl.Book;
import ua.com.foxmineded.library.services.BookImporterService;
import ua.com.foxmineded.library.utils.BookCsvImporter;

@Service
@RequiredArgsConstructor
public class BookImporterServiceImpl implements BookImporterService {
	private final ModelMapper modelMapper;
	private final BookCsvImporter booksCsvReader;
	private final BookRepository bookRepository;

	@Override
	public List<Book> importBooks() {
		List<Book> books = booksCsvReader.read().stream().map(value -> modelMapper.map(value, Book.class))
				.collect(toCollection(ArrayList::new));
		return bookRepository.saveAll(books);
	}

	@Override
	public Long countAll() {
		return bookRepository.count();
	}
}
