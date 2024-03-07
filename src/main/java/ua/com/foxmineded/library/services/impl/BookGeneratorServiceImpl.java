package ua.com.foxmineded.library.services.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ua.com.foxmineded.universitycms.dao.BookRepository;
import ua.com.foxmineded.universitycms.entities.impl.Book;
import ua.com.foxmineded.universitycms.services.BookGeneratorService;
import ua.com.foxmineded.universitycms.utils.impl.BooksCsvReaderImpl;

@Service
@RequiredArgsConstructor
public class BookGeneratorServiceImpl implements BookGeneratorService {
	private final BooksCsvReaderImpl booksCsvReaderImpl;
	private final BookRepository bookRepository;

	@Override
	public List<Book> generateBooks() {
		List<Book> books = booksCsvReaderImpl.readCsv();
		return bookRepository.saveAll(books);
	}

	@Override
	public Long countAll() {
		return bookRepository.count();
	}	
}
