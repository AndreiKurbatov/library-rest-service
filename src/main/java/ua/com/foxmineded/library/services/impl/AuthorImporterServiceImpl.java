package ua.com.foxmineded.library.services.impl;

import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toCollection;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ua.com.foxmineded.library.dao.AuthorRepository;
import ua.com.foxmineded.library.entities.impl.Author;
import ua.com.foxmineded.library.utils.AuthorCsvImporter;

@Service
@RequiredArgsConstructor
public class AuthorImporterServiceImpl implements AuthorImporterService {
	private final ModelMapper modelMapper;
	private final AuthorCsvImporter csvReader;
	private final AuthorRepository authorRepository;

	@Override
	public List<Author> importAuthors() {
		List<Author> authors = csvReader.read().stream().map(value -> modelMapper.map(value, Author.class))
				.collect(toCollection(ArrayList::new));
		return authorRepository.saveAll(authors);
	}

	@Override
	public Long countAll() {
		return authorRepository.count();
	}
}
