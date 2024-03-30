package ua.com.foxmineded.library.dataimporter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.com.foxmineded.library.entities.impl.Author;
import ua.com.foxmineded.library.entities.impl.Book;
import ua.com.foxmineded.library.entities.impl.BookRating;
import ua.com.foxmineded.library.entities.impl.BookReader;
import ua.com.foxmineded.library.entities.impl.Location;
import ua.com.foxmineded.library.entities.impl.Publisher;
import ua.com.foxmineded.library.services.AuthorImporterService;
import ua.com.foxmineded.library.services.BookImporterService;
import ua.com.foxmineded.library.services.BookRatingImporterService;
import ua.com.foxmineded.library.services.BookReaderImporterService;
import ua.com.foxmineded.library.services.ConcurrentDataImporterService;
import ua.com.foxmineded.library.services.LocationImporterService;
import ua.com.foxmineded.library.services.PublisherImporterService;

@Profile("!test")
@Component
@RequiredArgsConstructor
@Slf4j
public class DataImporter implements ApplicationRunner {
	private final PublisherImporterService publisherImporterService;
	private final AuthorImporterService authorImporterService;
	private final BookReaderImporterService bookReaderImporterService;
	private final BookImporterService bookImporterService;
	private final BookRatingImporterService bookRatingImporterService;
	private final ConcurrentDataImporterService concurrentDataImporterService;
	private final LocationImporterService locationImporterService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
        Map<Long, Set<Location>> locations = new HashMap<>();
        Map<Long, BookReader> bookReaders = new HashMap<>();
        Map<String, Publisher> publishers = new HashMap<>();
        Map<String, Author> authors = new HashMap<>();
        Map<String, Book> books = new HashMap<>();
        List<BookRating> bookRatings = new ArrayList<>();

		log.info("The process of importing publishers has begun");
		log.info("The process of importing book readers and locations has begun");
		concurrentDataImporterService.importConcurrently(
				() -> bookImporterService.importBooks(books, authors, publishers),
				() -> bookReaderImporterService.importBookReaders(bookReaders, locations)
				);
		log.info("%d books were imported".formatted(books.size()));
		log.info("%d book readers were imported".formatted(bookReaders.size()));
		log.info("%d locations were imported".formatted(locationImporterService.countAll()));
		log.info("%d publishers were imported".formatted(publisherImporterService.countAll()));
		log.info("%d authors were imported".formatted(authorImporterService.countAll()));
		
		log.info("The process of importing book ratings has begun");
		bookRatingImporterService.importBookRatings(bookRatings, bookReaders, books);
		log.info("%d book ratings were imported".formatted(bookRatings.size()));
	
		log.info("The process of creating the relationship between books and book readers has begun");
		bookImporterService.createBookToBookReaderRelationship(bookRatings, books, bookReaders);
		log.info("The relationship between books and book readers has finished");
	}
}
