package ua.com.foxmineded.library.models;

import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.PrePersist;
import lombok.RequiredArgsConstructor;
import ua.com.foxmineded.library.dao.BookReaderRepository;
import ua.com.foxmineded.library.dao.BookRepository;
import ua.com.foxmineded.library.entities.impl.BookRating;

@Component
@RequiredArgsConstructor
public class BookRatingListener {
	private final BookRepository bookRepository;
	private final BookReaderRepository bookReaderRepository;
	
	@Transactional
	@PrePersist
	private void persistDependingEntity(BookRating bookRating) {
		if (Objects.nonNull(bookRating.getBook())) {
			bookRepository.save(bookRating.getBook());
			bookRepository.flush();
		}
		if (Objects.nonNull(bookRating.getBookReader())) {
			bookReaderRepository.save(bookRating.getBookReader());
			bookReaderRepository.flush();
		}
	}
}
