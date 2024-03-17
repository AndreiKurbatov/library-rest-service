package ua.com.foxmineded.library.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
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
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.test.context.ActiveProfiles;
import ua.com.foxmineded.library.dto.AuthorDto;
import ua.com.foxmineded.library.exceptions.ServiceException;
import ua.com.foxmineded.library.services.AuthorService;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
class AuthorControllerTest {
	final static int PORT = 8080;
	final static String BASE_URL = "http://localhost:";
	@Autowired
	TestRestTemplate restTemplate;
	@Autowired
	ModelMapper mapper;
	@MockBean
	AuthorService authorService;

	@Test
	void testFindAll_AskFindAllEntities_AllEntitiesShouldBeFoundOK () {
		List<AuthorDto> authors = Instancio.ofList(AuthorDto.class).size(10).create();
		Page<AuthorDto> authorsPage = new PageImpl<>(authors);
		
		when(authorService.findAll(any(Pageable.class))).thenReturn(authorsPage);
		
		ResponseEntity<CustomPageImpl<AuthorDto>> responseEntity = restTemplate.exchange(BASE_URL + PORT + "/api/v1/authors/search/all", HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<AuthorDto>>() {});
		Page<AuthorDto> authorsPageResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(10, authorsPageResponse.getContent().size());
		assertIterableEquals(authorsPage, authorsPageResponse);
	}
	
	@Test
	void testFindAllByPublisherName_AskFindAllEntitiesByPublisherName_AllEntitiesShouldBeFoundOK() {
		List<AuthorDto> authors = Instancio.ofList(AuthorDto.class).size(10).create();
		Page<AuthorDto> authorsPage = new PageImpl<>(authors);
		
		when(authorService.findAllByPublisherName(anyString(), any(Pageable.class))).thenReturn(authorsPage);
		
		ResponseEntity<CustomPageImpl<AuthorDto>> responseEntity = restTemplate.exchange(BASE_URL + PORT + "/api/v1/authors/search/publisher-name/publisherName", HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<AuthorDto>>() {});
		Page<AuthorDto> authorsPageResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(10, authorsPageResponse.getContent().size());
		assertIterableEquals(authorsPage, authorsPageResponse);
	}
	
	@Test
	void testFindByAuthorName_AskFindEntityByAuthorName_EntityShouldBeReturnedOK() throws ServiceException {
		AuthorDto author = Instancio.create(AuthorDto.class);
		
		when(authorService.findByAuthorName(anyString())).thenReturn(author);
		
		ResponseEntity<AuthorDto> responseEntity = restTemplate.getForEntity(BASE_URL + PORT + "/api/v1/authors/search/author-name/authorName", AuthorDto.class);
		AuthorDto authorResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(author, authorResponse);
	}
	
	@Test
	void testFindByIsbn_AskFindEntityByIsbn_EntityShouldBeReturnedOK() throws ServiceException {
		AuthorDto author = Instancio.create(AuthorDto.class);
		
		when(authorService.findByIsbn(anyString())).thenReturn(author);
		
		ResponseEntity<AuthorDto> responseEntity = restTemplate.getForEntity(BASE_URL + PORT + "/api/v1/authors/search/isbn/isbn", AuthorDto.class);
		AuthorDto authorResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(author, authorResponse);
	}
	
	@Test
	void testFindByBookTitle_FindEntityByBookTitle_EntityShouldBeReturnedOK() throws ServiceException {
		AuthorDto author = Instancio.create(AuthorDto.class);
		
		when(authorService.findByBookTitle(anyString())).thenReturn(author);
		
		ResponseEntity<AuthorDto> responseEntity = restTemplate.getForEntity(BASE_URL + PORT + "/api/v1/authors/search/book-title/bookTitle", AuthorDto.class);
		AuthorDto authorResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(author, authorResponse);
	}
	
	@Test
	void testCreate_AskPostEntity_EntityShouldBeCreatedAndReturned200() {
		AuthorDto author = Instancio.create(AuthorDto.class);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<AuthorDto> request = new HttpEntity<AuthorDto>(author, headers);
		
		when(authorService.save(any(AuthorDto.class))).thenReturn(author);
		
		ResponseEntity<AuthorDto> responseEntity = restTemplate.postForEntity(BASE_URL + PORT + "/api/v1/authors/creation", request, AuthorDto.class );
		verify(authorService).save(any(AuthorDto.class));
		AuthorDto authorResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(author, authorResponse);
	}
	
	@Test
	void testUpdate_AskPutEntity_UpdatedEntityShouldBeReturned200() {
		AuthorDto author = Instancio.create(AuthorDto.class);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<AuthorDto> request = new HttpEntity<AuthorDto>(author, httpHeaders);
		
		when(authorService.save(any(AuthorDto.class))).thenReturn(author);
		
		ResponseEntity<AuthorDto> responseEntity = restTemplate.exchange(BASE_URL + PORT + "/api/v1/authors/update", HttpMethod.PUT, request, AuthorDto.class);
		verify(authorService).save(any(AuthorDto.class));
		AuthorDto authorResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(author, authorResponse);
	}
	
	
}
