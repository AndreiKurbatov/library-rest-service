package ua.com.foxmineded.library.utils.impl;

import org.springframework.stereotype.Component;
import ua.com.foxmineded.library.csvbeans.impl.BookCsv;
import ua.com.foxmineded.library.utils.BookCsvImporter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@Component
public class BookCsvImporterImpl extends AbstractCsvImporter<BookCsv, List<BookCsv>> implements BookCsvImporter {
    private static final Path ZIP_FILE_PATH = Paths.get("src", "main", "resources", "data", "book-dataset.zip");
    private static final String INNER_ZIP_FILE = "books.csv.zip";
    private static final String INNER_FILE = "books.csv";

    public BookCsvImporterImpl() {
        super(ZIP_FILE_PATH, INNER_ZIP_FILE, INNER_FILE, BookCsv::csvBean);
    }

    @Override
    protected List<BookCsv> collect(Stream<BookCsv> stream) {
        return stream.toList();
    }
}
