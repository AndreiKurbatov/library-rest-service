package ua.com.foxmineded.library.services.impl;

import java.util.List;
import static java.util.stream.Collectors.toCollection;
import java.util.ArrayList;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ua.com.foxmineded.library.csvbeans.impl.BookReaderCsv;
import ua.com.foxmineded.library.dao.BookReaderRepository;
import ua.com.foxmineded.library.entities.impl.BookReader;
import ua.com.foxmineded.library.services.BookReaderImporterService;
import ua.com.foxmineded.library.utils.BookReaderCsvImporter;

@Service
@RequiredArgsConstructor
public class BookReaderImporterServiceImpl implements BookReaderImporterService {
	private final ModelMapper modelMapper;
	private final BookReaderCsvImporter bookReaderCsvImporter;
	private final BookReaderRepository bookReaderRepository;

	@Override
	public List<BookReader> importBookReaders() {
		List<BookReaderCsv> bookReaderCsvs = bookReaderCsvImporter.read();
		List<BookReader> bookReaders = bookReaderCsvs.stream()
				.map(value -> modelMapper.map(value, BookReader.class)).collect(toCollection(ArrayList::new));
		return bookReaderRepository.saveAll(bookReaders);
	}

	@Override
	public Long countAll() {
		return bookReaderRepository.count();
	}
}
