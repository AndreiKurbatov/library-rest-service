package ua.com.foxmineded.libraryrestservice.dao;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ua.com.foxmineded.libraryrestservice.entities.impl.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
	Optional<Author> findByAuthorName(String name);

	@Query("from Author a join a.books b where b.isbn = :isbn")
	Optional<Author> findByIsbn(String isbn);

	@Query("from Author a join a.books b where b.bookTitle = :bookTitle")
	Optional<Author> findByBookTitle(String bookTitle);

	@Query("from Author a join a.books b join b.publisher p where p.publisherName = :publisherName")
	Page<Author> findAllByPublisherName(Pageable pageable, String publisherName);
}
