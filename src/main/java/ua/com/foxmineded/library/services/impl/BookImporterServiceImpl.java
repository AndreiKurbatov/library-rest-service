package ua.com.foxmineded.library.services.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ua.com.foxmineded.library.csvbeans.impl.BookCsv;
import ua.com.foxmineded.library.dao.BookRepository;
import ua.com.foxmineded.library.entities.impl.Author;
import ua.com.foxmineded.library.entities.impl.Book;
import ua.com.foxmineded.library.entities.impl.Publisher;
import ua.com.foxmineded.library.services.BookImporterService;
import ua.com.foxmineded.library.utils.BookCsvImporter;

@Service
@RequiredArgsConstructor
public class BookImporterServiceImpl implements BookImporterService {
	private final BookCsvImporter booksCsvReader;
	private final BookRepository bookRepository;

	@Override
	public void importBooks(Map<String, Book> books, Map<String, Author> authors,
			Map<String, Publisher> publishers) {
		List<BookCsv> bookCsvs = booksCsvReader.read();
		for (BookCsv bookCsv : bookCsvs) {
			String isbn = bookCsv.getIsbn();
			String title = bookCsv.getBookTitle();
			String authorName = bookCsv.getAuthorName();
			Integer year = bookCsv.getYearOfPublication();
			String publisherName = bookCsv.getPublisherName();
			String imgSmall = bookCsv.getImageUrlS();
			String imgMedium = bookCsv.getImageUrlM();
			String imgLarge = bookCsv.getImageUrlL();
			
            if (isbn.length()>10) {
                isbn = isbn.substring(0, 10);
            }

			Author author = new Author(null, authorName, null);
			authors.put(authorName, author);
			Publisher publisher = new Publisher(null, publisherName, null);
			publishers.put(publisherName, publisher);
			Book book = new Book(null, isbn, title, author, publisher, null, null, year, imgSmall, imgMedium, imgLarge);
			books.put(isbn, book);
			
			bookRepository.saveAll(books.values());
			bookRepository.flush();
		}
	}

	@Override
	public Long countAll() {
		return bookRepository.count();
	}
}
