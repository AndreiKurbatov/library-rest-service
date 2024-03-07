package ua.com.foxmineded.library.services.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ua.com.foxmineded.universitycms.dao.BookReaderRepository;
import ua.com.foxmineded.universitycms.entities.impl.BookReader;
import ua.com.foxmineded.universitycms.services.BookReaderGenerationService;
import ua.com.foxmineded.universitycms.utils.impl.BookReadersCsvReaderImpl;

@Service
@RequiredArgsConstructor
public class BookReaderGenerationServiceImpl implements BookReaderGenerationService {
	private final BookReadersCsvReaderImpl csvReaderImpl;
	private final BookReaderRepository bookReaderRepository;

	@Override
	public List<BookReader> generateBookReaders() {
		List<BookReader> bookReaders = csvReaderImpl.readCsv();
		return bookReaderRepository.saveAll(bookReaders);
	}

	@Override
	public Long countAll() {
		return bookReaderRepository.count();
	}
}
