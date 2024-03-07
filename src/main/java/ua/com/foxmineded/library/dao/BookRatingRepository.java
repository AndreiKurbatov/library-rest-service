package ua.com.foxmineded.library.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.foxmineded.library.entities.impl.BookRating;

@Repository
public interface BookRatingRepository extends JpaRepository<BookRating, Long> {

}
