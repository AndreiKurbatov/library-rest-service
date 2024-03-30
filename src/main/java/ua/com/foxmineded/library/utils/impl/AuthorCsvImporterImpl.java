package ua.com.foxmineded.library.utils.impl;

import org.springframework.stereotype.Component;
import ua.com.foxmineded.library.csvbeans.impl.AuthorCsv;
import ua.com.foxmineded.library.utils.AuthorCsvImporter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@Component
public class AuthorCsvImporterImpl extends AbstractCsvImporter<AuthorCsv, List<AuthorCsv>> implements AuthorCsvImporter {
    private static final Path ZIP_FILE_PATH = Paths.get("src", "main", "resources", "data", "book-dataset.zip");
    private static final String INNER_ZIP_FILE = "books.csv.zip";
    private static final String INNER_FILE = "books.csv";
    
    public AuthorCsvImporterImpl() {
        super(ZIP_FILE_PATH, INNER_ZIP_FILE, INNER_FILE, AuthorCsv::csvBean);
    }

    @Override
    protected List<AuthorCsv> collect(Stream<AuthorCsv> stream) {
        return stream.distinct().toList();
    }
}
