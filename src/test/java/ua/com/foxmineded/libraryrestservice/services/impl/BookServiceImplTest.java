package ua.com.foxmineded.libraryrestservice.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.instancio.Instancio;
import static org.instancio.Select.field;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import ua.com.foxmineded.libraryrestservice.config.TypeMapConfig;
import ua.com.foxmineded.libraryrestservice.dao.AuthorRepository;
import ua.com.foxmineded.libraryrestservice.dao.BookRepository;
import ua.com.foxmineded.libraryrestservice.dao.PublisherRepository;
import ua.com.foxmineded.libraryrestservice.dto.BookDto;
import ua.com.foxmineded.libraryrestservice.entities.impl.Book;
import ua.com.foxmineded.libraryrestservice.entities.impl.BookRating;
import ua.com.foxmineded.libraryrestservice.entities.impl.BookReader;
import ua.com.foxmineded.libraryrestservice.entities.impl.Location;
import ua.com.foxmineded.libraryrestservice.exceptions.ServiceException;
import ua.com.foxmineded.libraryrestservice.services.BookService;

@SpringBootTest(classes = { BookServiceImpl.class, TypeMapConfig.class })
class BookServiceImplTest {
	@Autowired
	BookService bookService;
	@MockBean
	BookRepository bookRepository;
	@MockBean
	AuthorRepository authorRepository;
	@MockBean
	PublisherRepository publisherRepository;

	@Test
	void findTop10ByLocationAndAgeRange_AskFindTop10BooksByLocationAndNames_AllBooksShouldBeFound()
			throws ServiceException {
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

		BookReader bookReader0 = new BookReader(1l, 1l, set1, null, 10);
		location0.setBookReader(bookReader0);
		location1.setBookReader(bookReader0);
		BookReader bookReader1 = new BookReader(2l, 2l, set2, null, 20);
		location2.setBookReader(bookReader1);
		location3.setBookReader(bookReader1);
		BookReader bookReader2 = new BookReader(3l, 3l, set3, null, 30);
		location4.setBookReader(bookReader2);
		location5.setBookReader(bookReader2);

		BookReader bookReader3 = new BookReader(4l, 4l, set4, null, 15);
		location6.setBookReader(bookReader3);
		location7.setBookReader(bookReader3);
		BookReader bookReader4 = new BookReader(5l, 5l, set5, null, 25);
		location8.setBookReader(bookReader4);
		location9.setBookReader(bookReader4);
		BookReader bookReader5 = new BookReader(6l, 6l, set6, null, 35);
		location10.setBookReader(bookReader5);
		location11.setBookReader(bookReader5);

		BookRating bookRating0 = new BookRating(1L, bookReader0, null, 1);
		bookReader0.setBookRatings(Stream.of(bookRating0).toList());
		BookRating bookRating1 = new BookRating(2L, bookReader1, null, 7);
		bookReader1.setBookRatings(Stream.of(bookRating1).toList());
		BookRating bookRating2 = new BookRating(3L, bookReader2, null, 10);
		bookReader2.setBookRatings(Stream.of(bookRating2).toList());

		BookRating bookRating3 = new BookRating(4L, bookReader3, null, 1);
		bookReader3.setBookRatings(Stream.of(bookRating3).toList());
		BookRating bookRating4 = new BookRating(5L, bookReader4, null, 7);
		bookReader4.setBookRatings(Stream.of(bookRating4).toList());
		BookRating bookRating5 = new BookRating(6L, bookReader5, null, 10);
		bookReader5.setBookRatings(Stream.of(bookRating5).toList());

		Book book0 = new Book(1L, "isbn1", "book1", null, null, Stream.of(bookRating0).toList(), 2002, "image1",
				"image2", "image3");
		bookRating0.setBook(book0);
		Book book1 = new Book(2L, "isbn2", "book2", null, null, Stream.of(bookRating1).toList(), 2003, "image1",
				"image2", "image3");
		bookRating1.setBook(book1);
		Book book2 = new Book(3L, "isbn3", "book3", null, null, Stream.of(bookRating2).toList(), 2004, "image1",
				"image2", "image3");
		bookRating2.setBook(book2);

		Book book3 = new Book(4L, "isbn4", "book4", null, null, Stream.of(bookRating3).toList(), 2006, "image1",
				"image2", "image3");
		bookRating3.setBook(book3);
		Book book4 = new Book(5L, "isbn5", "book5", null, null, Stream.of(bookRating4).toList(), 2003, "image1",
				"image2", "image3");
		bookRating4.setBook(book4);
		Book book5 = new Book(6L, "isbn6", "book6", null, null, Stream.of(bookRating5).toList(), 2004, "image1",
				"image2", "image3");
		bookRating5.setBook(book5);

		when(bookRepository.findAllByLocationName(any(Pageable.class), anyString()))
				.thenReturn(new PageImpl<>(Stream.of(book0, book1, book3).toList()));
		when(bookRepository.findAllByAgeRange(any(Pageable.class), anyInt(), anyInt()))
				.thenReturn(new PageImpl<>(Stream.of(book2, book0, book4, book5).toList()));

		Page<BookDto> bookDtos = bookService.findTop10ByLocationAndAgeRange(Pageable.ofSize(10), "location1", 5, 15);
		assertEquals(1, bookDtos.getContent().size());
		assertEquals(1, bookDtos.getContent().get(0).getId());
	}

	@Test
	void testFindAllByAgeRange_AskFindBooksByWrongAgeRange_ExceptionShouldBeThrown() {
		String message = "The start age parameter and end age parameter are in the wrong positions";
		Throwable throwable = assertThrows(ServiceException.class, () -> {
			bookService.findAllByAgeRange(PageRequest.of(10, 10), 4, 1);
		});
		assertEquals(message, throwable.getMessage());
	}

	@Test
	void testFindTop10ByLocationAndAgeRange_AskFindBooksByWrongAgeRange_ExceptionShouldBeThrown() {
		String message = "The start age parameter and end age parameter are in the wrong positions";
		Throwable throwable = assertThrows(ServiceException.class, () -> {
			bookService.findTop10ByLocationAndAgeRange(PageRequest.of(10, 10), "location", 4, 1);
		});
		assertEquals(message, throwable.getMessage());
	}

	@Test
	void testCreate_AskCreateBookIfIsbnAlreadyExists_BookShouldBeSaved() {
		Book book = Instancio.create(Book.class);
		BookDto bookDto = Instancio.of(BookDto.class).ignore(field(BookDto::getId)).create();

		bookDto.setIsbn(book.getIsbn());
		String message = "The book with isbn = %s already exists".formatted(book.getIsbn());

		when(bookRepository.findByIsbn(anyString())).thenReturn(Optional.of(book));
		Throwable throwable = assertThrows(ServiceException.class, () -> {
			bookService.save(bookDto);
		});
		assertEquals(message, throwable.getMessage());
	}

	@Test
	void testUpdate_AskUpdateBookIfIsbnAlreadyExists_BookShouldBeSaved() {
		Book book = Instancio.of(Book.class).set(field(Book::getIsbn), "isbn 2").create();
		BookDto bookDto = Instancio.of(BookDto.class).set(field(BookDto::getIsbn), "isbn 1").create();

		String message = "The book isbn cannot be updated".formatted(book.getIsbn());
		when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

		Throwable throwable = assertThrows(ServiceException.class, () -> {
			bookService.save(bookDto);
		});
		assertEquals(message, throwable.getMessage());
	}
}
