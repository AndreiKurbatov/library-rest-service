package ua.com.foxmineded.library.utils;

import java.util.Set;
import java.util.Map;
import ua.com.foxmineded.library.entities.impl.BookReader;
import ua.com.foxmineded.library.entities.impl.Location;

public interface BookReaderCsvToEntityConverter {
	void convert(Map<Long, BookReader> bookReaders, Map<Long, Set<Location>> locations);
}
