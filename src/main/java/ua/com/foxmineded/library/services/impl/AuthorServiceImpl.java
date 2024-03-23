package ua.com.foxmineded.library.services.impl;

import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.com.foxmineded.library.dao.AuthorRepository;
import ua.com.foxmineded.library.dto.AuthorDto;
import ua.com.foxmineded.library.entities.impl.Author;
import ua.com.foxmineded.library.exceptions.ServiceException;
import ua.com.foxmineded.library.services.AuthorService;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
	private final AuthorRepository authorRepository;
	private final ModelMapper modelMapper;

	@Override
	public Optional<AuthorDto> findById(Long id) {
		return authorRepository.findById(id).map(value -> modelMapper.map(value, AuthorDto.class));
	}

	@Override
	public Optional<AuthorDto> findByAuthorName(String name) {
		return authorRepository.findByAuthorName(name).map(value -> modelMapper.map(value, AuthorDto.class));
	}

	@Override
	public Optional<AuthorDto> findByIsbn(String isbn) {
		return authorRepository.findByIsbn(isbn).map(value -> modelMapper.map(value, AuthorDto.class));
	}

	@Override
	public Optional<AuthorDto> findByBookTitle(String bookTitle) {
		return authorRepository.findByBookTitle(bookTitle).map(value -> modelMapper.map(value, AuthorDto.class));
	}

	@Override
	public Page<AuthorDto> findAll(Pageable pageable) {
		Page<Author> authors = authorRepository.findAll(pageable);
		return authors.map(author -> modelMapper.map(authors, AuthorDto.class));
	}

	@Override
	public Page<AuthorDto> findAllByPublisherName(String publisherName, Pageable pageable) {
		Page<Author> authors = authorRepository.findAllByPublisherName(pageable, publisherName);
		return authors.map(author -> modelMapper.map(author, AuthorDto.class));
	}

	@Override
	public AuthorDto save(@Valid AuthorDto authorDto) throws ServiceException {
		if (authorRepository.findByAuthorName(authorDto.getAuthorName()).isPresent()) {
			String message = "The author with the name %s already exists".formatted(authorDto.getAuthorName());
			log.error(message);
			throw new ServiceException(message);
		}
		AuthorDto result = modelMapper.map(authorRepository.save(modelMapper.map(authorDto, Author.class)), AuthorDto.class);
		log.info("The author with id = %d was saved".formatted(authorDto.getId()));
		return result;
	}

	@Override
	public void deleteById(Long id) {
		authorRepository.deleteById(id);
		log.info("The author with id = %d was deleted".formatted(id));
	}
}
