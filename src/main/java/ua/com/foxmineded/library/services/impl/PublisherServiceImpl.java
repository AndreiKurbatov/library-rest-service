package ua.com.foxmineded.library.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.com.foxmineded.library.dao.PublisherRepository;
import ua.com.foxmineded.library.dto.PublisherDto;
import ua.com.foxmineded.library.entities.impl.Publisher;
import ua.com.foxmineded.library.exceptions.ServiceException;
import ua.com.foxmineded.library.services.PublisherService;

@Service
@Slf4j
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {
	private final PublisherRepository publisherRepository;
	private final ModelMapper modelMapper;

	@Override
	public PublisherDto findByPublisherName(String name) throws ServiceException {
		return publisherRepository.findByPublisherName(name) 
				.map(value -> modelMapper.map(value, PublisherDto.class)) 
				.orElseThrow(() -> {
					String message = "The publisher with the name = s% was not found".formatted(name);
					log.error(message);
					return new ServiceException(message);
				});
	}

	@Override
	public PublisherDto findByBookTitle(String bookTitle) throws ServiceException {
		return publisherRepository.findByBookTitle(bookTitle).map(value -> modelMapper.map(value, PublisherDto.class)) 
				.orElseThrow(() -> {
					String message = "The publisher with the book title = s% was not found".formatted(bookTitle);
					log.error(message);
					return new ServiceException(message);
				});
	}

	@Override
	public PublisherDto findByIsbn(String isbn) throws ServiceException {
		return publisherRepository.findByIsbn(isbn) 
				.map(value -> modelMapper.map(isbn, PublisherDto.class)) 
				.orElseThrow(() -> {
					String message = "The publisher with the isbn = s% was not found".formatted(isbn);
					log.error(message);
					return new ServiceException(message);
				});
	}

	@Override
	public Page<PublisherDto> findAll(Pageable pageable) {
		Page<Publisher> publishers = publisherRepository.findAll(pageable);
		return publishers.map(publisher -> modelMapper.map(publisher, PublisherDto.class));
	}

	@Override
	public Page<PublisherDto> findAllByAuthorName(String name, Pageable pageable) {
		Page<Publisher> publishers = publisherRepository.findAllByAuthorName(name, pageable);
		return publishers.map(publisher -> modelMapper.map(publisher, PublisherDto.class));
	}

	@Override
	public PublisherDto save(PublisherDto publisherDto) {
		return modelMapper.map(modelMapper.map(publisherDto, Publisher.class),PublisherDto.class);
	}

	@Override
	public void deleteById(Long id) {
		publisherRepository.deleteById(id);
	}
}
