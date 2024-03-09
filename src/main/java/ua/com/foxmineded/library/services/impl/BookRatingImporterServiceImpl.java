package ua.com.foxmineded.library.services.impl;

import java.util.List;
import static java.util.stream.Collectors.toCollection;
import java.util.ArrayList;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ua.com.foxmineded.library.dao.BookRatingRepository;
import ua.com.foxmineded.library.entities.impl.BookRating;
import ua.com.foxmineded.library.services.BookRatingImporterService;
import ua.com.foxmineded.library.utils.BookRatingCsvImporter;

@Service
@RequiredArgsConstructor
public class BookRatingImporterServiceImpl implements BookRatingImporterService {
	private final ModelMapper modelMapper;
	private final BookRatingCsvImporter bookRatingCsvImporter;
	private final BookRatingRepository bookRatingRepository;

	@Override
	public List<BookRating> importBookRatings() {
		List<BookRating> bookRatings = bookRatingCsvImporter.read().stream()
				.map(value -> modelMapper.map(value, BookRating.class)).collect(toCollection(ArrayList::new));
		return bookRatingRepository.saveAll(bookRatings);
	}

	@Override
	public Long countAll() {
		return bookRatingRepository.count();
	}
}
