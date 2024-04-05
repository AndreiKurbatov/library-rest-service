package ua.com.foxmineded.library.utils.impl;

import org.springframework.stereotype.Component;
import ua.com.foxmineded.library.csvbeans.impl.BookRatingCsv;
import ua.com.foxmineded.library.utils.BookRatingCsvImporter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@Component
public class BookRatingCsvImporterImpl extends AbstractCsvImporter<BookRatingCsv, List<BookRatingCsv>> implements BookRatingCsvImporter {
    private static final Path ZIP_FILE_PATH = Paths.get("src", "main", "resources", "data", "book-dataset.zip");
    private static final String INNER_ZIP_FILE = "ratings.csv.zip";
    private static final String INNER_FILE = "ratings.csv";

    public BookRatingCsvImporterImpl() {
        super(ZIP_FILE_PATH, INNER_ZIP_FILE, INNER_FILE, BookRatingCsv::csvBean);
    }

    @Override
    protected List<BookRatingCsv> collect(Stream<BookRatingCsv> stream) {
        return stream.toList();
    }
}
