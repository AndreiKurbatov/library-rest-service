package ua.com.foxmineded.library.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import ua.com.foxmineded.library.dto.BookReaderDto;
import ua.com.foxmineded.library.models.CustomPageImpl;
import ua.com.foxmineded.library.services.BookReaderService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class BookReaderControllerTest {
	final static String BASE_URL = "http://localhost:";
	@LocalServerPort
	int PORT;
	@Autowired
	TestRestTemplate restTemplate;
	@MockBean
	BookReaderService bookReaderService;
	
	@Test
	void testFindAll_AskFindAllEntities_AllEntitiesShouldBeFound200 () {
		List<BookReaderDto> bookReaderDtos = Instancio.ofList(BookReaderDto.class).size(10).create();
		Page<BookReaderDto> bookReaderPage = new PageImpl<>(bookReaderDtos);
		
		when(bookReaderService.findAll(any(Pageable.class))).thenReturn(bookReaderPage);
		
		ResponseEntity<CustomPageImpl<BookReaderDto>> responseEntity = restTemplate.exchange(BASE_URL + PORT + "/api/v1/book-readers/search/all", HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<BookReaderDto>>() {});
		Page<BookReaderDto> bookReadersPageResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(10, bookReadersPageResponse.getContent().size());
		assertIterableEquals(bookReaderPage, bookReadersPageResponse);
	}
	
	@Test
	void testFindAllByAge_AskFindAllEntitiesByAge_AllEntitiesShouldBeFound200() {
		List<BookReaderDto> bookReaderDtos = Instancio.ofList(BookReaderDto.class).size(10).create();
		Page<BookReaderDto> bookReaderPage = new PageImpl<>(bookReaderDtos);
		
		when(bookReaderService.findAllByAge(any(Pageable.class), anyInt())).thenReturn(bookReaderPage);
		
		ResponseEntity<CustomPageImpl<BookReaderDto>> responseEntity = restTemplate.exchange(BASE_URL + PORT + "/api/v1/book-readers/search/age/12", HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<BookReaderDto>>() {});
		Page<BookReaderDto> bookReadersPageResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(10, bookReadersPageResponse.getContent().size());
		assertIterableEquals(bookReaderPage, bookReadersPageResponse);
	}
	
	@Test
	void testFindByBookReaderId_AskFindEntityByBookReaderId_EntityShouldBeReturned200() {
		BookReaderDto bookReaderDto = Instancio.create(BookReaderDto.class);
		
		when(bookReaderService.findByBookReaderId(anyLong())).thenReturn(Optional.of(bookReaderDto));
		
		ResponseEntity<BookReaderDto> responseEntity = restTemplate.getForEntity(BASE_URL + PORT + "/api/v1/book-readers/search/book-reader-id/101", BookReaderDto.class);
		BookReaderDto bookReaderDtoResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(bookReaderDto, bookReaderDtoResponse);
	}
	
	@Test
	void testFindByBookReaderId_AskFindEntityByBookReaderIdIfEntityAbsent_EntityShouldBeReturned404() {
		String message = "The book reader with book reader id = %d was not found".formatted(101);
		
		when(bookReaderService.findByBookReaderId(anyLong())).thenReturn(Optional.empty());
		
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(BASE_URL + PORT + "/api/v1/book-readers/search/book-reader-id/101", String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(message, responseEntity.getBody());
	}
	
	@Test
	void testCreate_AskPostEntity_EntityShouldBeCreatedAndReturned201() {
		BookReaderDto bookReaderDto = Instancio.create(BookReaderDto.class);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<BookReaderDto> request = new HttpEntity<BookReaderDto>(bookReaderDto, headers);
		
		when(bookReaderService.save(any(BookReaderDto.class))).thenReturn(bookReaderDto);
		
		ResponseEntity<BookReaderDto> responseEntity = restTemplate.postForEntity(BASE_URL + PORT + "/api/v1/book-readers/creation", request, BookReaderDto.class );
		verify(bookReaderService).save(any(BookReaderDto.class));
		BookReaderDto bookReaderDtoResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(bookReaderDto, bookReaderDtoResponse);
	}
	
	@Test
	void testUpdate_AskPutEntity_UpdatedEntityShouldBeReturned200() {
		BookReaderDto bookReaderDto = Instancio.create(BookReaderDto.class);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<BookReaderDto> request = new HttpEntity<BookReaderDto>(bookReaderDto, httpHeaders);
		
		when(bookReaderService.save(any(BookReaderDto.class))).thenReturn(bookReaderDto);
		
		ResponseEntity<BookReaderDto> responseEntity = restTemplate.exchange(BASE_URL + PORT + "/api/v1/book-readers/update", HttpMethod.PUT, request, BookReaderDto.class);
		BookReaderDto bookReaderDtoResponse = responseEntity.getBody();
		
		verify(bookReaderService).save(any(BookReaderDto.class));
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(bookReaderDto, bookReaderDtoResponse);
	}
	
	@Test
	void testDeleteById_AskDeleteEntityById_EntityShouldBeDeleted204() {
		BookReaderDto bookReaderDto = Instancio.create(BookReaderDto.class);
		
		when(bookReaderService.findById(anyLong())).thenReturn(Optional.of(bookReaderDto));
		
		ResponseEntity<Object> responseEntity = restTemplate.exchange(BASE_URL + PORT + "/api/v1/book-readers/deletion/101", HttpMethod.DELETE, null, Object.class);
		
		verify(bookReaderService).findById(anyLong());
		verify(bookReaderService).deleteById(anyLong());
		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
		assertNull(responseEntity.getBody());
	}
	
	@Test
	void testDeleteById_AskDeleteEntityByIdIfEntityDoesNotExists_ExceptionShouldBeThrown404() {	
		String expectedExceptionMessage = "The book with id = %d was not found".formatted(101);
		when(bookReaderService.findById(anyLong())).thenReturn(Optional.empty());
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(BASE_URL + PORT + "/api/v1/book-readers/deletion/101", HttpMethod.DELETE, null, String.class);
		
		verify(bookReaderService).findById(anyLong());
		verifyNoMoreInteractions(bookReaderService);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(expectedExceptionMessage ,responseEntity.getBody());
	}
}
