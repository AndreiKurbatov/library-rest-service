package ua.com.foxmineded.library.dao;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.foxmineded.library.entities.impl.BookReader;

@Repository
public interface BookReaderRepository extends JpaRepository<BookReader, Long> {
	Optional<BookReader> findByBookReaderId(Long bookReaderId);
	
	Page<BookReader> findAllByAge(Pageable pageable, Integer age);
	
	@Query("from BookReader br where cast(size(:locationNames) as Long) = "
			+ "(select count(distinct loc.locationName)"
			+ "from br.locations loc where loc.locationName in :locationNames)")
	Page<BookReader> findAllByLocationNames(Pageable pageable, Set<String> locationNames);
	
	void deleteByBookReaderId(Long bookReaderId);
}
