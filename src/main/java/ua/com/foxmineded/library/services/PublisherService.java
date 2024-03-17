package ua.com.foxmineded.library.services;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxmineded.library.dto.PublisherDto;

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

	//@Secured({"ROLE_ADMINISTRATOR"})
	@Transactional
	PublisherDto save(PublisherDto publisherDto);

	//@Secured({"ROLE_ADMINISTRATOR"})
	@Transactional
	void deleteById(Long id);
}
