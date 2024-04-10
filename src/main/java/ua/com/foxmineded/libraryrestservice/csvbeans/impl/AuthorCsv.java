package ua.com.foxmineded.libraryrestservice.csvbeans.impl;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Data;
import ua.com.foxmineded.libraryrestservice.csvbeans.BeanCsv;

@Data
public class AuthorCsv implements BeanCsv {
	@CsvBindByName(column = "Book-Author")
	private String authorName;

	public static CsvToBean<AuthorCsv> csvBean(CSVReader csvReader) {
		return new CsvToBeanBuilder<AuthorCsv>(csvReader).withType(AuthorCsv.class).withSeparator(';').build();
	}
}
