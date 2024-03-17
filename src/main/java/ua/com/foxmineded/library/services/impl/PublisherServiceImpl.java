package ua.com.foxmineded.library.services.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.com.foxmineded.library.dao.PublisherRepository;
import ua.com.foxmineded.library.dto.PublisherDto;
import ua.com.foxmineded.library.entities.impl.Publisher;
import ua.com.foxmineded.library.services.PublisherService;

@Service
@Slf4j
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
		return publisherRepository.findByPublisherName(name) 
				.map(value -> modelMapper.map(value, PublisherDto.class));
	}

	@Override
	public Optional<PublisherDto> findByBookTitle(String bookTitle)  {
		return publisherRepository.findByBookTitle(bookTitle).map(value -> modelMapper.map(value, PublisherDto.class));
	}

	@Override
	public Optional<PublisherDto> findByIsbn(String isbn){
		return publisherRepository.findByIsbn(isbn) 
				.map(value -> modelMapper.map(isbn, PublisherDto.class));
	}

	@Override
	public PublisherDto save(PublisherDto publisherDto) {
		PublisherDto result =  modelMapper.map(modelMapper.map(publisherDto, Publisher.class),PublisherDto.class);
		log.info("Publisher with id = %d was saved".formatted(publisherDto.getId()));
		return result;
	}

	@Override
	public void deleteById(Long id) {
		publisherRepository.deleteById(id);
		log.info("Publisher with id = %d was deleted".formatted(id));
	}
}
