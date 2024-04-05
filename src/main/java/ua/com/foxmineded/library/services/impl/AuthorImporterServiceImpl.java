package ua.com.foxmineded.library.services.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ua.com.foxmineded.library.csvbeans.impl.AuthorCsv;
import ua.com.foxmineded.library.dao.AuthorRepository;
import ua.com.foxmineded.library.entities.impl.Author;
import ua.com.foxmineded.library.services.AuthorImporterService;
import ua.com.foxmineded.library.utils.AuthorCsvImporter;

@Service
@RequiredArgsConstructor
public class AuthorImporterServiceImpl implements AuthorImporterService {
	private final AuthorCsvImporter csvReader;
	private final AuthorRepository authorRepository;

	@Override
	public void importAuthors(Map<String, Author> authors) {
		List<AuthorCsv> authorCsvs = csvReader.read();
		for (AuthorCsv authorCsv : authorCsvs) {
			String authorName = authorCsv.getAuthorName();
			Author author = new Author(null, authorName, null);
			authors.put(authorName, author);
		}
		authorRepository.saveAll(authors.values());
		authorRepository.flush();
	}

	@Override
	public Long countAll() {
		return authorRepository.count();
	}
}
