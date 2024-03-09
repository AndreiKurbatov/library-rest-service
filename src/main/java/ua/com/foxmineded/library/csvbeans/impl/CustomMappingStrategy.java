package ua.com.foxmineded.library.csvbeans.impl;

import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.exceptions.CsvBadConverterException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class CustomMappingStrategy<T> extends HeaderColumnNameMappingStrategy<T> {
	@SneakyThrows
    @Override
    public T populateNewBean(String[] line) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, CsvBadConverterException {
        try {
            return super.populateNewBean(line);
        } catch (CsvBadConverterException e) {
            log.warn("Skipping line: " + String.join(" ; ", line));
            return null;
        }
    }
}