package ua.com.foxmineded.library.services;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxmineded.library.entities.impl.Author;

public interface AuthorImporterService {
	@Transactional
	List<Author> importAuthors();
	
	@Transactional(readOnly = true)
	Long countAll();
}
