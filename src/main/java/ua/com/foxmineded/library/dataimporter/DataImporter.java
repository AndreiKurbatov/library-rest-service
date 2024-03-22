package ua.com.foxmineded.library.dataimporter;

import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.com.foxmineded.library.entities.impl.BookRating;
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
	private final LocationImporterService locationImporterService;
	private final BookReaderImporterService bookReaderImporterService;
	private final AuthorImporterService authorImporterService;
	private final PublisherImporterService publisherImporterService;
	private final BookImporterService bookImporterService;
	private final BookRatingImporterService bookRatingImporterService;
	private final ConcurrentDataImporterService concurrentDataImporterService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
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
	}
}
