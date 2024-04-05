package ua.com.foxmineded.library.csvbeans.impl;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Data;
import ua.com.foxmineded.library.csvbeans.BeanCsv;

@Data
public class LocationCsv implements BeanCsv{
	@CsvBindByName(column = "User-ID")
	private Long bookReaderId;

	@CsvBindByName(column = "Location")
	private String locationName;

	public static CsvToBean<LocationCsv> csvBean(CSVReader csvReader) {
		return new CsvToBeanBuilder<LocationCsv>(csvReader)
				.withType(LocationCsv.class)
				.withSeparator(';')
				.build();
	}
}
