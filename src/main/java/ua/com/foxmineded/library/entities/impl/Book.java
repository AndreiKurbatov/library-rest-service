package ua.com.foxmineded.library.entities.impl;

import java.util.Objects;
import org.hibernate.proxy.HibernateProxy;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.com.foxmineded.library.entities.AbstractEntity;

@Entity
@Table(schema = "library", name = "books")
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class Book extends AbstractEntity<Long> {
	@Column(name = "isbn")
	private String isbn;
	@Column(name = "book_title")
	private String bookTitle;
	@Column(name = "author_name", insertable = false, updatable = false)
	private String authorName;
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "author_name")
	private Author author;
	@Column(name = "publisher_name", insertable = false, updatable = false)
	private String publisherName;
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "publisher_name")
	private Publisher publisher;
	@Column(name = "publication_year")
	private Integer publicationYear;
	@Column(name = "image_url_s")
	private String imageUrlS;
	@Column(name = "image_url_m")
	private String imageUrlM;
	@Column(name = "image_url_l")
	private String imageUrlL;
	
	public Book(Long id, String isbn, String bookTitle, Author author, Publisher publisher, Integer publicationYear,
			String imageUrlS, String imageUrlM, String imageUrlL) {
		super(id);
		this.isbn = isbn;
		this.bookTitle = bookTitle;
		this.author = author;
		this.publisher = publisher;
		this.publicationYear = publicationYear;
		this.imageUrlS = imageUrlS;
		this.imageUrlM = imageUrlM;
		this.imageUrlL = imageUrlL;
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
		Class<?> thisEffectClass = this instanceof HibernateProxy
				? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
				: this.getClass();
		if (oEffectiveClass != thisEffectClass)
			return false;
		Book book = (Book) o;
		return getId() != null && Objects.equals(getId(), book.getId());
	}

	@Override
	public final int hashCode() {
		return this instanceof HibernateProxy
				? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
				: getClass().hashCode();
	}
}
