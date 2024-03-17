package ua.com.foxmineded.library.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString(callSuper = true)
@Builder(setterPrefix = "with")
public class BookDto extends AbstractDto<Long> {
	private static final long serialVersionUID = 1L;
	private String isbn;
	private String bookTitle;
	private Long authorId;
	private Long publisherId;
	private int publicationYear;
	
	@Builder(setterPrefix = "with")
	@JsonCreator
	public BookDto( Long id,  String isbn,  String bookTitle,  Long authorId, Long publisherId, int publicationYear) {
		super(id);
		this.isbn = isbn;
		this.bookTitle = bookTitle;
		this.authorId = authorId;
		this.publisherId = publisherId;
		this.publicationYear = publicationYear;
	}
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    BookDto bookDto = (BookDto) o;
	    return Objects.equals(id, bookDto.id) &&
	           Objects.equals(isbn, bookDto.isbn) &&
	           Objects.equals(bookTitle, bookDto.bookTitle) && 
	           Objects.equals(authorId, bookDto.authorId) && 
	           Objects.equals(publisherId, bookDto.publisherId) && 
	           Objects.equals(publicationYear, bookDto.publicationYear);
	}

	@Override
	public int hashCode() {
	    return Objects.hash(id, isbn, bookTitle, authorId, publisherId, publicationYear);
	}
}
