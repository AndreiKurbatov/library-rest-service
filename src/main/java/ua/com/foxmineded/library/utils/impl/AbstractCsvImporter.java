package ua.com.foxmineded.library.utils.impl;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import lombok.Data;
import lombok.SneakyThrows;
import ua.com.foxmineded.library.utils.FileReader;

import java.nio.file.Path;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

@Data
public abstract class AbstractCsvImporter<T, R extends Collection<T>> implements FileReader<R> {
    private final Path csvPath;
    private final Function<CSVReader, CsvToBean<T>> csvToBeanBuilder;

    @SneakyThrows
    @Override
    public R read() {
        try (CSVReader csvReader = new CSVReaderBuilder(new java.io.FileReader(csvPath.toFile()))
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {
            return collect(csvToBeanBuilder.apply(csvReader).stream());
        }
    }

    protected abstract R collect(Stream<T> stream);
}
