package ua.com.foxmineded.library.services.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static java.util.stream.Collectors.toCollection;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ua.com.foxmineded.library.csvbeans.impl.BookReaderCsv;
import ua.com.foxmineded.library.dao.BookReaderRepository;
import ua.com.foxmineded.library.entities.impl.BookReader;
import ua.com.foxmineded.library.entities.impl.Location;
import ua.com.foxmineded.library.services.BookReaderImporterService;
import ua.com.foxmineded.library.utils.BookReaderCsvImporter;

@Service
@RequiredArgsConstructor
public class BookReaderImporterServiceImpl implements BookReaderImporterService {
	private final BookReaderCsvImporter bookReaderCsvImporter;
	private final BookReaderRepository bookReaderRepository;

	@Override
	public void importBookReaders(Map<Long, BookReader> bookReaders, Map<Long, Set<Location>> locationsMap) {
		List<BookReaderCsv> bookReaderCsvs = bookReaderCsvImporter.read();
		for (BookReaderCsv bookReaderCsv : bookReaderCsvs) {
			Long bookReaderId = bookReaderCsv.getBookReaderId();
			Set<Location> locations = Arrays.stream(bookReaderCsv.getLocationName().split(", ")).map(str -> new Location(null, null, str)).collect(toCollection(HashSet::new));
			locationsMap.put(bookReaderId, locations);
			Integer age = bookReaderCsv.getAge();
			BookReader bookReader = new BookReader(null, bookReaderId, locations, null, null, age);
			bookReaders.put(bookReaderId, bookReader);
		}
		bookReaderRepository.saveAll(bookReaders.values());
		bookReaderRepository.flush();
	}

	@Override
	public Long countAll() {
		return bookReaderRepository.count();
	}
}
