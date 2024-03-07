package ua.com.foxmineded.library.services;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxmineded.library.entities.impl.Book;

public interface BookGeneratorService {
	@Transactional
	List<Book> generateBooks();
	
	@Transactional(readOnly = true)
	Long countAll();
}
