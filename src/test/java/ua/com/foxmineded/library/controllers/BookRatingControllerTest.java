package ua.com.foxmineded.library.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
import ua.com.foxmineded.library.dto.BookRatingDto;
import ua.com.foxmineded.library.exceptions.ServiceException;
import ua.com.foxmineded.library.models.CustomPageImpl;
import ua.com.foxmineded.library.services.BookRatingService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class BookRatingControllerTest {
	final static String BASE_URL = "http://localhost:";
	@LocalServerPort
	int PORT;
	@Autowired
	TestRestTemplate restTemplate;
	@MockBean
	BookRatingService bookRatingService;

	@Test
	void testFindAll_AskFindAllEntities_AllEntitiesShouldBeFound200 () {
		List<BookRatingDto> bookRatingDtos = Instancio.ofList(BookRatingDto.class).size(10).create();
		Page<BookRatingDto> bookRatingDtosPage = new PageImpl<>(bookRatingDtos);
		
		when(bookRatingService.findAll(any(Pageable.class))).thenReturn(bookRatingDtosPage);
		
		ResponseEntity<CustomPageImpl<BookRatingDto>> responseEntity = restTemplate.exchange(BASE_URL + PORT + "/api/v1/book-ratings/search/all", HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<BookRatingDto>>() {});
		Page<BookRatingDto> bookRatingDtosPageResponse = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(10, bookRatingDtosPageResponse.getContent().size());
		assertIterableEquals(bookRatingDtosPage, bookRatingDtosPageResponse);
	}
	
	@Test
	void testFindAllByBookId_AskFindAllBookRatingsByBookId_AllRatingsShouldBeFound200() {
		List<BookRatingDto> bookRatingDtos = Instancio.ofList(BookRatingDto.class).size(10).create();
		Page<BookRatingDto> bookRatingDtosPage = new PageImpl<>(bookRatingDtos);
		
		when(bookRatingService.findAllByBookId(any(Pageable.class), anyLong())).thenReturn(bookRatingDtosPage);
	
		ResponseEntity<CustomPageImpl<BookRatingDto>> responseEntity = restTemplate.exchange(BASE_URL + PORT + "/api/v1/book-ratings/search/book-id/11", HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<BookRatingDto>>() {});
		Page<BookRatingDto> bookRatingDtosPageResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(10, bookRatingDtosPageResponse.getContent().size());
		assertIterableEquals(bookRatingDtosPage, bookRatingDtosPageResponse);
	}
	
	@Test
	void testCreate_AskPostEntity_EntityShouldBeCreatedAndReturned201() throws ServiceException {
		BookRatingDto bookRatingDto = Instancio.create(BookRatingDto.class);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<BookRatingDto> request = new HttpEntity<BookRatingDto>(bookRatingDto, headers);
		
		when(bookRatingService.save(any(BookRatingDto.class))).thenReturn(bookRatingDto);
		
		ResponseEntity<BookRatingDto> responseEntity = restTemplate.postForEntity(BASE_URL + PORT + "/api/v1/book-ratings/creation", request, BookRatingDto.class );
		verify(bookRatingService).save(any(BookRatingDto.class));
		BookRatingDto bookRatingDtoResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(bookRatingDto, bookRatingDtoResponse);
	}
	
	@Test
	void testCreate_AskPostEntityIfEntityAlreadyExists_ExceptionShouldBeThrown() throws ServiceException {
		BookRatingDto bookRatingDto = Instancio.create(BookRatingDto.class);
		String message = "The book reader with id = %d already made feedback for the book with id = %d".formatted(1L, 10);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<BookRatingDto> request = new HttpEntity<BookRatingDto>(bookRatingDto, headers);
		
		when(bookRatingService.save(any(BookRatingDto.class))).thenThrow(new ServiceException(message));
		
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(BASE_URL + PORT + "/api/v1/book-ratings/creation", request, String.class );
		verify(bookRatingService).save(any(BookRatingDto.class));
		
		assertEquals(HttpStatus.I_AM_A_TEAPOT, responseEntity.getStatusCode());
		assertEquals(message, responseEntity.getBody());
	}
	
	@Test
	void testUpdate_AskPutEntity_UpdatedEntityShouldBeReturned200() throws ServiceException {
		BookRatingDto bookRatingDto = Instancio.create(BookRatingDto.class);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<BookRatingDto> request = new HttpEntity<BookRatingDto>(bookRatingDto, headers);
		
		when(bookRatingService.save(any(BookRatingDto.class))).thenReturn(bookRatingDto);

		ResponseEntity<BookRatingDto> responseEntity = restTemplate.exchange(BASE_URL + PORT + "/api/v1/book-ratings/update", HttpMethod.PUT ,  request, BookRatingDto.class );
		verify(bookRatingService).save(any(BookRatingDto.class));
		BookRatingDto bookRatingDtoResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(bookRatingDto, bookRatingDtoResponse);
	}
	
	@Test
	void testDeleteById_AskDeleteEntityById_EntityShouldBeDeleted204() {
		BookRatingDto bookRatingDto = Instancio.create(BookRatingDto.class);
		
		when(bookRatingService.findById(anyLong())).thenReturn(Optional.of(bookRatingDto));
		
		ResponseEntity<Object> responseEntity = restTemplate.exchange(BASE_URL + PORT + "/api/v1/book-ratings/deletion/101", HttpMethod.DELETE, null, Object.class);
		
		verify(bookRatingService).findById(anyLong());
		verify(bookRatingService).deleteById(anyLong());
		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
		assertNull(responseEntity.getBody());
	}
	
	@Test
	void testDeleteById_AskDeleteEntityByIdIfEntityDoesNotExists_ExceptionShouldBeThrown404() {	
		String expectedExceptionMessage =  "The book rating with id = %d was not found".formatted(101);
		when(bookRatingService.findById(anyLong())).thenReturn(Optional.empty());
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(BASE_URL + PORT + "/api/v1/book-ratings/deletion/101", HttpMethod.DELETE, null, String.class);
		
		verify(bookRatingService).findById(anyLong());
		verifyNoMoreInteractions(bookRatingService);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(expectedExceptionMessage ,responseEntity.getBody());
	}
}
