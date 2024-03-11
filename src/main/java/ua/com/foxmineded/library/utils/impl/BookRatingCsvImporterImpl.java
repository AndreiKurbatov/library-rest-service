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
    private static final Path RATINGS_CSV = Paths.get("src", "main", "resources", "csv", "ratings.csv");

    public BookRatingCsvImporterImpl() {
        super(RATINGS_CSV, BookRatingCsv::csvBean);
    }

    @Override
    protected List<BookRatingCsv> collect(Stream<BookRatingCsv> stream) {
        return stream.toList();
    }
}
