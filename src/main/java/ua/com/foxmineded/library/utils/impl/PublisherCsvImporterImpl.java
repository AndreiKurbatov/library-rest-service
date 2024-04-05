package ua.com.foxmineded.library.utils.impl;

import org.springframework.stereotype.Component;
import ua.com.foxmineded.library.csvbeans.impl.PublisherCsv;
import ua.com.foxmineded.library.utils.PublisherCsvImporter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@Component
public class PublisherCsvImporterImpl extends AbstractCsvImporter<PublisherCsv, List<PublisherCsv>> implements PublisherCsvImporter {
    private static final Path ZIP_FILE_PATH = Paths.get("src", "main", "resources", "data", "book-dataset.zip");
    private static final String INNER_ZIP_FILE = "books.csv.zip";
    private static final String INNER_FILE = "books.csv";

    public PublisherCsvImporterImpl() {
        super(ZIP_FILE_PATH, INNER_ZIP_FILE, INNER_FILE, PublisherCsv::csvBean);
    }

    @Override
    protected List<PublisherCsv> collect(Stream<PublisherCsv> stream) {
        return stream.distinct().toList();
    }
}
