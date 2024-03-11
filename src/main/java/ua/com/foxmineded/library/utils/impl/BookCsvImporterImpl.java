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
    private static final Path BOOKS_CSV = Paths.get("src", "main", "resources", "csv", "books.csv");

    public BookCsvImporterImpl() {
        super(BOOKS_CSV, BookCsv::csvBean);
    }

    @Override
    protected List<BookCsv> collect(Stream<BookCsv> stream) {
        return stream.toList();
    }
}
