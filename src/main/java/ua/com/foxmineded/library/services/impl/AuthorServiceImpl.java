package ua.com.foxmineded.library.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.com.foxmineded.library.dao.AuthorRepository;
import ua.com.foxmineded.library.dto.AuthorDto;
import ua.com.foxmineded.library.entities.impl.Author;
import ua.com.foxmineded.library.exceptions.ServiceException;
import ua.com.foxmineded.library.services.AuthorService;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
	private final AuthorRepository authorRepository;
	private final ModelMapper modelMapper;

	@Override
	public AuthorDto findById(Long id) throws ServiceException {
		return authorRepository.findById(id).map(value -> modelMapper.map(value, AuthorDto.class)).orElseThrow(() -> {
			String message = "The author with id = %d was not found".formatted(id);
			log.error(message);
			return new ServiceException(message);
		});
	}

	@Override
	public AuthorDto findByAuthorName(String name) throws ServiceException {
		return authorRepository.findByAuthorName(name).map(value -> modelMapper.map(value, AuthorDto.class))
				.orElseThrow(() -> {
					String message = "The author with name = %s was not found".formatted(name);
					log.error(message);
					return new ServiceException(message);
				});
	}

	@Override
	public AuthorDto findByIsbn(String isbn) throws ServiceException {
		return authorRepository.findByIsbn(isbn).map(value -> modelMapper.map(value, AuthorDto.class))
				.orElseThrow(() -> {
					String message = "The author with isbn = %s was not found".formatted(isbn);
					log.error(message);
					return new ServiceException(message);
				});
	}

	@Override
	public AuthorDto findByBookTitle(String bookTitle, Pageable pageable) throws ServiceException {
		return authorRepository.findByBookTitle(bookTitle).map(value -> modelMapper.map(value, AuthorDto.class))
				.orElseThrow(() -> {
					String message = "The book with book title = %s was not found".formatted(bookTitle);
					log.error(message);
					return new ServiceException(message);
				});
	}

	@Override
	public Page<AuthorDto> findAll(Pageable pageable) {
		Page<Author> authors = authorRepository.findAll(pageable);
		return authors.map(author -> modelMapper.map(authors, AuthorDto.class));
	}

	@Override
	public Page<AuthorDto> findAllByPublisherName(String publisherName, Pageable pageable) {
		Page<Author> authors = authorRepository.findAllByPublisherName(publisherName, pageable);
		return authors.map(author -> modelMapper.map(author, AuthorDto.class));
	}

	@Override
	public AuthorDto save(AuthorDto authorDto) {
		return modelMapper.map(modelMapper.map(authorDto, Author.class), AuthorDto.class);
	}

	@Override
	public void deleteById(Long id) {
		authorRepository.deleteById(id);
	}
}
