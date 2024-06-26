package ua.com.foxmineded.libraryrestservice.entities.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.hibernate.proxy.HibernateProxy;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.com.foxmineded.libraryrestservice.entities.AbstractEntity;

@Entity
@Table(schema = "library", name = "book_readers")
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class BookReader extends AbstractEntity<Long> {
	@Column(name = "book_reader_id")
	private Long bookReaderId;
	@ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "bookReader")
	private Set<Location> locations;
	@ToString.Exclude
	@OneToMany(mappedBy = "bookReader", cascade = CascadeType.REMOVE)
	private List<BookRating> bookRatings;
	@Column(name = "age")
	private Integer age;

	public BookReader(Long id, Long bookReaderId, Set<Location> locations, List<BookRating> bookRatings, Integer age) {
		super(id);
		this.bookReaderId = bookReaderId;
		this.locations = locations;
		this.bookRatings = bookRatings;
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
		for (Location location : Objects.nonNull(this.getLocations()) ? this.getLocations() : new HashSet<Location>()) {
			location.setBookReader(this);
		}
	}
}
