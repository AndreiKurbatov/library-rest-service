package ua.com.foxmineded.library.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.foxmineded.library.entities.impl.BookRating;

@Repository
public interface BookRatingRepository extends JpaRepository<BookRating, Long> {
	@Query("from BookRating br where br.book.id = :id ")
	Page<BookRating> findAllByBookId(Pageable pageable, Long id);

	@Query("from BookRating br where br.bookReader.id = :bookReaderId and br.book.id = :bookId")
	Optional<BookRating> findByBookReaderIdAndBookId(Long bookReaderId, Long bookId);
}
