package ua.com.foxmineded.library.entities;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import ua.com.foxmineded.library.dao.BookRatingRepository;
import ua.com.foxmineded.library.dao.BookReaderRepository;
import ua.com.foxmineded.library.dao.BookRepository;
import ua.com.foxmineded.library.entities.impl.Author;
import ua.com.foxmineded.library.entities.impl.Book;
import ua.com.foxmineded.library.entities.impl.BookRating;
import ua.com.foxmineded.library.entities.impl.BookReader;
import ua.com.foxmineded.library.entities.impl.Location;
import ua.com.foxmineded.library.entities.impl.Publisher;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {BookRepository.class, BookReaderRepository.class, BookRatingRepository.class}))
@AutoConfigureTestDatabase(replace = Replace.NONE) 
@ActiveProfiles("test")
class PersistTest {
	@Autowired
	BookRepository bookRepository;
	@Autowired 
	BookReaderRepository bookReaderRepository;
	@Autowired
	BookRatingRepository bookRatingRepository;

	@Test
	void testPersistEntityByCascade() {
		Book book = Instancio.of(Book.class)
				.ignore(field(Book::getId))
				.ignore(field(Book::getBookRatings)) 
				.ignore(field(Book::getBookReaders))
				.set(field(Book::getIsbn), "1234567890")
				.set(field(Book::getAuthor), new Author(null, "AuthorName", null ))
				.set(field(Book::getPublisher), new Publisher(null, "PublisherName", null)) 
				.create();
		
		List<Book> persistedBooks = bookRepository.saveAll(Stream.of(book).toList());
		bookRepository.flush();
		assertEquals(1, persistedBooks.size());
		Book persistedBook = persistedBooks.get(0);
		assertNotNull(persistedBook.getId());
		assertNotNull(persistedBook.getIsbn());
		assertNotNull(persistedBook.getBookTitle());
		assertNotNull(persistedBook.getAuthor());
		assertNotNull(persistedBook.getAuthor().getId());
		assertEquals("AuthorName", persistedBook.getAuthor().getAuthorName());
		assertNotNull(persistedBook.getAuthor().getBooks());
		assertEquals("1234567890", persistedBook.getAuthor().getBooks().get(0).getIsbn());
		assertNotNull(persistedBook.getPublisher());
		assertNotNull(persistedBook.getPublisher().getId());
		assertEquals("PublisherName", persistedBook.getPublisher().getPublisherName());
		assertNotNull(persistedBook.getPublisher().getBooks());
		assertEquals("1234567890", persistedBook.getPublisher().getBooks().get(0).getIsbn());
		assertNull(persistedBook.getBookRatings());
		assertNull(persistedBook.getBookReaders());
		assertNotNull(persistedBook.getPublicationYear());
		assertNotNull(persistedBook.getImageUrlS());
		assertNotNull(persistedBook.getImageUrlM());
		assertNotNull(persistedBook.getImageUrlL());
		
		BookReader bookReader = Instancio.of(BookReader.class)
				.ignore(field(BookReader::getId))
				.ignore(field(BookReader::getBookRatings))
				.ignore(field(BookReader::getBooks))
				.set(field(BookReader::getBookReaderId), 1L) 
				.set(field(BookReader::getLocations), Stream.of(new Location(null, null, "location1"),
						new Location(null, null, "location2"), new Location(null, null, "location3")).collect(toCollection(HashSet::new)))
				.create();
		
		List<BookReader> persistedBookReaders = bookReaderRepository.saveAll(Stream.of(bookReader).toList());
		bookReaderRepository.flush();
		assertEquals(1, persistedBookReaders.size());
		BookReader persistedBookReader = persistedBookReaders.get(0);
		assertNotNull(persistedBookReader.getId());
		assertNull(persistedBookReader.getBookRatings());
		assertNull(persistedBookReader.getBooks());
		assertNotNull(persistedBookReader.getLocations());
		assertEquals(3, persistedBookReader.getLocations().size());
		for (Location location : persistedBookReader.getLocations()) {
			assertNotNull(location.getBookReader());
			assertEquals(1L, location.getBookReader().getBookReaderId());
			assertNotNull(location.getLocationName());
		}
		
		BookRating bookRating = Instancio.of(BookRating.class) 
				.ignore(field(BookRating::getId))
				.set(field(BookRating::getBookReader), bookReaderRepository.findById(bookReader.getId()).get())
				.set(field(BookRating::getBook), bookRepository.findById(book.getId()).get())
				.create();
		
		List<BookRating> persistedBookRatings = bookRatingRepository.saveAll(Stream.of(bookRating).toList());
		bookRatingRepository.flush();
		assertEquals(1, persistedBookRatings.size());
		BookRating persistedBookRating = persistedBookRatings.get(0);
		assertNotNull(persistedBookRating.getId());
		assertNotNull(persistedBookRating.getBookReader());
		assertEquals(persistedBookReader, persistedBookRating.getBookReader());
		assertNotNull(persistedBookRating.getBook());
		assertEquals(persistedBook, persistedBookRating.getBook());
		
		assertEquals(persistedBook.getBookRatings().get(0), persistedBookRating);
		assertEquals(persistedBookReader.getBookRatings().get(0), persistedBookRating);
	}
}
