package ua.com.foxmineded.libraryrestservice.dao;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.com.foxmineded.libraryrestservice.entities.impl.BookReader;

@Repository
public interface BookReaderRepository extends JpaRepository<BookReader, Long> {
	Optional<BookReader> findByBookReaderId(Long bookReaderId);

	Page<BookReader> findAllByAge(Pageable pageable, Integer age);
}
