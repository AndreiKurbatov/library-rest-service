package ua.com.foxmineded.libraryrestservice.utils;

import java.util.List;

import ua.com.foxmineded.libraryrestservice.csvbeans.impl.BookCsv;

public interface BookCsvImporter extends FileReader<List<BookCsv>> {

}
