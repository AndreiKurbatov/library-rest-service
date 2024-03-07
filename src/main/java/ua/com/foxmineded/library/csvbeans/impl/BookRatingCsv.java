package ua.com.foxmineded.library.csvbeans.impl;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import lombok.Data;
import ua.com.foxmineded.library.csvbeans.BeanCsv;

@Data
public class BookRatingCsv implements BeanCsv {
	@CsvBindByName(column = "User-ID")
	private Long bookReaderId;
	
	@CsvBindByName(column = "ISBN")
	private String isbn;
	
	@CsvBindByName(column = "Book-Rating")
	private Integer bookRating;
	
	public static CsvToBean<BookRatingCsv> csvBean(CSVReader csvReader) {
		return new CsvToBeanBuilder<BookRatingCsv>(csvReader).withType(BookRatingCsv.class).withSeparator(';')
				.withIgnoreLeadingWhiteSpace(true).withIgnoreEmptyLine(true).withIgnoreQuotations(true).build();
	}
}
