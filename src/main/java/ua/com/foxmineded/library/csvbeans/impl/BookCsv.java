package ua.com.foxmineded.library.csvbeans.impl;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Data;
import ua.com.foxmineded.library.csvbeans.BeanCsv;

@Data
public class BookCsv implements BeanCsv{
	@CsvBindByName(column = "ISBN")
	private String isbn;

	@CsvBindByName(column = "Book-Title")
	private String bookTitle;

	@CsvBindByName(column = "Book-Author")
	private String bookAuthor;

	@CsvBindByName(column = "Year-Of-Publication")
	private Integer yearOfPublication;

	@CsvBindByName(column = "Publisher")
	private String publisher;

	@CsvBindByName(column = "Image-URL-S")
	private String imageUrlS;

	@CsvBindByName(column = "Image-URL-M")
	private String imageUrlM;

	@CsvBindByName(column = "Image-URL-L")
	private String imageUrlL;

	public static CsvToBean<BookCsv> csvBean(CSVReader csvReader) {
		return new CsvToBeanBuilder<BookCsv>(csvReader).withType(BookCsv.class).withSeparator(';')
				.withIgnoreLeadingWhiteSpace(true).withIgnoreEmptyLine(true).withIgnoreQuotations(true).build();
	}
}
