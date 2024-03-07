package ua.com.foxmineded.library.services;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxmineded.library.entities.impl.Author;

public interface AuthorGeneratorService {
	@Transactional
	List<Author> generateAuthors();
	
	@Transactional(readOnly = true)
	Long countAll();
}
