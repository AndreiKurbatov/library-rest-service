package ua.com.foxmineded.library.utils.impl;

import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import lombok.SneakyThrows;
import ua.com.foxmineded.library.csvbeans.impl.AuthorCsv;
import ua.com.foxmineded.library.utils.AuthorCsvImporter;

@Component
public class AuthorCsvImporterImpl implements AuthorCsvImporter {
	private static final Path BOOKS_CSV = Paths.get("src", "main", "resources", "csv", "books.csv");

	@SneakyThrows
	@Override
	public Set<AuthorCsv> read() {
		Set<AuthorCsv> authors = new HashSet<>();
		try (CSVReader csvReader = new CSVReader(new FileReader(BOOKS_CSV.toString()))) {
			CsvToBean<AuthorCsv> csvToBean = AuthorCsv.csvBean(csvReader);
			List<AuthorCsv> list = csvToBean.parse();
			authors = new HashSet<>(list);
		}
		return authors;
	}
}
