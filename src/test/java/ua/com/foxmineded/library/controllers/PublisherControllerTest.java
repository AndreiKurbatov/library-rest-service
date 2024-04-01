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
import ua.com.foxmineded.library.dto.PublisherDto;
import ua.com.foxmineded.library.exceptions.ServiceException;
import ua.com.foxmineded.library.models.CustomPageImpl;
import ua.com.foxmineded.library.services.PublisherService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class PublisherControllerTest {
	final static String BASE_URL = "http://localhost:";
	final static String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6InJBTC02eWVyMUZKV3N6U0tzMUQtdiJ9.eyJpc3MiOiJodHRwczovL2Rldi1naXA3bDA4b3BqMDRoeHN0LnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJpdVo4Y2JJdE12Q3R1MWFtaUxNcU5DZnBPYVdORmx0MUBjbGllbnRzIiwiYXVkIjoiaHR0cHM6Ly9kZXYtZ2lwN2wwOG9wajA0aHhzdC51cy5hdXRoMC5jb20vYXBpL3YyLyIsImlhdCI6MTcxMTk2MTAwOSwiZXhwIjoxNzEyMDQ3NDA5LCJzY29wZSI6InJlYWQ6Y2xpZW50X2dyYW50cyBjcmVhdGU6Y2xpZW50X2dyYW50cyBkZWxldGU6Y2xpZW50X2dyYW50cyB1cGRhdGU6Y2xpZW50X2dyYW50cyByZWFkOnVzZXJzIHVwZGF0ZTp1c2VycyBkZWxldGU6dXNlcnMgY3JlYXRlOnVzZXJzIHJlYWQ6dXNlcnNfYXBwX21ldGFkYXRhIHVwZGF0ZTp1c2Vyc19hcHBfbWV0YWRhdGEgZGVsZXRlOnVzZXJzX2FwcF9tZXRhZGF0YSBjcmVhdGU6dXNlcnNfYXBwX21ldGFkYXRhIHJlYWQ6dXNlcl9jdXN0b21fYmxvY2tzIGNyZWF0ZTp1c2VyX2N1c3RvbV9ibG9ja3MgZGVsZXRlOnVzZXJfY3VzdG9tX2Jsb2NrcyBjcmVhdGU6dXNlcl90aWNrZXRzIHJlYWQ6Y2xpZW50cyB1cGRhdGU6Y2xpZW50cyBkZWxldGU6Y2xpZW50cyBjcmVhdGU6Y2xpZW50cyByZWFkOmNsaWVudF9rZXlzIHVwZGF0ZTpjbGllbnRfa2V5cyBkZWxldGU6Y2xpZW50X2tleXMgY3JlYXRlOmNsaWVudF9rZXlzIHJlYWQ6Y29ubmVjdGlvbnMgdXBkYXRlOmNvbm5lY3Rpb25zIGRlbGV0ZTpjb25uZWN0aW9ucyBjcmVhdGU6Y29ubmVjdGlvbnMgcmVhZDpyZXNvdXJjZV9zZXJ2ZXJzIHVwZGF0ZTpyZXNvdXJjZV9zZXJ2ZXJzIGRlbGV0ZTpyZXNvdXJjZV9zZXJ2ZXJzIGNyZWF0ZTpyZXNvdXJjZV9zZXJ2ZXJzIHJlYWQ6ZGV2aWNlX2NyZWRlbnRpYWxzIHVwZGF0ZTpkZXZpY2VfY3JlZGVudGlhbHMgZGVsZXRlOmRldmljZV9jcmVkZW50aWFscyBjcmVhdGU6ZGV2aWNlX2NyZWRlbnRpYWxzIHJlYWQ6cnVsZXMgdXBkYXRlOnJ1bGVzIGRlbGV0ZTpydWxlcyBjcmVhdGU6cnVsZXMgcmVhZDpydWxlc19jb25maWdzIHVwZGF0ZTpydWxlc19jb25maWdzIGRlbGV0ZTpydWxlc19jb25maWdzIHJlYWQ6aG9va3MgdXBkYXRlOmhvb2tzIGRlbGV0ZTpob29rcyBjcmVhdGU6aG9va3MgcmVhZDphY3Rpb25zIHVwZGF0ZTphY3Rpb25zIGRlbGV0ZTphY3Rpb25zIGNyZWF0ZTphY3Rpb25zIHJlYWQ6ZW1haWxfcHJvdmlkZXIgdXBkYXRlOmVtYWlsX3Byb3ZpZGVyIGRlbGV0ZTplbWFpbF9wcm92aWRlciBjcmVhdGU6ZW1haWxfcHJvdmlkZXIgYmxhY2tsaXN0OnRva2VucyByZWFkOnN0YXRzIHJlYWQ6aW5zaWdodHMgcmVhZDp0ZW5hbnRfc2V0dGluZ3MgdXBkYXRlOnRlbmFudF9zZXR0aW5ncyByZWFkOmxvZ3MgcmVhZDpsb2dzX3VzZXJzIHJlYWQ6c2hpZWxkcyBjcmVhdGU6c2hpZWxkcyB1cGRhdGU6c2hpZWxkcyBkZWxldGU6c2hpZWxkcyByZWFkOmFub21hbHlfYmxvY2tzIGRlbGV0ZTphbm9tYWx5X2Jsb2NrcyB1cGRhdGU6dHJpZ2dlcnMgcmVhZDp0cmlnZ2VycyByZWFkOmdyYW50cyBkZWxldGU6Z3JhbnRzIHJlYWQ6Z3VhcmRpYW5fZmFjdG9ycyB1cGRhdGU6Z3VhcmRpYW5fZmFjdG9ycyByZWFkOmd1YXJkaWFuX2Vucm9sbG1lbnRzIGRlbGV0ZTpndWFyZGlhbl9lbnJvbGxtZW50cyBjcmVhdGU6Z3VhcmRpYW5fZW5yb2xsbWVudF90aWNrZXRzIHJlYWQ6dXNlcl9pZHBfdG9rZW5zIGNyZWF0ZTpwYXNzd29yZHNfY2hlY2tpbmdfam9iIGRlbGV0ZTpwYXNzd29yZHNfY2hlY2tpbmdfam9iIHJlYWQ6Y3VzdG9tX2RvbWFpbnMgZGVsZXRlOmN1c3RvbV9kb21haW5zIGNyZWF0ZTpjdXN0b21fZG9tYWlucyB1cGRhdGU6Y3VzdG9tX2RvbWFpbnMgcmVhZDplbWFpbF90ZW1wbGF0ZXMgY3JlYXRlOmVtYWlsX3RlbXBsYXRlcyB1cGRhdGU6ZW1haWxfdGVtcGxhdGVzIHJlYWQ6bWZhX3BvbGljaWVzIHVwZGF0ZTptZmFfcG9saWNpZXMgcmVhZDpyb2xlcyBjcmVhdGU6cm9sZXMgZGVsZXRlOnJvbGVzIHVwZGF0ZTpyb2xlcyByZWFkOnByb21wdHMgdXBkYXRlOnByb21wdHMgcmVhZDpicmFuZGluZyB1cGRhdGU6YnJhbmRpbmcgZGVsZXRlOmJyYW5kaW5nIHJlYWQ6bG9nX3N0cmVhbXMgY3JlYXRlOmxvZ19zdHJlYW1zIGRlbGV0ZTpsb2dfc3RyZWFtcyB1cGRhdGU6bG9nX3N0cmVhbXMgY3JlYXRlOnNpZ25pbmdfa2V5cyByZWFkOnNpZ25pbmdfa2V5cyB1cGRhdGU6c2lnbmluZ19rZXlzIHJlYWQ6bGltaXRzIHVwZGF0ZTpsaW1pdHMgY3JlYXRlOnJvbGVfbWVtYmVycyByZWFkOnJvbGVfbWVtYmVycyBkZWxldGU6cm9sZV9tZW1iZXJzIHJlYWQ6ZW50aXRsZW1lbnRzIHJlYWQ6YXR0YWNrX3Byb3RlY3Rpb24gdXBkYXRlOmF0dGFja19wcm90ZWN0aW9uIHJlYWQ6b3JnYW5pemF0aW9uc19zdW1tYXJ5IGNyZWF0ZTphdXRoZW50aWNhdGlvbl9tZXRob2RzIHJlYWQ6YXV0aGVudGljYXRpb25fbWV0aG9kcyB1cGRhdGU6YXV0aGVudGljYXRpb25fbWV0aG9kcyBkZWxldGU6YXV0aGVudGljYXRpb25fbWV0aG9kcyByZWFkOm9yZ2FuaXphdGlvbnMgdXBkYXRlOm9yZ2FuaXphdGlvbnMgY3JlYXRlOm9yZ2FuaXphdGlvbnMgZGVsZXRlOm9yZ2FuaXphdGlvbnMgY3JlYXRlOm9yZ2FuaXphdGlvbl9tZW1iZXJzIHJlYWQ6b3JnYW5pemF0aW9uX21lbWJlcnMgZGVsZXRlOm9yZ2FuaXphdGlvbl9tZW1iZXJzIGNyZWF0ZTpvcmdhbml6YXRpb25fY29ubmVjdGlvbnMgcmVhZDpvcmdhbml6YXRpb25fY29ubmVjdGlvbnMgdXBkYXRlOm9yZ2FuaXphdGlvbl9jb25uZWN0aW9ucyBkZWxldGU6b3JnYW5pemF0aW9uX2Nvbm5lY3Rpb25zIGNyZWF0ZTpvcmdhbml6YXRpb25fbWVtYmVyX3JvbGVzIHJlYWQ6b3JnYW5pemF0aW9uX21lbWJlcl9yb2xlcyBkZWxldGU6b3JnYW5pemF0aW9uX21lbWJlcl9yb2xlcyBjcmVhdGU6b3JnYW5pemF0aW9uX2ludml0YXRpb25zIHJlYWQ6b3JnYW5pemF0aW9uX2ludml0YXRpb25zIGRlbGV0ZTpvcmdhbml6YXRpb25faW52aXRhdGlvbnMgZGVsZXRlOnBob25lX3Byb3ZpZGVycyBjcmVhdGU6cGhvbmVfcHJvdmlkZXJzIHJlYWQ6cGhvbmVfcHJvdmlkZXJzIHVwZGF0ZTpwaG9uZV9wcm92aWRlcnMgZGVsZXRlOnBob25lX3RlbXBsYXRlcyBjcmVhdGU6cGhvbmVfdGVtcGxhdGVzIHJlYWQ6cGhvbmVfdGVtcGxhdGVzIHVwZGF0ZTpwaG9uZV90ZW1wbGF0ZXMgY3JlYXRlOmVuY3J5cHRpb25fa2V5cyByZWFkOmVuY3J5cHRpb25fa2V5cyB1cGRhdGU6ZW5jcnlwdGlvbl9rZXlzIGRlbGV0ZTplbmNyeXB0aW9uX2tleXMgcmVhZDpzZXNzaW9ucyBkZWxldGU6c2Vzc2lvbnMgcmVhZDpyZWZyZXNoX3Rva2VucyBkZWxldGU6cmVmcmVzaF90b2tlbnMgY3JlYXRlOnNlbGZfc2VydmljZV9wcm9maWxlcyByZWFkOnNlbGZfc2VydmljZV9wcm9maWxlcyB1cGRhdGU6c2VsZl9zZXJ2aWNlX3Byb2ZpbGVzIGRlbGV0ZTpzZWxmX3NlcnZpY2VfcHJvZmlsZXMgY3JlYXRlOnNzb19hY2Nlc3NfdGlja2V0cyByZWFkOmNsaWVudF9jcmVkZW50aWFscyBjcmVhdGU6Y2xpZW50X2NyZWRlbnRpYWxzIHVwZGF0ZTpjbGllbnRfY3JlZGVudGlhbHMgZGVsZXRlOmNsaWVudF9jcmVkZW50aWFscyIsImd0eSI6ImNsaWVudC1jcmVkZW50aWFscyIsImF6cCI6Iml1WjhjYkl0TXZDdHUxYW1pTE1xTkNmcE9hV05GbHQxIn0.V4XUAp6buz7ky3O35-0JJmvZLGIcgrKDqZd4z87pXyKNvL5WBqSJenIJYK2g6d-M750ywS0wvdOEs-4ofYrO1Ye6sqCOqgLqN6MtB2Teyk4pHmx2n19BHuyxORiDOzj0CVH8w2rfD5X3KcltjZJq-6rDXXXh5jhwvd3Bv7UXwF-uP0z31EBLdyAWpOVCZSz59hOeHux_KiqBT9mlKM61UmJYs9EmSMXpaHE9TXAef87ihKkvHxQ1lJe9M--5IVwSjB9KCbmJaAecGDuCjgSzP4Z_enCju1Q97HpZrK1EgNkhwf3S0vr_Cd0a8QBd95a5QNDlhltoMrU9ww_ND04dxA";
	@LocalServerPort
	int PORT;
	@Autowired
	TestRestTemplate restTemplate;
	@MockBean
	PublisherService publisherService;
	
	@Test
	void testFindAll_AskFindAllEntities_AllEntitiesShouldBeFound200 () {
		List<PublisherDto> publishers = Instancio.ofList(PublisherDto.class).size(10).create();
		Page<PublisherDto> publishersPage = new PageImpl<>(publishers);
		
		when(publisherService.findAll(any(Pageable.class))).thenReturn(publishersPage);
		
		ResponseEntity<CustomPageImpl<PublisherDto>> responseEntity = restTemplate.exchange(BASE_URL + PORT + "/api/v1/publishers/search/all", HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<PublisherDto>>() {});
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
		
		ResponseEntity<CustomPageImpl<PublisherDto>> responseEntity = restTemplate.exchange(BASE_URL + PORT + "/api/v1/publishers/search/author-name/authorName", HttpMethod.GET , null, new ParameterizedTypeReference<CustomPageImpl<PublisherDto>>() {});
		Page<PublisherDto> publishersPageResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(10, publishersPageResponse.getContent().size());
		assertIterableEquals(publishersPage, publishersPageResponse);
	}
	
	@Test
	void testFindByPublisherName_AskFindEntityByPublisherName_EntityShouldBeFound200() {
		PublisherDto publisherDto = Instancio.create(PublisherDto.class);
		
		when(publisherService.findByPublisherName(any(String.class))).thenReturn(Optional.of(publisherDto));
		
		ResponseEntity<PublisherDto> responseEntity = restTemplate.exchange(BASE_URL + PORT + "/api/v1/publishers/search/publisher-name/publisherName", HttpMethod.GET, null, PublisherDto.class);
		PublisherDto publishersResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(publisherDto, publishersResponse);
	}
	
	@Test
	void testFindByPublisherName_AskFindEntityByPublisherNameIfEntityDoesNotExists_ExceptionShouldBeThrown404() {
		String expectedExceptionMessage = "The publisher with name %s was not found".formatted("publisherName");
		when(publisherService.findByPublisherName(any(String.class))).thenReturn(Optional.empty());
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(BASE_URL + PORT + "/api/v1/publishers/search/publisher-name/publisherName", HttpMethod.GET, null, String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(expectedExceptionMessage, responseEntity.getBody());
	}
	
	@Test
	void testFindByBookTitle_FindEntityByBookTitle_EntityShouldBeReturned200() {
		PublisherDto publisherDto = Instancio.create(PublisherDto.class);
		
		when(publisherService.findByBookTitle(anyString())).thenReturn(Optional.of(publisherDto));
		
		ResponseEntity<PublisherDto> responseEntity = restTemplate.getForEntity(BASE_URL + PORT + "/api/v1/publishers/search/book-title/bookTitle", PublisherDto.class);
		PublisherDto publisherResponse = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(publisherDto, publisherResponse);
	}
	
	@Test
	void testFindByBookTitle_AskFindEntityByBookTitleIfEntityDoesNotExists_ExceptionShouldBeThrown404() {
		when(publisherService.findByBookTitle(anyString())).thenReturn(Optional.empty());
		String expectedExceptionMessage =  "The publisher by book title %s was not found".formatted("bookTitle");
		
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(BASE_URL + PORT + "/api/v1/publishers/search/book-title/bookTitle", String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(expectedExceptionMessage ,responseEntity.getBody());
	}
	
	@Test
	void testFindByIsbn_AskFindEntityByIsbn_EntityShouldBeReturned200() {
		PublisherDto publisherDto = Instancio.create(PublisherDto.class);
		
		when(publisherService.findByIsbn(anyString())).thenReturn(Optional.of(publisherDto));
		
		ResponseEntity<PublisherDto> responseEntity = restTemplate.getForEntity(BASE_URL + PORT + "/api/v1/publishers/search/isbn/isbn", PublisherDto.class);
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
		assertEquals(expectedExceptionMessage ,responseEntity.getBody());
	}
	
	@Test
	void testCreate_AskPostEntity_EntityShouldBeCreatedAndReturned201() throws ServiceException {
		PublisherDto publisherDto = Instancio.create(PublisherDto.class);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		HttpEntity<PublisherDto> request = new HttpEntity<PublisherDto>(publisherDto, headers);
		
		when(publisherService.save(any(PublisherDto.class))).thenReturn(publisherDto);
		
		ResponseEntity<PublisherDto> responseEntity = restTemplate.postForEntity(BASE_URL + PORT + "/api/v1/publishers/creation", request, PublisherDto.class );
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
		
		ResponseEntity<PublisherDto> responseEntity = restTemplate.exchange(BASE_URL + PORT + "/api/v1/publishers/update", HttpMethod.PUT, request, PublisherDto.class);
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
		
		ResponseEntity<Object> responseEntity = restTemplate.exchange(BASE_URL + PORT + "/api/v1/publishers/deletion/101", HttpMethod.DELETE, request, Object.class);
		
		verify(publisherService).findById(anyLong());
		verify(publisherService).deleteById(anyLong());
		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
		assertNull(responseEntity.getBody());
	}
	
	@Test
	void testDeleteById_AskDeleteEntityByIdIfEntityDoesNotExists_ExceptionShouldBeThrown404() {	
		String expectedExceptionMessage = "The publisher with id = %d was deleted".formatted(101);
		when(publisherService.findById(anyLong())).thenReturn(Optional.empty());
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setBearerAuth(token);
		HttpEntity<Object> request = new HttpEntity<Object>(httpHeaders);
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(BASE_URL + PORT + "/api/v1/publishers/deletion/101", HttpMethod.DELETE, request, String.class);
		
		verify(publisherService).findById(anyLong());
		verifyNoMoreInteractions(publisherService);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(expectedExceptionMessage ,responseEntity.getBody());
	}
}
