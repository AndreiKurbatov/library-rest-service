package ua.com.foxmineded.libraryrestservice.entities.impl;

import java.util.ArrayList;
import java.util.Objects;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.com.foxmineded.libraryrestservice.entities.AbstractEntity;

@Entity
@Table(schema = "library", name = "book_ratings")
@Data
@ToString(callSuper = true)
@NoArgsConstructor
public class BookRating extends AbstractEntity<Long> {
	@ToString.Exclude
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "book_reader_id", referencedColumnName = "book_reader_id")
	private BookReader bookReader;
	@ToString.Exclude
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "isbn", referencedColumnName = "isbn")
	private Book book;
	@Column(name = "book_rating")
	private Integer rating;

	public BookRating(Long id, BookReader bookReader, Book book, Integer bookRating) {
		super(id);
		this.bookReader = bookReader;
		this.book = book;
		this.rating = bookRating;
	}

	@Override
	public final boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		Class<?> oEffectiveClass = o instanceof HibernateProperties
				? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
				: o.getClass();
		Class<?> thisEffectiveClass = this instanceof HibernateProxy
				? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
				: this.getClass();
		if (oEffectiveClass != thisEffectiveClass)
			return false;
		BookRating bookRating = (BookRating) o;
		return getId() != null && Objects.equals(getId(), bookRating.getId());
	}

	@Override
	public final int hashCode() {
		return this instanceof HibernateProxy
				? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
				: getClass().hashCode();
	}

	@PostPersist
	private void assignBookRatingToRelatedEntity() {
		if (Objects.isNull(book.getBookRatings())) {
			book.setBookRatings(new ArrayList<>());
		}
		if (Objects.isNull(bookReader.getBookRatings())) {
			bookReader.setBookRatings(new ArrayList<>());
		}
		book.getBookRatings().add(this);
		bookReader.getBookRatings().add(this);
	}
}
