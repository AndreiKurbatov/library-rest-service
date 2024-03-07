package ua.com.foxmineded.library.services;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxmineded.library.entities.impl.BookReader;

public interface BookReaderGenerationService {
	@Transactional
	List<BookReader> generateBookReaders();
	
	@Transactional(readOnly = true)
	Long countAll();
}
