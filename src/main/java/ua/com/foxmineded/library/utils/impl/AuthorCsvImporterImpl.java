package ua.com.foxmineded.library.utils.impl;

import org.springframework.stereotype.Component;
import ua.com.foxmineded.library.csvbeans.impl.AuthorCsv;
import ua.com.foxmineded.library.utils.AuthorCsvImporter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class AuthorCsvImporterImpl extends AbstractCsvImporter<AuthorCsv, Set<AuthorCsv>> implements AuthorCsvImporter {
    private static final Path BOOKS_CSV = Paths.get("src", "main", "resources", "csv", "books.csv");

    public AuthorCsvImporterImpl() {
        super(BOOKS_CSV, AuthorCsv::csvBean);
    }

    @Override
    protected Set<AuthorCsv> collect(Stream<AuthorCsv> stream) {
        return stream.collect(Collectors.toSet());
    }
}
