package ua.com.foxmineded.libraryrestservice.services;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;
import ua.com.foxmineded.libraryrestservice.dto.AuthorDto;
import ua.com.foxmineded.libraryrestservice.exceptions.ServiceException;

public interface AuthorService {
	@Transactional(readOnly = true)
	Optional<AuthorDto> findById(Long id);

	@Transactional(readOnly = true)
	Optional<AuthorDto> findByAuthorName(String name);

	@Transactional(readOnly = true)
	Optional<AuthorDto> findByIsbn(String isbn);

	@Transactional(readOnly = true)
	Optional<AuthorDto> findByBookTitle(String bookTitle);

	@Transactional(readOnly = true)
	Page<AuthorDto> findAll(Pageable pageable);

	@Transactional(readOnly = true)
	Page<AuthorDto> findAllByPublisherName(Pageable pageable, String publisherName);

	@Transactional
	AuthorDto save(@Valid AuthorDto authorDto) throws ServiceException;

	@Transactional
	void deleteById(Long id);
}
