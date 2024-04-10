package ua.com.foxmineded.libraryrestservice.services.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ua.com.foxmineded.libraryrestservice.csvbeans.impl.BookRatingCsv;
import ua.com.foxmineded.libraryrestservice.dao.BookRatingRepository;
import ua.com.foxmineded.libraryrestservice.entities.impl.Book;
import ua.com.foxmineded.libraryrestservice.entities.impl.BookRating;
import ua.com.foxmineded.libraryrestservice.entities.impl.BookReader;
import ua.com.foxmineded.libraryrestservice.services.BookRatingImporterService;
import ua.com.foxmineded.libraryrestservice.utils.BookRatingCsvImporter;

@Service
@RequiredArgsConstructor
public class BookRatingImporterServiceImpl implements BookRatingImporterService {
	private final BookRatingCsvImporter bookRatingCsvImporter;
	private final BookRatingRepository bookRatingRepository;

	@Override
	public void importBookRatings(List<BookRating> bookRatings, Map<Long, BookReader> bookReaders,
			Map<String, Book> books) {
		List<BookRatingCsv> bookRatingCsvs = bookRatingCsvImporter.read();
		for (BookRatingCsv bookRatingCsv : bookRatingCsvs) {
			Long bookReaderId = bookRatingCsv.getBookReaderId();
			String isbn = bookRatingCsv.getIsbn();
			Integer rating = bookRatingCsv.getBookRating();

			if (isbn.length() > 10) {
				isbn = isbn.substring(0, 10);
			}

			Book book;
			BookReader bookReader;
			if (Objects.nonNull(book = books.get(isbn))
					&& Objects.nonNull(bookReader = bookReaders.get(bookReaderId))) {
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
