package ua.com.foxmineded.library.csvbeans.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ua.com.foxmineded.library.config.TypeMapConfig;
import ua.com.foxmineded.library.entities.impl.Book;

@SpringBootTest(classes = {Book.class, BookCsv.class, TypeMapConfig.class})
class BookCsvTest {
	@Autowired
	ModelMapper modelMapper;
	
	@Disabled
	@Test
	void testMapBookCsvToBook() {
		BookCsv bookCsv = new BookCsv();
		bookCsv.setIsbn("1111122222");
		bookCsv.setBookTitle("book title");
		bookCsv.setAuthorName("book author");
		bookCsv.setYearOfPublication(1983);
		bookCsv.setPublisherName("publisher");
		bookCsv.setImageUrlS("http:/blablaS");
		bookCsv.setImageUrlM("http:/blablaM");
		bookCsv.setImageUrlL("http:/blablaL");
		
		Book book = modelMapper.map(bookCsv, Book.class);
		assertNull(book.getId());
		assertNull(book.getAuthor().getId());
		assertNull(book.getPublisher().getId());
		assertEquals(bookCsv.getAuthorName(), book.getAuthor().getAuthorName());
		assertEquals(bookCsv.getPublisherName(), book.getPublisher().getPublisherName());
		assertEquals(bookCsv.getIsbn(), book.getIsbn());
		assertEquals(bookCsv.getBookTitle(), book.getBookTitle());
		assertEquals(bookCsv.getAuthorName(), book.getAuthor().getAuthorName());
		assertEquals(bookCsv.getYearOfPublication(), book.getPublicationYear());
		assertEquals(bookCsv.getPublisherName(), book.getPublisher().getPublisherName());
		assertEquals(bookCsv.getImageUrlS(), book.getImageUrlS());
		assertEquals(bookCsv.getImageUrlM(), book.getImageUrlM());
		assertEquals(bookCsv.getImageUrlL(), book.getImageUrlL());
	}
}
