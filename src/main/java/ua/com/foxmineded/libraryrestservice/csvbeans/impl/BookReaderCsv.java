package ua.com.foxmineded.libraryrestservice.csvbeans.impl;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.*;
import ua.com.foxmineded.libraryrestservice.csvbeans.BeanCsv;

@Data
public class BookReaderCsv implements BeanCsv {
	@CsvBindByName(column = "User-ID", required = true)
	@Getter
	private Long bookReaderId;
	@CsvBindByName(column = "Location")
	private String locationName;
	@CsvBindByName(column = "Age")
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private String _rawAge;

	private Integer age;

	public Integer getAge() {
		if (age == null) {
			age = parseAge(_rawAge);
		}
		return age;
	}

	private Integer parseAge(String rawAge) {
		if (rawAge == null || rawAge.isEmpty() || rawAge.equals("NULL")) {
			return null;
		}
		return Integer.parseInt(rawAge);
	}

	public static CsvToBean<BookReaderCsv> csvBean(CSVReader csvReader) {
		return new CsvToBeanBuilder<BookReaderCsv>(csvReader).withType(BookReaderCsv.class).withSeparator(';').build();
	}
}
