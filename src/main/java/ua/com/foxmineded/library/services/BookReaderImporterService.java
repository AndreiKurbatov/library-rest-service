package ua.com.foxmineded.library.services;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxmineded.library.entities.impl.BookReader;

public interface BookReaderImporterService {
	@Transactional
	List<BookReader> importBookReaders();
	
	@Transactional(readOnly = true)
	Long countAll();
}
