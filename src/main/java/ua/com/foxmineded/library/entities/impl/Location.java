package ua.com.foxmineded.library.entities.impl;

import java.util.Objects;
import org.hibernate.proxy.HibernateProxy;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.com.foxmineded.library.entities.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(schema = "library", name = "locations")
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class Location extends AbstractEntity<Long> {
	@Column(name = "book_reader_id", updatable = false)
	private Long bookReaderId;
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "book_reader_id")
	private BookReader bookReader;
	@Column(name = "location_name")
	private String locationName;

	public Location(Long id, BookReader bookReader, String locationName) {
		super(id);
		this.bookReader = bookReader;
		this.locationName = locationName;
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
		Location location = (Location) o;
		return getId() != null && Objects.equals(getId(), location.getId());
	}

	@Override
	public final int hashCode() {
		return this instanceof HibernateProxy
				? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
				: getClass().hashCode();
	}
}
