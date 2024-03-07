package ua.com.foxmineded.library.csvbeans.impl;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Data;
import ua.com.foxmineded.library.csvbeans.BeanCsv;

@Data
public class AuthorCsv implements BeanCsv {
	@CsvBindByName(column = "Book-Author")
	private String bookAuthor;

	public static CsvToBean<AuthorCsv> csvBean(CSVReader csvReader) {
		return new CsvToBeanBuilder<AuthorCsv>(csvReader)
				.withType(AuthorCsv.class)
				.withSeparator(';')
				.withIgnoreLeadingWhiteSpace(true)
				.withIgnoreEmptyLine(true)
				.withIgnoreQuotations(true)
				.build();
	}
}
