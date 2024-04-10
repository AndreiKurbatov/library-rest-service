package ua.com.foxmineded.libraryrestservice.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.util.UriComponentsBuilder;

import ua.com.foxmineded.libraryrestservice.dto.AuthorDto;
import ua.com.foxmineded.libraryrestservice.dto.BookDto;
import ua.com.foxmineded.libraryrestservice.dto.PublisherDto;
import ua.com.foxmineded.libraryrestservice.exceptions.ServiceException;
import ua.com.foxmineded.libraryrestservice.models.CustomPageImpl;
import ua.com.foxmineded.libraryrestservice.services.AuthorService;
import ua.com.foxmineded.libraryrestservice.services.BookService;
import ua.com.foxmineded.libraryrestservice.services.PublisherService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class BookControllerTest {
	final static String BASE_URL = "http://localhost:";
	@Value("${auth0.token}")
	String token;
	@LocalServerPort
	int PORT;
	@Autowired
	TestRestTemplate restTemplate;
	@MockBean
	BookService bookService;
	@MockBean
	AuthorService authorService;
	@MockBean
	PublisherService publisherService;
	
	@Test
	void testFindAll_AskFindAllEntities_AllEntitiesShouldBeFound200 () {
		List<BookDto> books = Instancio.ofList(BookDto.class).size(10).create();
		Page<BookDto> booksPage = new PageImpl<>(books);
		
		when(bookService.findAll(any(Pageable.class))).thenReturn(booksPage);
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL + PORT + "/api/v1/books/search/all")
			    .queryParam("pageable", 0);
		
		ResponseEntity<CustomPageImpl<BookDto>> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<BookDto>>() {});
		Page<BookDto> booksPageResponse = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(10, booksPageResponse.getContent().size());
		assertIterableEquals(booksPage, booksPageResponse);
	}
	
	@Test
	void testFindAllByAuthorName_AskFindAllEntitiesByAuthorName_EntitiesShouldBeReturned200() {
		List<BookDto> books = Instancio.ofList(BookDto.class).size(10).create();
		Page<BookDto> booksPage = new PageImpl<>(books);
		
		when(bookService.findAllByAuthorName(any(Pageable.class), anyString())).thenReturn(booksPage);
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL + PORT + "/api/v1/books/search/author-name/authorName")
			    .queryParam("pageable", 0);
		
		ResponseEntity<CustomPageImpl<BookDto>> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET , null, new ParameterizedTypeReference<CustomPageImpl<BookDto>>() {});
		Page<BookDto> booksPageResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(10, booksPageResponse.getContent().size());
		assertIterableEquals(booksPage, booksPageResponse);
	}
	
	@Test
	void testFindAllByPublisherName_AskFindAllEntitiesByPublisherName_AllEntitiesShouldBeFound200() {
		List<BookDto> books = Instancio.ofList(BookDto.class).size(10).create();
		Page<BookDto> booksPage = new PageImpl<>(books);
		
		when(bookService.findAllByPublisherName(any(Pageable.class), any(String.class))).thenReturn(booksPage);
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL + PORT + "/api/v1/books/search/publisher-name/publisherName")
			    .queryParam("pageable", 0);
		
		ResponseEntity<CustomPageImpl<BookDto>> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<BookDto>>() {});
		Page<BookDto> booksPageResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(10, booksPageResponse.getContent().size());
		assertIterableEquals(booksPage, booksPageResponse);
	}
	
	@Test
	void testFindAllByAgeRange_AskFindAllByAgeRange_AllEntitiesShouldBeFound200() throws ServiceException {
		List<BookDto> books = Instancio.ofList(BookDto.class).size(10).create();
		Page<BookDto> booksPage = new PageImpl<>(books);
		
		when(bookService.findAllByAgeRange(any(Pageable.class), anyInt(), anyInt())).thenReturn(booksPage);
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL + PORT + "/api/v1/books/search/start-age/2/end-age/7")
			    .queryParam("pageable", 0);
		
		ResponseEntity<CustomPageImpl<BookDto>> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<BookDto>>() {});
		Page<BookDto> booksPageResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(10, booksPageResponse.getContent().size());
		assertIterableEquals(booksPage, booksPageResponse);
	}
	
	@Test
	void testFindAllByLocationName_AskFindAllByLocationName_AllEntitiesShouldBeFound200() {
		List<BookDto> books = Instancio.ofList(BookDto.class).size(10).create();
		Page<BookDto> booksPage = new PageImpl<>(books);
		
		when(bookService.findAllByLocationName(any(Pageable.class), anyString())).thenReturn(booksPage);
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL + PORT + "/api/v1/books/search/location-name/locationName")
			    .queryParam("pageable", 0);
		
		ResponseEntity<CustomPageImpl<BookDto>> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<BookDto>>() {});
		Page<BookDto> booksPageResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(10, booksPageResponse.getContent().size());
		assertIterableEquals(booksPage, booksPageResponse);
	}
	
	@Test
	void testFindTop10ByLocationAndAgeRange_AskFindTop10ByLocationAndAgeRange_AllEntitiesShouldBeFound200() throws ServiceException {
		List<BookDto> books = Instancio.ofList(BookDto.class).size(10).create();
		Page<BookDto> booksPage = new PageImpl<>(books);
		
		when(bookService.findTop10ByLocationAndAgeRange(any(Pageable.class), anyString(), anyInt(), anyInt())).thenReturn(booksPage);
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL + PORT + "/api/v1/books/search/location-name/locationName/age-start/1/age-end/10")
			    .queryParam("pageable", 0);
		
		ResponseEntity<CustomPageImpl<BookDto>> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<BookDto>>() {});
		Page<BookDto> booksPageResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(10, booksPageResponse.getContent().size());
		assertIterableEquals(booksPage, booksPageResponse);
	}
	
	@Test
	void testFindByIsbn_AskFindEntityByIsbn_EntityShouldBeReturned200() {
		BookDto book = Instancio.create(BookDto.class);
		
		when(bookService.findByIsbn(anyString())).thenReturn(Optional.of(book));
		
		ResponseEntity<BookDto> responseEntity = restTemplate.getForEntity(BASE_URL + PORT + "/api/v1/books/search/isbn/isbn", BookDto.class);
		BookDto booksResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(book, booksResponse);
	}
	
	@Test
	void testFindByIsbn_AskFindEntityByIsbnIfEntityDoesNotExists_ExceptionShouldBeThrown404() {
		when(bookService.findByIsbn(anyString())).thenReturn(Optional.empty());
		String expectedExceptionMessage = "The book with isbn %s was not found".formatted("isbn");
		
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(BASE_URL + PORT + "/api/v1/books/search/isbn/isbn", String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(expectedExceptionMessage ,responseEntity.getHeaders().getFirst("exceptionMessage"));
	}
	
	@Test
	void testFindByBookTitle_FindEntityByBookTitle_EntityShouldBeReturned200() {
		BookDto bookDto = Instancio.create(BookDto.class);
		
		when(bookService.findByBookTitle(anyString())).thenReturn(Optional.of(bookDto));
		
		ResponseEntity<BookDto> responseEntity = restTemplate.getForEntity(BASE_URL + PORT + "/api/v1/books/search/book-title/bookTitle", BookDto.class);
		BookDto booksResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(bookDto, booksResponse);
	}

	@Test
	void testFindByBookTitle_AskFindEntityByBookTitleIfEntityDoesNotExists_ExceptionShouldBeThrown404() {
		when(bookService.findByBookTitle(anyString())).thenReturn(Optional.empty());
		String expectedExceptionMessage =  "The book with book title %s was not found".formatted("bookTitle");
		
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(BASE_URL + PORT + "/api/v1/books/search/book-title/bookTitle", String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(expectedExceptionMessage ,responseEntity.getHeaders().getFirst("exceptionMessage"));
	}
	
	@Test
	void testCreate_AskPostEntity_EntityShouldBeCreatedAndReturned200() throws ServiceException {
		BookDto bookDto = Instancio.create(BookDto.class);
		PublisherDto publisherDto = Instancio.create(PublisherDto.class);
		AuthorDto authorDto = Instancio.create(AuthorDto.class);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
		HttpEntity<BookDto> request = new HttpEntity<BookDto>(bookDto, headers);
		
		when(authorService.findByAuthorName(anyString())).thenReturn(Optional.of(authorDto));
		when(publisherService.findByPublisherName(anyString())).thenReturn(Optional.of(publisherDto));
		when(bookService.save(any(BookDto.class))).thenReturn(bookDto);
		
		ResponseEntity<BookDto> responseEntity = restTemplate.postForEntity(BASE_URL + PORT + "/api/v1/books/creation", request, BookDto.class );
		verify(bookService).save(any(BookDto.class));
		BookDto bookRatingDtoResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(bookDto, bookRatingDtoResponse);
	}
	
	@Test
	void testUpdate_AskPutEntity_UpdatedEntityShouldBeReturned200() throws ServiceException {
		BookDto bookDto = Instancio.create(BookDto.class);
		PublisherDto publisherDto = Instancio.create(PublisherDto.class);
		AuthorDto authorDto = Instancio.create(AuthorDto.class);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setBearerAuth(token);
		HttpEntity<BookDto> request = new HttpEntity<BookDto>(bookDto, httpHeaders);
		
		when(authorService.findByAuthorName(anyString())).thenReturn(Optional.of(authorDto));
		when(publisherService.findByPublisherName(anyString())).thenReturn(Optional.of(publisherDto));
		when(bookService.save(any(BookDto.class))).thenReturn(bookDto);
		
		ResponseEntity<BookDto> responseEntity = restTemplate.exchange(BASE_URL + PORT + "/api/v1/books/update", HttpMethod.PUT, request, BookDto.class);
		verify(bookService).save(any(BookDto.class));
		BookDto booksResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(bookDto, booksResponse);
	}
	
	@Test
	void testDeleteById_AskDeleteEntityById_EntityShouldBeDeleted204() {
		BookDto bookDto = Instancio.create(BookDto.class);
		
		when(bookService.findById(anyLong())).thenReturn(Optional.of(bookDto));
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
		HttpEntity<BookDto> request = new HttpEntity<BookDto>(headers);
		
		ResponseEntity<Object> responseEntity = restTemplate.exchange(BASE_URL + PORT + "/api/v1/books/deletion/101", HttpMethod.DELETE, request, Object.class);
		
		verify(bookService).findById(anyLong());
		verify(bookService).deleteById(anyLong());
		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
		assertNull(responseEntity.getBody());
	}
	
	@Test
	void testDeleteById_AskDeleteEntityByIdIfEntityDoesNotExists_ExceptionShouldBeThrown404() {	
		String expectedExceptionMessage =  "The book with id = %d was not found".formatted(101);
		when(bookService.findById(anyLong())).thenReturn(Optional.empty());
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
		HttpEntity<BookDto> request = new HttpEntity<BookDto>(headers);
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(BASE_URL + PORT + "/api/v1/books/deletion/101", HttpMethod.DELETE, request, String.class);
		
		verify(bookService).findById(anyLong());
		verifyNoMoreInteractions(bookService);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(expectedExceptionMessage ,responseEntity.getHeaders().getFirst("exceptionMessage"));
	}
}
