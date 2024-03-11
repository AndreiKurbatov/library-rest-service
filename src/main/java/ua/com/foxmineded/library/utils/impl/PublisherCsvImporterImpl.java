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
    private static final Path BOOKS_CSV = Paths.get("src", "main", "resources", "csv", "books.csv");

    public PublisherCsvImporterImpl() {
        super(BOOKS_CSV, PublisherCsv::csvBean);
    }

    @Override
    protected List<PublisherCsv> collect(Stream<PublisherCsv> stream) {
        return stream.distinct().toList();
    }
}
