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
import ua.com.foxmineded.libraryrestservice.dao.PublisherRepository;
import ua.com.foxmineded.libraryrestservice.dto.PublisherDto;
import ua.com.foxmineded.libraryrestservice.entities.impl.Publisher;
import ua.com.foxmineded.libraryrestservice.exceptions.ServiceException;
import ua.com.foxmineded.libraryrestservice.services.PublisherService;

@Service
@Slf4j
@Validated
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {
	private final PublisherRepository publisherRepository;
	private final ModelMapper modelMapper;

	@Override
	public Page<PublisherDto> findAll(Pageable pageable) {
		Page<Publisher> publishers = publisherRepository.findAll(pageable);
		return publishers.map(publisher -> modelMapper.map(publisher, PublisherDto.class));
	}

	@Override
	public Page<PublisherDto> findAllByAuthorName(Pageable pageable, String name) {
		Page<Publisher> publishers = publisherRepository.findAllByAuthorName(pageable, name);
		return publishers.map(publisher -> modelMapper.map(publisher, PublisherDto.class));
	}

	@Override
	public Optional<PublisherDto> findById(Long id) {
		return publisherRepository.findById(id).map(value -> modelMapper.map(value, PublisherDto.class));
	}

	@Override
	public Optional<PublisherDto> findByPublisherName(String name) {
		return publisherRepository.findByPublisherName(name).map(value -> modelMapper.map(value, PublisherDto.class));
	}

	@Override
	public Page<PublisherDto> findAllByBookTitle(Pageable pageable,String bookTitle) {
		return publisherRepository.findAllByBookTitle(pageable,bookTitle).map(value -> modelMapper.map(value, PublisherDto.class));
	}

	@Override
	public Optional<PublisherDto> findByIsbn(String isbn) {
		return publisherRepository.findByIsbn(isbn).map(value -> modelMapper.map(value, PublisherDto.class));
	}

	@Override
	public PublisherDto save(@Valid PublisherDto publisherDto) throws ServiceException {
		if (Objects.isNull(publisherDto.getId())) {
			if (publisherRepository.findByPublisherName(publisherDto.getPublisherName()).isPresent()) {
				String message = "The publisher with the name %s already exists"
						.formatted(publisherDto.getPublisherName());
				log.error(message);
				throw new ServiceException(message);
			}
		} else {
			Publisher publisher = publisherRepository.findById(publisherDto.getId()).get();
			if (!Objects.equals(publisherDto.getPublisherName(), publisher.getPublisherName())) {
				String message = "The publisher name cannot be updated. Sorry:(";
				log.error(message);
				throw new ServiceException(message);
			}
		}
		PublisherDto result = modelMapper.map(publisherRepository.save(modelMapper.map(publisherDto, Publisher.class)),
				PublisherDto.class);
		log.info("Publisher with id = %d was saved".formatted(result.getId()));
		return result;

	}

	@Override
	public void deleteById(Long id) {
		publisherRepository.deleteById(id);
		log.info("Publisher with id = %d was deleted".formatted(id));
	}
}
