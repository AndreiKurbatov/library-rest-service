package ua.com.foxmineded.libraryrestservice.services.impl;

import java.util.Objects;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.com.foxmineded.libraryrestservice.dao.AuthorRepository;
import ua.com.foxmineded.libraryrestservice.dto.AuthorDto;
import ua.com.foxmineded.libraryrestservice.entities.impl.Author;
import ua.com.foxmineded.libraryrestservice.exceptions.ServiceException;
import ua.com.foxmineded.libraryrestservice.services.AuthorService;

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
		return authors.map(author -> modelMapper.map(author, AuthorDto.class));
	}

	@Override
	public Page<AuthorDto> findAllByPublisherName(Pageable pageable, String publisherName) {
		Page<Author> authors = authorRepository.findAllByPublisherName(pageable, publisherName);
		return authors.map(author -> modelMapper.map(author, AuthorDto.class));
	}

	@Override
	public AuthorDto save(@Valid AuthorDto authorDto) throws ServiceException {
		if (Objects.nonNull(authorDto.getId()) && authorRepository.findById(authorDto.getId()).isPresent()) {
			Author entity = authorRepository.findById(authorDto.getId()).get();
			if (!entity.getAuthorName().equals(authorDto.getAuthorName())) {
				String message = "The author name cannot be updated";
				log.error(message);
				throw new ServiceException(message);
			}
		}
		if (authorRepository.findByAuthorName(authorDto.getAuthorName()).isPresent()) {
			Author entity = authorRepository.findByAuthorName(authorDto.getAuthorName()).get();
			if (Objects.isNull(authorDto.getId()) || !Objects.equals(entity.getId(), authorDto.getId())) {
				String message = "The author with the name %s already exists".formatted(authorDto.getAuthorName());
				log.error(message);
				throw new ServiceException(message);
			}
		}
		AuthorDto result = modelMapper.map(authorRepository.save(modelMapper.map(authorDto, Author.class)),
				AuthorDto.class);
		log.info("The author with id = %d was saved".formatted(result.getId()));
		return result;
	}

	@Override
	public void deleteById(Long id) {
		authorRepository.deleteById(id);
		log.info("The author with id = %d was deleted".formatted(id));
	}
}
