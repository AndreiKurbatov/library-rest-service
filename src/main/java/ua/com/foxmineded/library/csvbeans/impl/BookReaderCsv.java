package ua.com.foxmineded.library.csvbeans.impl;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Data;
import ua.com.foxmineded.library.csvbeans.BeanCsv;

@Data
public class BookReaderCsv implements BeanCsv {
	@CsvBindByName(column = "User-ID")
	private Long bookReaderId;
	
	@CsvBindByName(column = "Age")
	private Integer age;
	
	public static CsvToBean<BookReaderCsv> csvBean(CSVReader csvReader) {
		return new CsvToBeanBuilder<BookReaderCsv>(csvReader)  
				.withType(BookReaderCsv.class) 
				.withSeparator(';') 
				.withIgnoreLeadingWhiteSpace(true)
				.withIgnoreEmptyLine(true)
				.withIgnoreQuotations(true)
				.build();
				
	}	
}
