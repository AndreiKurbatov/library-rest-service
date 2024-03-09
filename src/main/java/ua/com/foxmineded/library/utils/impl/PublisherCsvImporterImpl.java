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
import ua.com.foxmineded.library.csvbeans.impl.PublisherCsv;
import ua.com.foxmineded.library.utils.PublisherCsvImporter;

@Component
public class PublisherCsvImporterImpl implements PublisherCsvImporter {
	private static final Path BOOKS_CSV = Paths.get("src", "main", "resources", "csv", "books.csv");

	@SneakyThrows
	@Override
	public List<PublisherCsv> read() {
		List<PublisherCsv> publishers = new ArrayList<>();
		try (CSVReader csvReader = new CSVReader(new FileReader(BOOKS_CSV.toFile()))) {
			CsvToBean<PublisherCsv> csvToBean = PublisherCsv.csvBean(csvReader);
			publishers = csvToBean.parse();
		}
		return publishers;
	}
}
