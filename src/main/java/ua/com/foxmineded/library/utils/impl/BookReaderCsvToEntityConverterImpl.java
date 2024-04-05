package ua.com.foxmineded.library.utils.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toCollection;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import ua.com.foxmineded.library.csvbeans.impl.BookReaderCsv;
import ua.com.foxmineded.library.entities.impl.BookReader;
import ua.com.foxmineded.library.entities.impl.Location;
import ua.com.foxmineded.library.utils.BookReaderCsvImporter;
import ua.com.foxmineded.library.utils.BookReaderCsvToEntityConverter;

@Component
@RequiredArgsConstructor
public class BookReaderCsvToEntityConverterImpl implements BookReaderCsvToEntityConverter {
	private final BookReaderCsvImporter bookReaderCsvImporter;
	
	@Override
	public void convert(Map<Long, BookReader> bookReaders, Map<Long, Set<Location>> locations) {
		List<BookReaderCsv> readers = bookReaderCsvImporter.read();
		for (BookReaderCsv reader : readers) {
			Long bookReaderId = reader.getBookReaderId();
			Integer age = reader.getAge();
			Set<Location> locationEntities  = Stream.of(reader.getLocationName().split(", ")).map(str -> new Location(null, null, str)).collect(toCollection(HashSet::new));
		
			BookReader bookReader = new BookReader(null, bookReaderId, locationEntities, null,  age);
			
			locations.put(bookReaderId, locationEntities);
			bookReaders.put(bookReaderId, bookReader);
		}
	}
}
