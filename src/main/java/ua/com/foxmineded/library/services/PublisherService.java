package ua.com.foxmineded.library.services;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;
import ua.com.foxmineded.library.dto.PublisherDto;
import ua.com.foxmineded.library.exceptions.ServiceException;

public interface PublisherService {
	@Transactional(readOnly = true)
	Page<PublisherDto> findAll(Pageable pageable);

	@Transactional(readOnly = true)
	Page<PublisherDto> findAllByAuthorName(Pageable pageable, String name);
	
	@Transactional(readOnly = true)
	Optional<PublisherDto> findById(Long id);
	
	@Transactional(readOnly = true)
	Optional<PublisherDto> findByPublisherName(String name);

	@Transactional(readOnly = true)
	Optional<PublisherDto> findByBookTitle(String bookTitle);

	@Transactional(readOnly = true)
	Optional<PublisherDto> findByIsbn(String isbn);

	@Transactional
	PublisherDto save(@Valid PublisherDto publisherDto) throws ServiceException;

	@Transactional
	void deleteById(Long id);
}
