package ua.com.foxmineded.libraryrestservice.services;

import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxmineded.libraryrestservice.entities.impl.Author;

public interface AuthorImporterService {
	@Transactional
	void importAuthors(Map<String, Author> authors);

	@Transactional(readOnly = true)
	Long countAll();
}
