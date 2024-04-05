package ua.com.foxmineded.library.services;

import java.util.Map;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;
import ua.com.foxmineded.library.entities.impl.BookReader;
import ua.com.foxmineded.library.entities.impl.Location;

public interface BookReaderImporterService {
	@Transactional
	void importBookReaders(Map<Long, BookReader> bookReaders, Map<Long, Set<Location>> locationsMap);
	
	@Transactional(readOnly = true)
	Long countAll();
}
