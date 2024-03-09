package ua.com.foxmineded.library.utils.impl;

import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;

import lombok.SneakyThrows;
import ua.com.foxmineded.library.csvbeans.impl.LocationCsv;
import ua.com.foxmineded.library.utils.LocationCsvImporter;

@Component
public class LocationCsvImporterImpl implements LocationCsvImporter {
	private static final Path USERS_CSV = Paths.get("src", "main", "resources", "csv", "users.csv");
	
	@SneakyThrows
	@Override
	public List<LocationCsv> read() {
		List<LocationCsv> locations = new ArrayList<>();
		try (CSVReader csvReader = new CSVReader(new FileReader(USERS_CSV.toFile()))) {
			CsvToBean<LocationCsv> csvToBean = LocationCsv.csvBean(csvReader);
			locations = csvToBean.parse();
		}
		return locations;
	}
}
