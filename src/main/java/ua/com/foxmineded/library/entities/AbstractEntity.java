package ua.com.foxmineded.library.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@ToString
@Getter
@Setter
public abstract class AbstractEntity<T> implements Entity<T> {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
	@SequenceGenerator(name="sequence_generator",schema = "university", sequenceName = "hibernate_sequence", allocationSize=10000)
	protected T id;

	protected AbstractEntity(T id) {
		this.id = id;
	}

	protected AbstractEntity() {
	}
}
