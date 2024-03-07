package ua.com.foxmineded.library.services.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ua.com.foxmineded.library.services.AuthorGeneratorService;

@Service
@RequiredArgsConstructor
public class AuthorGeneratorServiceImpl implements AuthorGeneratorService {
	private final AuthorsCsvReaderImpl csvReaderImpl;
	private final AuthorRepository authorRepository;

	@Override
	public List<Author> generateAuthors() {
		List<Author> authors = csvReaderImpl.readCsv();
		return authorRepository.saveAll(authors);
	}

	@Override
	public Long countAll() {
		return authorRepository.count();
	}
}
