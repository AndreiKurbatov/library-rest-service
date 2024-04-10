package ua.com.foxmineded.libraryrestservice.controllers;

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
import ua.com.foxmineded.libraryrestservice.exceptions.ServiceException;
import ua.com.foxmineded.libraryrestservice.models.CustomPageImpl;
import ua.com.foxmineded.libraryrestservice.services.AuthorService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
class AuthorControllerTest {
	final static String BASE_URL = "http://localhost:";
	@Value("${auth0.token}")
	String token;
	@LocalServerPort
	int PORT;
	@Autowired
	TestRestTemplate restTemplate;
	@MockBean
	AuthorService authorService;

	@Test
	void testFindAll_AskFindAllEntities_AllEntitiesShouldBeFound200 () {
		List<AuthorDto> authors = Instancio.ofList(AuthorDto.class).size(10).create();
		Page<AuthorDto> authorsPage = new PageImpl<>(authors);
		
		when(authorService.findAll(any(Pageable.class))).thenReturn(authorsPage);
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL + PORT + "/api/v1/authors/search/all")
			    .queryParam("pageable", 0);
		
		ResponseEntity<CustomPageImpl<AuthorDto>> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<AuthorDto>>() {});
		Page<AuthorDto> authorsPageResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(10, authorsPageResponse.getContent().size());
		assertIterableEquals(authorsPage, authorsPageResponse);
	}
	
	@Test
	void testFindAllByPublisherName_AskFindAllEntitiesByPublisherName_AllEntitiesShouldBeFound200() {
		List<AuthorDto> authors = Instancio.ofList(AuthorDto.class).size(10).create();
		Page<AuthorDto> authorsPage = new PageImpl<>(authors);
		
		when(authorService.findAllByPublisherName(any(Pageable.class), anyString())).thenReturn(authorsPage);
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL + PORT + "/api/v1/authors/search/publisher-name/publisherName")
			    .queryParam("pageable", 0);
		
		ResponseEntity<CustomPageImpl<AuthorDto>> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<AuthorDto>>() {});
		Page<AuthorDto> authorsPageResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(10, authorsPageResponse.getContent().size());
		assertIterableEquals(authorsPage, authorsPageResponse);
	}
	
	@Test
	void testFindByAuthorName_AskFindEntityByAuthorName_EntityShouldBeReturned200() throws ServiceException {
		AuthorDto author = Instancio.create(AuthorDto.class);
		
		when(authorService.findByAuthorName(anyString())).thenReturn(Optional.of(author));
		
		ResponseEntity<AuthorDto> responseEntity = restTemplate.getForEntity(BASE_URL + PORT + "/api/v1/authors/search/author-name/authorName", AuthorDto.class);
		AuthorDto authorResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(author, authorResponse);
	}
	
	@Test
	void testFindByAuthorName_AskFindEntityByAuthorNameIfEntityDoesNotExists_ExceptionShouldBeThrown404() {
		when(authorService.findByAuthorName(anyString())).thenReturn(Optional.empty());
		String expectedExceptionMessage = "The author with name %s was not found".formatted("authorName");
		
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(BASE_URL + PORT + "/api/v1/authors/search/author-name/authorName", String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(expectedExceptionMessage ,responseEntity.getHeaders().getFirst("exceptionMessage"));
	}
	
	@Test
	void testFindByIsbn_AskFindEntityByIsbn_EntityShouldBeReturned200() {
		AuthorDto author = Instancio.create(AuthorDto.class);
		
		when(authorService.findByIsbn(anyString())).thenReturn(Optional.of(author));
		
		ResponseEntity<AuthorDto> responseEntity = restTemplate.getForEntity(BASE_URL + PORT + "/api/v1/authors/search/isbn/isbn", AuthorDto.class);
		AuthorDto authorResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(author, authorResponse);
	}
	
	@Test
	void testFindByIsbn_AskFindEntityByIsbnIfEntityDoesNotExists_ExceptionShouldBeThrown404() {
		when(authorService.findByIsbn(anyString())).thenReturn(Optional.empty());
		String expectedExceptionMessage = "The author with isbn %s was not found".formatted("isbn");
		
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(BASE_URL + PORT + "/api/v1/authors/search/isbn/isbn", String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(expectedExceptionMessage ,responseEntity.getHeaders().getFirst("exceptionMessage"));
	}
	
	@Test
	void testFindByBookTitle_FindEntityByBookTitle_EntityShouldBeReturned200() {
		AuthorDto author = Instancio.create(AuthorDto.class);
		
		when(authorService.findByBookTitle(anyString())).thenReturn(Optional.of(author));
		
		ResponseEntity<AuthorDto> responseEntity = restTemplate.getForEntity(BASE_URL + PORT + "/api/v1/authors/search/book-title/bookTitle", AuthorDto.class);
		AuthorDto authorResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(author, authorResponse);
	}
	
	@Test
	void testFindByBookTitle_AskFindEntityByBookTitleIfEntityDoesNotExists_ExceptionShouldBeThrown404() {
		when(authorService.findByBookTitle(anyString())).thenReturn(Optional.empty());
		String expectedExceptionMessage =  "The author with book title %s was not found".formatted("bookTitle");
		
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(BASE_URL + PORT + "/api/v1/authors/search/book-title/bookTitle", String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(expectedExceptionMessage ,responseEntity.getHeaders().getFirst("exceptionMessage"));
	}
	
	@Test
	void testCreate_AskPostEntity_EntityShouldBeCreatedAndReturned201() throws ServiceException {
		AuthorDto author = Instancio.create(AuthorDto.class);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		HttpEntity<AuthorDto> request = new HttpEntity<AuthorDto>(author, headers);
		
		when(authorService.save(any(AuthorDto.class))).thenReturn(author);	
		
		ResponseEntity<AuthorDto> responseEntity = restTemplate.postForEntity(BASE_URL + PORT + "/api/v1/authors/creation", request, AuthorDto.class );
		verify(authorService).save(any(AuthorDto.class));
		AuthorDto authorResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(author, authorResponse);
	}
	
	@Test
	void testUpdate_AskPutEntity_UpdatedEntityShouldBeReturned200() throws ServiceException {
		AuthorDto author = Instancio.create(AuthorDto.class);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setBearerAuth(token);
		HttpEntity<AuthorDto> request = new HttpEntity<AuthorDto>(author, httpHeaders);
		
		when(authorService.save(any(AuthorDto.class))).thenReturn(author);
		
		ResponseEntity<AuthorDto> responseEntity = restTemplate.exchange(BASE_URL + PORT + "/api/v1/authors/update", HttpMethod.PUT, request, AuthorDto.class);
		AuthorDto authorResponse = responseEntity.getBody();
		
		verify(authorService).save(any(AuthorDto.class));
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(author, authorResponse);
	}
	
	@Test
	void testDeleteById_AskDeleteEntityById_EntityShouldBeDeleted204() {
		AuthorDto authorDto = Instancio.create(AuthorDto.class);
		
		when(authorService.findById(anyLong())).thenReturn(Optional.of(authorDto));
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		
		ResponseEntity<Object> responseEntity = restTemplate.exchange(BASE_URL + PORT + "/api/v1/authors/deletion/101", HttpMethod.DELETE, request, Object.class);
		
		verify(authorService).findById(anyLong());
		verify(authorService).deleteById(anyLong());
		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
		assertNull(responseEntity.getBody());
	}
	
	@Test
	void testDeleteById_AskDeleteEntityByIdIfEntityDoesNotExists_ExceptionShouldBeThrown404() {	
		String expectedExceptionMessage = "The author with id = %d was not found".formatted(101);
		when(authorService.findById(anyLong())).thenReturn(Optional.empty());
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(BASE_URL + PORT + "/api/v1/authors/deletion/101", HttpMethod.DELETE, request, String.class);
		
		verify(authorService).findById(anyLong());
		verifyNoMoreInteractions(authorService);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(expectedExceptionMessage ,responseEntity.getHeaders().getFirst("exceptionMessage"));
	}
}
