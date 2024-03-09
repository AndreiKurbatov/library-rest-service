package ua.com.foxmineded.library.entities.impl;

import java.util.Objects;
import java.util.Set;
import org.hibernate.proxy.HibernateProxy;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "bookReader")
	private Set<Location> locations;
	@Column(name = "age")
	private Integer age;
	
	public BookReader(Long id, Long bookReaderId, Set<Location> locations, Integer age) {
		super(id);
		this.bookReaderId = bookReaderId;
		this.locations = locations;
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
}
