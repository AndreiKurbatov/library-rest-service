package ua.com.foxmineded.library.utils.impl;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import lombok.Data;
import lombok.SneakyThrows;
import ua.com.foxmineded.library.utils.FileReader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Data
public abstract class AbstractCsvImporter<T, R extends Collection<T>> implements FileReader<R> {
    private final Path zipFilePath;
    private final String innerZipFile;
    private final String innerFile;
    private final Function<CSVReader, CsvToBean<T>> csvToBeanBuilder;

    @SneakyThrows
    @Override
    public R read() {
    	try (InputStream inputStream = Files.newInputStream(zipFilePath);
    			ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
    		ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (entry.getName().equals(innerZipFile)) {
                    return readInnerZip(zipInputStream);
                }
    		}
    	}
    	return null;
    }
    
    @SneakyThrows
    private R readInnerZip(InputStream inputStream) {
    	try (ZipInputStream innerZipInputStream = new ZipInputStream(inputStream)) {
    		ZipEntry innerEntry;
    		while ((innerEntry = innerZipInputStream.getNextEntry()) != null) {
    			if (innerEntry.getName().equals(innerFile)) {
    		        try (CSVReader csvReader = new CSVReaderBuilder(new BufferedReader(new InputStreamReader(innerZipInputStream)))
    		                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
    		                .build()) {
    		            return collect(csvToBeanBuilder.apply(csvReader).stream());
    		        }
    			}
    		}
    	}
    	return null;
    }

    protected abstract R collect(Stream<T> stream);
}
