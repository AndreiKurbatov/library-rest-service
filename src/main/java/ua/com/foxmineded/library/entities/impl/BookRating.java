package ua.com.foxmineded.library.entities.impl;

import java.util.Objects;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.com.foxmineded.library.entities.AbstractEntity;
import ua.com.foxmineded.library.models.BookRatingListener;

@EntityListeners(BookRatingListener.class)
@Entity
@Table(schema = "library", name = "book_ratings")
@Data
@ToString(callSuper = true)
@NoArgsConstructor
public class BookRating extends AbstractEntity<Long> {
	@ToString.Exclude
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "book_reader_id", referencedColumnName = "book_reader_id")
	private BookReader bookReader;
	@ToString.Exclude
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
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
}
