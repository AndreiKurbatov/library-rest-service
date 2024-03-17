package ua.com.foxmineded.library.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxmineded.library.dto.PublisherDto;
import ua.com.foxmineded.library.exceptions.ServiceException;

public interface PublisherService {

	@Transactional(readOnly = true)
	PublisherDto findByPublisherName(String name) throws ServiceException;

	@Transactional(readOnly = true)
	PublisherDto findByBookTitle(String bookTitle) throws ServiceException;

	@Transactional(readOnly = true)
	PublisherDto findByIsbn(String isbn) throws ServiceException;

	@Transactional(readOnly = true)
	Page<PublisherDto> findAll(Pageable pageable);

	@Transactional(readOnly = true)
	Page<PublisherDto> findAllByAuthorName(String name, Pageable pageable);

	//@Secured({"ROLE_ADMINISTRATOR"})
	@Transactional
	PublisherDto save(PublisherDto publisherDto);

	//@Secured({"ROLE_ADMINISTRATOR"})
	@Transactional
	void deleteById(Long id);
}
