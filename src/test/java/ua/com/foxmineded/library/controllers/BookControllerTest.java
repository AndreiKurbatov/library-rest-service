package ua.com.foxmineded.library.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
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
import ua.com.foxmineded.library.dto.BookDto;
import ua.com.foxmineded.library.services.BookService;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
class BookControllerTest {
	final static int PORT = 8080;
	final static String BASE_URL = "http://localhost:";
	@Autowired
	TestRestTemplate restTemplate;
	@MockBean
	BookService bookService;
	
	@Test
	void testFindAll_AskFindAllEntities_AllEntitiesShouldBeFound200 () {
		List<BookDto> books = Instancio.ofList(BookDto.class).size(10).create();
		Page<BookDto> booksPage = new PageImpl<>(books);
		
		when(bookService.findAll(any(Pageable.class))).thenReturn(booksPage);
		
		ResponseEntity<CustomPageImpl<BookDto>> responseEntity = restTemplate.exchange(BASE_URL + PORT + "/api/v1/books/search/all", HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<BookDto>>() {});
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
		
		ResponseEntity<CustomPageImpl<BookDto>> responseEntity = restTemplate.exchange(BASE_URL + PORT + "/api/v1/books/search/author-name/authorName", HttpMethod.GET , null, new ParameterizedTypeReference<CustomPageImpl<BookDto>>() {});
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
		
		ResponseEntity<CustomPageImpl<BookDto>> responseEntity = restTemplate.exchange(BASE_URL + PORT + "/api/v1/books/search/publisher-name/publisherName", HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<BookDto>>() {});
		Page<BookDto> authorsPageResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(10, authorsPageResponse.getContent().size());
		assertIterableEquals(booksPage, authorsPageResponse);
	}
	
	@Test
	void testFindByIsbn_AskFindEntityByIsbn_EntityShouldBeReturned200() {
		BookDto book = Instancio.create(BookDto.class);
		
		when(bookService.findByIsbn(anyString())).thenReturn(Optional.of(book));
		
		ResponseEntity<BookDto> responseEntity = restTemplate.getForEntity(BASE_URL + PORT + "/api/v1/books/search/isbn/isbn", BookDto.class);
		BookDto authorResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(book, authorResponse);
	}
	
	@Test
	void testFindByIsbn_AskFindEntityByIsbnIfEntityDoesNotExists_ExceptionShouldBeThrown404() {
		when(bookService.findByIsbn(anyString())).thenReturn(Optional.empty());
		String expectedExceptionMessage = "The book with isbn %s was not found".formatted("isbn");
		
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(BASE_URL + PORT + "/api/v1/books/search/isbn/isbn", String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(expectedExceptionMessage ,responseEntity.getBody());
	}
	
	@Test
	void testFindByBookTitle_FindEntityByBookTitle_EntityShouldBeReturned200() {
		BookDto bookDto = Instancio.create(BookDto.class);
		
		when(bookService.findByBookTitle(anyString())).thenReturn(Optional.of(bookDto));
		
		ResponseEntity<BookDto> responseEntity = restTemplate.getForEntity(BASE_URL + PORT + "/api/v1/books/search/book-title/bookTitle", BookDto.class);
		BookDto authorResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(bookDto, authorResponse);
	}

	@Test
	void testFindByBookTitle_AskFindEntityByBookTitleIfEntityDoesNotExists_ExceptionShouldBeThrown404() {
		when(bookService.findByBookTitle(anyString())).thenReturn(Optional.empty());
		String expectedExceptionMessage =  "The book with book title %s was not found".formatted("bookTitle");
		
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(BASE_URL + PORT + "/api/v1/books/search/book-title/bookTitle", String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(expectedExceptionMessage ,responseEntity.getBody());
	}
	
	@Test
	void testCreate_AskPostEntity_EntityShouldBeCreatedAndReturned200() {
		BookDto bookDto = Instancio.create(BookDto.class);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<BookDto> request = new HttpEntity<BookDto>(bookDto, headers);
		
		when(bookService.save(any(BookDto.class))).thenReturn(bookDto);
		
		ResponseEntity<BookDto> responseEntity = restTemplate.postForEntity(BASE_URL + PORT + "/api/v1/books/creation", request, BookDto.class );
		verify(bookService).save(any(BookDto.class));
		BookDto authorResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(bookDto, authorResponse);
	}
	
	@Test
	void testUpdate_AskPutEntity_UpdatedEntityShouldBeReturned200() {
		BookDto bookDto = Instancio.create(BookDto.class);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<BookDto> request = new HttpEntity<BookDto>(bookDto, httpHeaders);
		
		when(bookService.save(any(BookDto.class))).thenReturn(bookDto);
		
		ResponseEntity<BookDto> responseEntity = restTemplate.exchange(BASE_URL + PORT + "/api/v1/books/update", HttpMethod.PUT, request, BookDto.class);
		verify(bookService).save(any(BookDto.class));
		BookDto authorResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(bookDto, authorResponse);
	}
	
	@Test
	void testDeleteById_AskDeleteEntityById_EntityShouldBeDeleted200() {
		BookDto bookDto = Instancio.create(BookDto.class);
		
		when(bookService.findById(anyLong())).thenReturn(Optional.of(bookDto));
		
		ResponseEntity<Object> responseEntity = restTemplate.exchange(BASE_URL + PORT + "/api/v1/books/deletion/101", HttpMethod.DELETE, null, Object.class);
		
		verify(bookService).findById(anyLong());
		verify(bookService).deleteById(anyLong());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNull(responseEntity.getBody());
	}
	
	@Test
	void testDeleteById_AskDeleteEntityByIdIfEntityDoesNotExists_ExceptionShouldBeThrown404() {	
		String expectedExceptionMessage =  "The book with id = %d was not found".formatted(101);
		when(bookService.findById(anyLong())).thenReturn(Optional.empty());
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(BASE_URL + PORT + "/api/v1/books/deletion/101", HttpMethod.DELETE, null, String.class);
		
		verify(bookService).findById(anyLong());
		verifyNoMoreInteractions(bookService);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(expectedExceptionMessage ,responseEntity.getBody());
	}
}
