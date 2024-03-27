package ua.com.foxmineded.library.utils.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import ua.com.foxmineded.library.csvbeans.impl.BookCsv;
import ua.com.foxmineded.library.entities.impl.Author;
import ua.com.foxmineded.library.entities.impl.Book;
import ua.com.foxmineded.library.entities.impl.Publisher;
import ua.com.foxmineded.library.utils.BookCsvImporter;
import ua.com.foxmineded.library.utils.BookCsvToEntityConverter;

@Component
@RequiredArgsConstructor
public class BookCsvToEntityConverterImpl implements BookCsvToEntityConverter {
	private final BookCsvImporter bookCsvImporter;

	@Override
	public void convert(Map<String, Book> books, Map<String, Author> authors, Map<String, Publisher> publishers) {
		List<BookCsv> bookCsvs = bookCsvImporter.read();
		for (BookCsv bookCsv : bookCsvs) {
			String isbn = bookCsv.getIsbn();
			String bookTitle = bookCsv.getBookTitle();
			String authorName = bookCsv.getAuthorName();
			String publisherName = bookCsv.getPublisherName();
			Integer publicationYear = bookCsv.getYearOfPublication();
			String imageUrlS = bookCsv.getImageUrlS();
			String imageUrlM = bookCsv.getImageUrlM();
			String imageUrlL = bookCsv.getImageUrlL();
			
			Author author = new Author(null, authorName, null);
			Publisher publisher = new Publisher(null, publisherName, null);
			Book book = new Book(null, isbn, bookTitle, author, publisher, null, null, publicationYear , imageUrlS, imageUrlM, imageUrlL);
			
			authors.put(authorName, author);
			publishers.put(publisherName, publisher);
			books.put(isbn, book);
		}
	}
}
