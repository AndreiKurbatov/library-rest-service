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
import ua.com.foxmineded.library.csvbeans.impl.BookRatingCsv;
import ua.com.foxmineded.library.utils.BookRatingCsvImporter;

@Component
public class BookRatingCsvImporterImpl implements BookRatingCsvImporter {
	private static final Path RATINGS_CSV = Paths.get("src", "main", "resources", "csv", "ratings.csv");
	
	@SneakyThrows
	@Override
	public List<BookRatingCsv> read() {
		List<BookRatingCsv> bookRatings = new ArrayList<>();
		try (CSVReader csvReader = new CSVReader(new FileReader(RATINGS_CSV.toFile()))) {
			CsvToBean<BookRatingCsv> csvToBean = BookRatingCsv.csvBean(csvReader);
			bookRatings = csvToBean.parse();
		}
		return bookRatings;
	}

}
