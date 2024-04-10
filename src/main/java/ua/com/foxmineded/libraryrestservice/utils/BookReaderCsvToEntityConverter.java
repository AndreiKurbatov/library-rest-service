package ua.com.foxmineded.libraryrestservice.utils;

import java.util.Set;

import ua.com.foxmineded.libraryrestservice.entities.impl.BookReader;
import ua.com.foxmineded.libraryrestservice.entities.impl.Location;

import java.util.Map;

public interface BookReaderCsvToEntityConverter {
	void convert(Map<Long, BookReader> bookReaders, Map<Long, Set<Location>> locations);
}
