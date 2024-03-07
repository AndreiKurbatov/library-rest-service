package ua.com.foxmineded.library.services.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ua.com.foxmineded.universitycms.dao.BookRatingRepository;
import ua.com.foxmineded.universitycms.entities.impl.BookRating;
import ua.com.foxmineded.universitycms.services.BookRatingGeneratorService;
import ua.com.foxmineded.universitycms.utils.impl.BookRatingsCsvReaderImpl;

@Service
@RequiredArgsConstructor
public class BookRatingGeneratorServiceImpl implements BookRatingGeneratorService {
	private final BookRatingsCsvReaderImpl csvReaderImpl;
	private final BookRatingRepository bookRatingRepository;

	@Override
	public List<BookRating> generateBookRatings() {
		List<BookRating> bookRatings = csvReaderImpl.readCsv();
		return bookRatingRepository.saveAll(bookRatings);
	}

	@Override
	public Long countAll() {
		return bookRatingRepository.count();
	}
}
