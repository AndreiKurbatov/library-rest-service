package ua.com.foxmineded.library.entities.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.hibernate.proxy.HibernateProxy;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostPersist;
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
	@Column(name = "isbn", length = 10, nullable = false, unique = true)
	private String isbn;
	@Column(name = "book_title")
	private String bookTitle;
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "author_name", referencedColumnName = "author_name")
	private Author author;
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "publisher_name", referencedColumnName = "publisher_name")
	private Publisher publisher;
	@ToString.Exclude
	@OneToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE}, mappedBy = "book")
	private List<BookRating> bookRatings;
	@ToString.Exclude
	@ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE})
	@JoinTable(schema = "library", name = "book_readers_books", joinColumns = @JoinColumn(name = "isbn", referencedColumnName = "isbn"), inverseJoinColumns = @JoinColumn(name = "book_reader_id", referencedColumnName = "book_reader_id"))
	private List<BookReader> bookReaders;
	@Column(name = "publication_year")
	private Integer publicationYear;
	@Column(name = "image_url_s")
	private String imageUrlS;
	@Column(name = "image_url_m")
	private String imageUrlM;
	@Column(name = "image_url_l")
	private String imageUrlL;

	public Book(Long id, String isbn, String bookTitle, Author author, Publisher publisher,
			List<BookRating> bookRatings, List<BookReader> bookReaders, Integer publicationYear, String imageUrlS,
			String imageUrlM, String imageUrlL) {
		super(id);
		this.isbn = isbn;
		this.bookTitle = bookTitle;
		this.author = author;
		this.publisher = publisher;
		this.bookRatings = bookRatings;
		this.bookReaders = bookReaders;
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
	
	@PostPersist
	private void assignBookToDependingEntity() {
		if (Objects.isNull(this.getAuthor().getBooks())) {
			this.getAuthor().setBooks(new ArrayList<>());
		}
		if (Objects.isNull(this.getPublisher().getBooks())) {
			this.getPublisher().setBooks(new ArrayList<>());
		}
		this.getAuthor().getBooks().add(this);
		this.getPublisher().getBooks().add(this);
	}
}
