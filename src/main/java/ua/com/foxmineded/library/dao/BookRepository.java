package ua.com.foxmineded.library.dao;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.foxmineded.library.entities.impl.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	Page<Book> findAllByPublicationYear(Pageable pageable, Integer publicationYear);

	@Query("from Book b join b.author a where a.authorName = :name")
	Page<Book> findAllByAuthorName(Pageable pageable, String name);

	@Query("from Book b join b.publisher p where p.publisherName = :name")
	Page<Book> findAllByPublisherName(Pageable pageable, String name);

	@Query("from Book b join b.bookReaders br group by b.id having avg(br.age) >= :startAge and avg(br.age) <= :endAge")
	Page<Book> findAllByAgeRange(Pageable pageable, Integer startAge, Integer endAge);

	@Query("from Book b where :locationName in (select loc.locationName from b.bookReaders br join br.locations loc)")
	Page<Book> findAllByLocationName(Pageable pageable, String locationName);

	Optional<Book> findByIsbn(String isbn);

	Optional<Book> findByBookTitle(String bookTitle);
}
