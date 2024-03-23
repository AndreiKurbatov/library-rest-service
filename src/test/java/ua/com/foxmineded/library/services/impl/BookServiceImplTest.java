package ua.com.foxmineded.library.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import ua.com.foxmineded.library.config.TypeMapConfig;
import ua.com.foxmineded.library.dao.BookRepository;
import ua.com.foxmineded.library.dto.BookDto;
import ua.com.foxmineded.library.entities.impl.Book;
import ua.com.foxmineded.library.entities.impl.BookRating;
import ua.com.foxmineded.library.entities.impl.BookReader;
import ua.com.foxmineded.library.entities.impl.Location;
import ua.com.foxmineded.library.exceptions.ServiceException;
import ua.com.foxmineded.library.services.BookService;

@SpringBootTest(classes = { BookServiceImpl.class, TypeMapConfig.class})
class BookServiceImplTest {
	@Autowired
	BookService bookService;
	@MockBean
	BookRepository bookRepository;


	@Test
	void findTop10ByLocationAndAgeRange_AskFindTop10BooksByLocationAndNames_AllBooksShouldBeFound() {
		Location location0 = new Location(1L, null, "location1");
		Location location1 = new Location(2L, null, "location2");
		Set<Location> set1 = new HashSet<>();
		set1.add(location0);
		set1.add(location1);
		Location location2 = new Location(3L, null, "location1");
		Location location3 = new Location(4L, null, "location2");
		Set<Location> set2 = new HashSet<>();
		set2.add(location2);
		set2.add(location3);
		Location location4 = new Location(5L, null, "location1");
		Location location5 = new Location(6L, null, "location2");
		Set<Location> set3 = new HashSet<>();
		set3.add(location4);
		set3.add(location5);
		
		Location location6 = new Location(7L, null, "location1");
		Location location7 = new Location(8L, null, "location3");
		Set<Location> set4 = new HashSet<>();
		set4.add(location6);
		set4.add(location7);
		Location location8 = new Location(9L, null, "location2");
		Location location9 = new Location(10L, null, "location3");
		Set<Location> set5 = new HashSet<>();
		set5.add(location8);
		set5.add(location9);
		Location location10 = new Location(11L, null, "location4");
		Location location11 = new Location(12L, null, "location2");
		Set<Location> set6 = new HashSet<>();
		set6.add(location10);
		set6.add(location11);
		
		BookReader bookReader0 = new BookReader(1l, 1l, set1, null, null,10);
		location0.setBookReader(bookReader0);
		location1.setBookReader(bookReader0);
		BookReader bookReader1 = new BookReader(2l, 2l, set2, null, null,20);
		location2.setBookReader(bookReader1);
		location3.setBookReader(bookReader1);
		BookReader bookReader2 = new BookReader(3l, 3l, set3, null, null,30);
		location4.setBookReader(bookReader2);
		location5.setBookReader(bookReader2);
		
		BookReader bookReader3 = new BookReader(4l, 4l, set4, null, null,15);
		location6.setBookReader(bookReader3);
		location7.setBookReader(bookReader3);
		BookReader bookReader4 = new BookReader(5l, 5l, set5, null, null,25);
		location8.setBookReader(bookReader4);
		location9.setBookReader(bookReader4);
		BookReader bookReader5 = new BookReader(6l, 6l, set6, null, null,35);
		location10.setBookReader(bookReader5);
		location11.setBookReader(bookReader5);
		
		BookRating bookRating0 = new BookRating(1L, bookReader0, null, 1);
		bookReader0.setBookRatings(Stream.of(bookRating0).toList());
		BookRating bookRating1 = new BookRating(2L, bookReader1, null, 7);
		bookReader1.setBookRatings(Stream.of(bookRating1).toList());
		BookRating bookRating2 = new BookRating(3L, bookReader2, null, 10);
		bookReader2.setBookRatings(Stream.of(bookRating2).toList());
		
		BookRating bookRating3 = new BookRating(1L, bookReader3, null, 1);
		bookReader3.setBookRatings(Stream.of(bookRating3).toList());
		BookRating bookRating4 = new BookRating(2L, bookReader4, null, 7);
		bookReader4.setBookRatings(Stream.of(bookRating4).toList());
		BookRating bookRating5 = new BookRating(3L, bookReader5, null, 10);
		bookReader5.setBookRatings(Stream.of(bookRating5).toList());
		
		Book book0 = new Book(1L, "isbn1", "book1", null, null, 2002, Stream.of(bookRating0).toList(), Stream.of(bookReader0).toList(), "image1", "image2", "image3");
		bookReader0.setBooks(Stream.of(book0).toList());
		bookRating0.setBook(book0);
		Book book1 = new Book(2L, "isbn2", "book2", null, null, 2003, Stream.of(bookRating1).toList(), Stream.of(bookReader1).toList(), "image1", "image2", "image3");
		bookReader1.setBooks(Stream.of(book1).toList());
		bookRating1.setBook(book1);
		Book book2 = new Book(3L, "isbn3", "book3", null, null, 2004, Stream.of(bookRating2).toList(), Stream.of(bookReader2).toList(), "image1", "image2", "image3");
		bookReader2.setBooks(Stream.of(book2).toList());
		bookRating2.setBook(book2);
		
		Book book3 = new Book(4L, "isbn4", "book4", null, null, 2006, Stream.of(bookRating3).toList(), Stream.of(bookReader3).toList(), "image1", "image2", "image3");
		bookReader3.setBooks(Stream.of(book3).toList());
		bookRating3.setBook(book3);
		Book book4 = new Book(5L, "isbn5", "book5", null, null, 2003, Stream.of(bookRating4).toList(), Stream.of(bookReader4).toList(), "image1", "image2", "image3");
		bookReader4.setBooks(Stream.of(book4).toList());
		bookRating4.setBook(book4);
		Book book5 = new Book(6L, "isbn6", "book6", null, null, 2004, Stream.of(bookRating5).toList(), Stream.of(bookReader5).toList(), "image1", "image2", "image3");
		bookReader5.setBooks(Stream.of(book5).toList());
		bookRating5.setBook(book5);
		
		when(bookRepository.findAllByLocationName(any(Pageable.class), anyString())).thenReturn(new PageImpl<>(Stream.of(book0, book1, book3).toList()));
		when(bookRepository.findAllByAgeRange(any(Pageable.class), anyInt(), anyInt())).thenReturn(new PageImpl<>(Stream.of(book2, book0, book4, book5).toList()));

		Page<BookDto> bookDtos = bookService.findTop10ByLocationAndAgeRange(Pageable.ofSize(10), "location1", 10, 15);
		assertEquals(1, bookDtos.getContent().size());
		assertEquals(1 , bookDtos.getContent().get(0).getId());
	}
	
	@Test
	void testSave_AskSaveBookIfIsbnAlreadyExists_BookShouldBeSaved() {
		Book book = Instancio.create(Book.class);
		BookDto bookDto = Instancio.create(BookDto.class);
		bookDto.setIsbn(book.getIsbn());
		String message = "The book with isbn = %s already exists".formatted(book.getIsbn());
		
		when(bookRepository.findByIsbn(anyString())).thenReturn(Optional.of(book));
		Throwable throwable = assertThrows(ServiceException.class, () -> {
			bookService.save(bookDto);
		});
		assertEquals(message, throwable.getMessage());
	}
}
