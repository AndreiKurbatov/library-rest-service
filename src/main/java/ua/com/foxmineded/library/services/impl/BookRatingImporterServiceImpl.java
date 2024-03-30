package ua.com.foxmineded.library.services.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ua.com.foxmineded.library.csvbeans.impl.BookRatingCsv;
import ua.com.foxmineded.library.dao.BookRatingRepository;
import ua.com.foxmineded.library.dao.BookReaderRepository;
import ua.com.foxmineded.library.dao.BookRepository;
import ua.com.foxmineded.library.entities.impl.Book;
import ua.com.foxmineded.library.entities.impl.BookRating;
import ua.com.foxmineded.library.entities.impl.BookReader;
import ua.com.foxmineded.library.services.BookRatingImporterService;
import ua.com.foxmineded.library.utils.BookRatingCsvImporter;

@Service
@RequiredArgsConstructor
public class BookRatingImporterServiceImpl implements BookRatingImporterService {
	private final BookRatingCsvImporter bookRatingCsvImporter;
	private final BookRatingRepository bookRatingRepository;
	private final BookRepository bookRepository;
	private final BookReaderRepository bookReaderRepository;

	@Override
	public void importBookRatings(List<BookRating> bookRatings, Map<Long, BookReader> bookReaders, Map<String, Book> books) {
		List<BookRatingCsv> bookRatingCsvs = bookRatingCsvImporter.read(); 
		for (BookRatingCsv bookRatingCsv : bookRatingCsvs) {
			Long bookReaderId = bookRatingCsv.getBookReaderId();
			String isbn = bookRatingCsv.getIsbn();
			Integer rating = bookRatingCsv.getBookRating();
			
            if (isbn.length()>10) {
                isbn = isbn.substring(0, 10);
            }
            
            if (books.containsKey(isbn) || bookReaders.containsKey(bookReaderId)) {
            	Book book = bookRepository.findByIsbn(isbn).get();
            	BookReader bookReader = bookReaderRepository.findByBookReaderId(bookReaderId).get();
            	BookRating bookRating = new BookRating(null, bookReader, book, rating);
            	bookRatings.add(bookRating);
            }
		}
		bookRatingRepository.saveAll(bookRatings);
		bookRatingRepository.flush();
	}

	@Override
	public Long countAll() {
		return bookRatingRepository.count();
	}
}
