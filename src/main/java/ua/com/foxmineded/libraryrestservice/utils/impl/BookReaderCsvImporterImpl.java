package ua.com.foxmineded.libraryrestservice.utils.impl;

import org.springframework.stereotype.Component;

import ua.com.foxmineded.libraryrestservice.csvbeans.impl.BookReaderCsv;
import ua.com.foxmineded.libraryrestservice.utils.BookReaderCsvImporter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@Component
public class BookReaderCsvImporterImpl extends AbstractCsvImporter<BookReaderCsv, List<BookReaderCsv>>
		implements BookReaderCsvImporter {
	private static final Path ZIP_FILE_PATH = Paths.get("src", "main", "resources", "data", "book-dataset.zip");
	private static final String INNER_ZIP_FILE = "users.csv.zip";
	private static final String INNER_FILE = "users.csv";

	public BookReaderCsvImporterImpl() {
		super(ZIP_FILE_PATH, INNER_ZIP_FILE, INNER_FILE, BookReaderCsv::csvBean);
	}

	@Override
	protected List<BookReaderCsv> collect(Stream<BookReaderCsv> stream) {
		return stream.toList();
	}
}
