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
import ua.com.foxmineded.library.csvbeans.impl.BookReaderCsv;
import ua.com.foxmineded.library.utils.BookReaderCsvImporter;

@Component
public class BookReaderCsvImporterImpl implements BookReaderCsvImporter {
	private static final Path USERS_CSV = Paths.get("src", "main", "resources", "csv", "users.csv");

	@SneakyThrows
	@Override
	public List<BookReaderCsv> read() {
		List<BookReaderCsv> bookReaders = new ArrayList<>();
		try (CSVReader csvReader = new CSVReader(new FileReader(USERS_CSV.toFile()))) {
			CsvToBean<BookReaderCsv> csvToBean = BookReaderCsv.csvBean(csvReader);
			bookReaders = csvToBean.parse();
		}
		return bookReaders;
	}

}
