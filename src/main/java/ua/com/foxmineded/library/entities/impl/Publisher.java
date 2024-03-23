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
@Table(schema = "library", name = "publishers")
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class Publisher extends AbstractEntity<Long> {
	@Column(name = "publisher_name", unique = true, nullable = false)
	private String publisherName;
	@ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "publisher")
	private Set<Book> books;

	public Publisher(Long id, String publisherName, Set<Book> books) {
		super(id);
		this.publisherName = publisherName;
		this.books = books;
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
		if (oEffectiveClass != thisEffectiveClass)
			return false;
		Publisher publisher = (Publisher) o;
		return getId() != null && Objects.equals(getId(), publisher.getId());
	}

	@Override
	public final int hashCode() {
		return this instanceof HibernateProxy
				? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
				: getClass().hashCode();
	}
}
