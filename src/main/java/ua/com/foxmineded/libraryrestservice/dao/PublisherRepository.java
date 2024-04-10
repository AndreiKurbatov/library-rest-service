package ua.com.foxmineded.libraryrestservice.dao;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ua.com.foxmineded.libraryrestservice.entities.impl.Publisher;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
	Optional<Publisher> findByPublisherName(String name);

	@Query("from Publisher p join p.books b where b.bookTitle = :bookTitle")
	Page<Publisher> findAllByBookTitle(Pageable pageable, String bookTitle);

	@Query("from Publisher p join p.books b where b.isbn = :isbn")
	Optional<Publisher> findByIsbn(String isbn);

	@Query("from Publisher p join p.books b join b.author a where a.authorName = :name")
	Page<Publisher> findAllByAuthorName(Pageable pageable, String name);
}
