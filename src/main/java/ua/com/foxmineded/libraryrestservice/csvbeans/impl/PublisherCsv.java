package ua.com.foxmineded.libraryrestservice.csvbeans.impl;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Data;
import ua.com.foxmineded.libraryrestservice.csvbeans.BeanCsv;

@Data
public class PublisherCsv implements BeanCsv {
	@CsvBindByName(column = "Publisher")
	private String publisherName;

	public static CsvToBean<PublisherCsv> csvBean(CSVReader csvReader) {
		return new CsvToBeanBuilder<PublisherCsv>(csvReader).withType(PublisherCsv.class).withSeparator(';').build();
	}
}
