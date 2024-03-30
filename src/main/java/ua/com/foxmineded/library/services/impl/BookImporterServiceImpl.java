package ua.com.foxmineded.library.services.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ua.com.foxmineded.library.csvbeans.impl.AuthorCsv;
import ua.com.foxmineded.library.csvbeans.impl.BookCsv;
import ua.com.foxmineded.library.csvbeans.impl.PublisherCsv;
import ua.com.foxmineded.library.dao.AuthorRepository;
import ua.com.foxmineded.library.dao.BookReaderRepository;
import ua.com.foxmineded.library.dao.BookRepository;
import ua.com.foxmineded.library.dao.PublisherRepository;
import ua.com.foxmineded.library.entities.impl.Author;
import ua.com.foxmineded.library.entities.impl.Book;
import ua.com.foxmineded.library.entities.impl.BookRating;
import ua.com.foxmineded.library.entities.impl.BookReader;
import ua.com.foxmineded.library.entities.impl.Publisher;
import ua.com.foxmineded.library.services.BookImporterService;
import ua.com.foxmineded.library.utils.AuthorCsvImporter;
import ua.com.foxmineded.library.utils.BookCsvImporter;
import ua.com.foxmineded.library.utils.PublisherCsvImporter;

@Service
@RequiredArgsConstructor
public class BookImporterServiceImpl implements BookImporterService {
	private final BookCsvImporter booksCsvReader;
	private final AuthorCsvImporter authorCsvImporter;
	private final PublisherCsvImporter publisherCsvImporter;
	
	private final BookRepository bookRepository;
	private final BookReaderRepository bookReaderRepository;
	private final AuthorRepository authorRepository;
	private final PublisherRepository publisherRepository;

	@Override
	public void importBooks(Map<String, Book> books, Map<String, Author> authors,
			Map<String, Publisher> publishers) {
		
		List<PublisherCsv> publisherCsvs = publisherCsvImporter.read();
		for (PublisherCsv publisherCsv : publisherCsvs) {
			String publisherName = publisherCsv.getPublisherName();
			Publisher publisher = new Publisher(null, publisherName, null);
			publishers.put(publisherName, publisher);
		}
		publisherRepository.saveAll(publishers.values());
		publisherRepository.flush();
		
		List<AuthorCsv> authorCsvs = authorCsvImporter.read();
		for (AuthorCsv authorCsv : authorCsvs) {
			String authorName = authorCsv.getAuthorName();
			Author author = new Author(null, authorName, null);
			authors.put(authorName, author);
		}
		authorRepository.saveAll(authors.values());
		authorRepository.flush();
        
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

			Author author = authors.get(authorName);
			Publisher publisher = publishers.get(publisherName);
			Book book = new Book(null, isbn, title, author, publisher, null, null, year, imgSmall, imgMedium, imgLarge);
			books.put(isbn, book);
		}
		bookRepository.saveAll(books.values());
		bookRepository.flush();
	}
	
	@Override
	public void createBookToBookReaderRelationship(List<BookRating> bookRatings, Map<String, Book> books, Map<Long, BookReader> bookReaders) {
		for (BookRating bookRating : bookRatings) {
			String isbn = bookRating.getBook().getIsbn();
			Long bookReaderId = bookRating.getBookReader().getBookReaderId();
			Book book = books.get(isbn);
			BookReader bookReader = bookReaders.get(bookReaderId);
			book.getBookReaders().add(bookReader);
			bookReader.getBooks().add(book);
		}
		bookRepository.saveAll(books.values());
		bookRepository.flush();
		bookReaderRepository.saveAll(bookReaders.values());
		bookReaderRepository.flush();
	}

	@Override
	public Long countAll() {
		return bookRepository.count();
	}
}
