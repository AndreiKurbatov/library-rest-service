package ua.com.foxmineded.library.utils.impl;

import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;

import lombok.SneakyThrows;
import ua.com.foxmineded.library.csvbeans.impl.BookCsv;
import ua.com.foxmineded.library.csvbeans.impl.LocationCsv;
import ua.com.foxmineded.library.utils.LocationCsvImporter;

@Component
public class LocationCsvImporterImpl extends AbstractCsvImporter<LocationCsv, List<LocationCsv>> implements LocationCsvImporter {
	private static final Path USERS_CSV = Paths.get("src", "main", "resources", "csv", "users.csv");

	public LocationCsvImporterImpl() {
		super(USERS_CSV, LocationCsv::csvBean);
	}

	@Override
	protected List<LocationCsv> collect(Stream<LocationCsv> stream) {
		return stream.distinct().toList();
	}
}
