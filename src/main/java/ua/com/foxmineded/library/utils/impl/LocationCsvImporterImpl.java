package ua.com.foxmineded.library.utils.impl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;
import ua.com.foxmineded.library.csvbeans.impl.LocationCsv;
import ua.com.foxmineded.library.utils.LocationCsvImporter;

@Component
public class LocationCsvImporterImpl extends AbstractCsvImporter<LocationCsv, List<LocationCsv>> implements LocationCsvImporter {
    private static final Path ZIP_FILE_PATH = Paths.get("src", "main", "resources", "data", "book-dataset.zip");
    private static final String INNER_ZIP_FILE = "users.csv.zip";
    private static final String INNER_FILE = "users.csv";
    
	public LocationCsvImporterImpl() {
		super(ZIP_FILE_PATH, INNER_ZIP_FILE, INNER_FILE, LocationCsv::csvBean);
	}

	@Override
	protected List<LocationCsv> collect(Stream<LocationCsv> stream) {
		return stream.distinct().toList();
	}
}
