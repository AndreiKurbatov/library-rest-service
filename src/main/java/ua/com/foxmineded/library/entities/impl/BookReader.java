package ua.com.foxmineded.library.entities.impl;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.hibernate.proxy.HibernateProxy;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.com.foxmineded.library.entities.AbstractEntity;

@Entity
@Table(schema = "library", name = "book_readers")
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class BookReader extends AbstractEntity<Long> {
	@Column(name = "book_reader_id")
	private Long bookReaderId;
	@ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "bookReader")
	private Set<Location> locations;
	@ToString.Exclude
	@OneToMany(mappedBy = "bookReader")
	private List<BookRating> bookRatings;
	@ToString.Exclude
	@ManyToMany
	@JoinTable(schema = "library", name = "book_readers_books", joinColumns = @JoinColumn(name = "book_reader_id", referencedColumnName = "book_reader_id"), inverseJoinColumns = @JoinColumn(name = "isbn", referencedColumnName = "isbn"))
	private List<Book> books;
	@Column(name = "age")
	private Integer age;

	public BookReader(Long id, Long bookReaderId, Set<Location> locations, List<BookRating> bookRatings,
			List<Book> books, Integer age) {
		super(id);
		this.bookReaderId = bookReaderId;
		this.locations = locations;
		this.bookRatings = bookRatings;
		this.books = books;
		this.age = age;
	}

	@Override
	public final boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		Class<?> oEffectiveClass = o instanceof HibernateProxy
				? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
				: o.getClass();
		Class<?> thisEffectiveClass = this instanceof HibernateProxy
				? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
				: this.getClass();
		if (thisEffectiveClass != oEffectiveClass)
			return false;
		BookReader bookReader = (BookReader) o;
		return getId() != null && Objects.equals(getId(), bookReader.getId());
	}

	@Override
	public final int hashCode() {
		return this instanceof HibernateProxy
				? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
				: getClass().hashCode();
	}
	
	@PostPersist
	private void assignBookReaderToDependingEntity() {
		for (Location location : this.getLocations()) {
			location.setBookReader(this);
		}
	}
}
