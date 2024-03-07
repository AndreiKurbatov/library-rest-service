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
	Optional<Book> findByIsbn(String isbn);
	
	Optional<Book> findByBookTitle(String bookTitle);
	
	Page<Book> findAllByPublicationYear(Integer publicationYear, Pageable pageable);
		
	@Query("from Book b join b.author a where a.authorName = :name")
	Page<Book> findAllByAuthorName(String name, Pageable pageable);
	
	@Query("from Book b join b.publisher p where p.publisherName = :name")
	Page<Book> findAllByPublisherName(String name, Pageable pageable);
} 
