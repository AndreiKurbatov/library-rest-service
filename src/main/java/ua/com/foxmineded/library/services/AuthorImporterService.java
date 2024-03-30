package ua.com.foxmineded.library.services;

import java.util.Map;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxmineded.library.entities.impl.Author;

public interface AuthorImporterService {
	@Transactional
	void importAuthors(Map<String, Author> authors);

	@Transactional(readOnly = true)
	Long countAll();
}
