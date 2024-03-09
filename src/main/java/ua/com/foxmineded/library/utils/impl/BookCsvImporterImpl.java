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
import ua.com.foxmineded.library.csvbeans.impl.BookCsv;
import ua.com.foxmineded.library.utils.BookCsvImporter;

@Component
public class BookCsvImporterImpl implements BookCsvImporter {
	private static final Path BOOKS_CSV = Paths.get("src", "main", "resources", "csv", "books.csv");
	
	@SneakyThrows
	@Override
	public List<BookCsv> read() {
		List<BookCsv> books = new ArrayList<>();
		try (CSVReader csvReader = new CSVReader(new FileReader(BOOKS_CSV.toFile()))) {
			CsvToBean<BookCsv> csvToBean = BookCsv.csvBean(csvReader);
			books = csvToBean.parse();
		}
		
		return books;
	}
}
