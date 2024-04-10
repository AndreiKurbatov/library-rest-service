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

import ua.com.foxmineded.libraryrestservice.dto.PublisherDto;
import ua.com.foxmineded.libraryrestservice.exceptions.ServiceException;
import ua.com.foxmineded.libraryrestservice.models.CustomPageImpl;
import ua.com.foxmineded.libraryrestservice.services.PublisherService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class PublisherControllerTest {
	final static String BASE_URL = "http://localhost:";
	@Value("${auth0.token}")
	String token;
	@LocalServerPort
	int PORT;
	@Autowired
	TestRestTemplate restTemplate;
	@MockBean
	PublisherService publisherService;

	@Test
	void testFindAll_AskFindAllEntities_AllEntitiesShouldBeFound200() {
		List<PublisherDto> publishers = Instancio.ofList(PublisherDto.class).size(10).create();
		Page<PublisherDto> publishersPage = new PageImpl<>(publishers);

		when(publisherService.findAll(any(Pageable.class))).thenReturn(publishersPage);

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(BASE_URL + PORT + "/api/v1/publishers/search/all").queryParam("pageable", 0);

		ResponseEntity<CustomPageImpl<PublisherDto>> responseEntity = restTemplate.exchange(builder.toUriString(),
				HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<PublisherDto>>() {
				});
		Page<PublisherDto> publishersPageResponse = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(10, publishersPageResponse.getContent().size());
		assertIterableEquals(publishersPage, publishersPageResponse);
	}

	@Test
	void testFindAllByAuthorName_AskFindAllEntitiesByAuthorName_EntitiesShouldBeReturned200() {
		List<PublisherDto> publishers = Instancio.ofList(PublisherDto.class).size(10).create();
		Page<PublisherDto> publishersPage = new PageImpl<>(publishers);

		when(publisherService.findAllByAuthorName(any(Pageable.class), anyString())).thenReturn(publishersPage);

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(BASE_URL + PORT + "/api/v1/publishers/search/author-name/authorName")
				.queryParam("pageable", 0);

		ResponseEntity<CustomPageImpl<PublisherDto>> responseEntity = restTemplate.exchange(builder.toUriString(),
				HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<PublisherDto>>() {
				});
		Page<PublisherDto> publishersPageResponse = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(10, publishersPageResponse.getContent().size());
		assertIterableEquals(publishersPage, publishersPageResponse);
	}

	@Test
	void testFindByPublisherName_AskFindEntityByPublisherName_EntityShouldBeFound200() {
		PublisherDto publisherDto = Instancio.create(PublisherDto.class);

		when(publisherService.findByPublisherName(any(String.class))).thenReturn(Optional.of(publisherDto));

		ResponseEntity<PublisherDto> responseEntity = restTemplate.exchange(
				BASE_URL + PORT + "/api/v1/publishers/search/publisher-name/publisherName", HttpMethod.GET, null,
				PublisherDto.class);
		PublisherDto publishersResponse = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(publisherDto, publishersResponse);
	}

	@Test
	void testFindByPublisherName_AskFindEntityByPublisherNameIfEntityDoesNotExists_ExceptionShouldBeThrown404() {
		String expectedExceptionMessage = "The publisher with name %s was not found".formatted("publisherName");
		when(publisherService.findByPublisherName(any(String.class))).thenReturn(Optional.empty());

		ResponseEntity<String> responseEntity = restTemplate.exchange(
				BASE_URL + PORT + "/api/v1/publishers/search/publisher-name/publisherName", HttpMethod.GET, null,
				String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(expectedExceptionMessage, responseEntity.getHeaders().getFirst("exceptionMessage"));
	}

	@Test
	void testFindByBookTitle_FindEntityByBookTitle_EntityShouldBeReturned200() {
		List<PublisherDto> publishers = Instancio.ofList(PublisherDto.class).size(10).create();
		Page<PublisherDto> publishersPage = new PageImpl<>(publishers);

		when(publisherService.findAllByBookTitle(any(Pageable.class), anyString())).thenReturn(publishersPage);

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(BASE_URL + PORT + "/api/v1/publishers/search/book-title/bookTitle")
				.queryParam("pageable", 0);

		ResponseEntity<CustomPageImpl<PublisherDto>> responseEntity = restTemplate.exchange(builder.toUriString(),
				HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<PublisherDto>>() {
				});
		Page<PublisherDto> publishersPageResponse = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(10, publishersPageResponse.getContent().size());
		assertIterableEquals(publishersPage, publishersPageResponse);
	}

	@Test
	void testFindByIsbn_AskFindEntityByIsbn_EntityShouldBeReturned200() {
		PublisherDto publisherDto = Instancio.create(PublisherDto.class);

		when(publisherService.findByIsbn(anyString())).thenReturn(Optional.of(publisherDto));

		ResponseEntity<PublisherDto> responseEntity = restTemplate
				.getForEntity(BASE_URL + PORT + "/api/v1/publishers/search/isbn/isbn", PublisherDto.class);
		PublisherDto publisherResponse = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(publisherDto, publisherResponse);
	}

	@Test
	void testFindByIsbn_AskFindEntityByIsbnIfEntityDoesNotExists_ExceptionShouldBeThrown404() {
		when(publisherService.findByIsbn(anyString())).thenReturn(Optional.empty());
		String expectedExceptionMessage = "The publisher by isbn %s was not found".formatted("isbn");
		
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(BASE_URL + PORT + "/api/v1/publishers/search/isbn/isbn", String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(expectedExceptionMessage ,responseEntity.getHeaders().getFirst("exceptionMessage"));
	}

	@Test
	void testCreate_AskPostEntity_EntityShouldBeCreatedAndReturned201() throws ServiceException {
		PublisherDto publisherDto = Instancio.create(PublisherDto.class);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		HttpEntity<PublisherDto> request = new HttpEntity<PublisherDto>(publisherDto, headers);

		when(publisherService.save(any(PublisherDto.class))).thenReturn(publisherDto);

		ResponseEntity<PublisherDto> responseEntity = restTemplate
				.postForEntity(BASE_URL + PORT + "/api/v1/publishers/creation", request, PublisherDto.class);
		verify(publisherService).save(any(PublisherDto.class));
		PublisherDto publisherResponse = responseEntity.getBody();

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(publisherDto, publisherResponse);
	}

	@Test
	void testUpdate_AskPutEntity_UpdatedEntityShouldBeReturned200() throws ServiceException {
		PublisherDto publisherDto = Instancio.create(PublisherDto.class);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setBearerAuth(token);
		HttpEntity<PublisherDto> request = new HttpEntity<PublisherDto>(publisherDto, httpHeaders);

		when(publisherService.save(any(PublisherDto.class))).thenReturn(publisherDto);

		ResponseEntity<PublisherDto> responseEntity = restTemplate
				.exchange(BASE_URL + PORT + "/api/v1/publishers/update", HttpMethod.PUT, request, PublisherDto.class);
		verify(publisherService).save(any(PublisherDto.class));
		PublisherDto publisherResponse = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(publisherDto, publisherResponse);
	}

	@Test
	void testDeleteById_AskDeleteEntityById_EntityShouldBeDeleted204() {
		PublisherDto publisherDto = Instancio.create(PublisherDto.class);

		when(publisherService.findById(anyLong())).thenReturn(Optional.of(publisherDto));

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setBearerAuth(token);
		HttpEntity<Object> request = new HttpEntity<Object>(httpHeaders);

		ResponseEntity<Object> responseEntity = restTemplate.exchange(
				BASE_URL + PORT + "/api/v1/publishers/deletion/101", HttpMethod.DELETE, request, Object.class);

		verify(publisherService).findById(anyLong());
		verify(publisherService).deleteById(anyLong());
		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
		assertNull(responseEntity.getBody());
	}

	@Test
	void testDeleteById_AskDeleteEntityByIdIfEntityDoesNotExists_ExceptionShouldBeThrown404() {
		String expectedExceptionMessage = "The publisher with id = %d was not found".formatted(101);
		when(publisherService.findById(anyLong())).thenReturn(Optional.empty());

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setBearerAuth(token);
		HttpEntity<Object> request = new HttpEntity<Object>(httpHeaders);

		ResponseEntity<String> responseEntity = restTemplate.exchange(
				BASE_URL + PORT + "/api/v1/publishers/deletion/101", HttpMethod.DELETE, request, String.class);

		verify(publisherService).findById(anyLong());
		verifyNoMoreInteractions(publisherService);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(expectedExceptionMessage, responseEntity.getHeaders().getFirst("exceptionMessage"));
	}
}
