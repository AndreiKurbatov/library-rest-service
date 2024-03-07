package ua.com.foxmineded.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Builder(setterPrefix = "with")
public class BookDto extends AbstractDto<Long> {
	private static final long serialVersionUID = 1L;
	private String isbn;
	private String bookTitle;
	private Long authorId;
	private Long publisherId;
	private int publicationYear;
	
	@Builder(setterPrefix = "with")
	public BookDto(Long id, String isbn, String bookTitle, Long authorId, Long publisherId, int publicationYear) {
		super(id);
		this.isbn = isbn;
		this.bookTitle = bookTitle;
		this.authorId = authorId;
		this.publisherId = publisherId;
		this.publicationYear = publicationYear;
	}
}
