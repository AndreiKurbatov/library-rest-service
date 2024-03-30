package ua.com.foxmineded.library.dataimporter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

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
	private final BookReaderImporterService bookReaderImporterService;
	private final BookImporterService bookImporterService;
	private final BookRatingImporterService bookRatingImporterService;
	private final ConcurrentDataImporterService concurrentDataImporterService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
        Map<Long, Set<Location>> locations = new HashMap<>();
        Map<Long, BookReader> bookReaders = new HashMap<>();
        Map<String, Publisher> publishers = new HashMap<>();
        Map<String, Author> authors = new HashMap<>();
        Map<String, Book> books = new HashMap<>();
        List<BookRating> bookRatings = new ArrayList<>();
        
        bookImporterService.importBooks(books, authors, publishers);
        bookReaderImporterService.importBookReaders(bookReaders, locations);
        bookRatingImporterService.importBookRatings(bookRatings, bookReaders, books);
        
		/*
		log.info("The data import process has started");

		log.info("The process of importing book readers has begun");
		log.info("The process of importing authors has begun");
		log.info("The process of importing publishers has begun");
		concurrentDataImporterService.importConcurrently(bookReaderImporterService::importBookReaders,
				authorImporterService::importAuthors, publisherImporterService::importPublishers);
		log.info("%d book readers were imported".formatted(bookReaderImporterService.countAll()));
		log.info("%d authors were imported".formatted(authorImporterService.countAll()));
		log.info("%d publishers were imported".formatted(publisherImporterService.countAll()));

		log.info("The process of importing locations has begun");
		log.info("The process of importing books has begun");
		concurrentDataImporterService.importConcurrently(locationImporterService::importLocations,
				bookImporterService::importBooks);
		log.info("%d locations were imported".formatted(locationImporterService.countAll()));
		log.info("%d books were imported".formatted(bookImporterService.countAll()));

		log.info("The process of importing book ratings has begun");
		List<BookRating> bookRatings = bookRatingImporterService.importBookRatings();
		log.info("%d book ratings were imported".formatted(bookRatings.size()));

		log.info("The data import process has been completed");
		*/
	}
}
