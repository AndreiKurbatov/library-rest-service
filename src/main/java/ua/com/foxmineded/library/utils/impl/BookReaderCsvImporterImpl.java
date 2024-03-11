package ua.com.foxmineded.library.utils.impl;

import org.springframework.stereotype.Component;
import ua.com.foxmineded.library.csvbeans.impl.BookReaderCsv;
import ua.com.foxmineded.library.utils.BookReaderCsvImporter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@Component
public class BookReaderCsvImporterImpl extends AbstractCsvImporter<BookReaderCsv, List<BookReaderCsv>> implements BookReaderCsvImporter {
    private static final Path USERS_CSV = Paths.get("src", "main", "resources", "csv", "users.csv");

    public BookReaderCsvImporterImpl() {
        super(USERS_CSV, BookReaderCsv::csvBean);
    }

    @Override
    protected List<BookReaderCsv> collect(Stream<BookReaderCsv> stream) {
        return stream.toList();
    }
}
