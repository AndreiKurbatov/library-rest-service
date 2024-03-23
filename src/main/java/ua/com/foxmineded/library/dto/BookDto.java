package ua.com.foxmineded.library.dto;

import java.util.Objects;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Builder(setterPrefix = "with")
public class BookDto extends AbstractDto<Long> {
	private static final long serialVersionUID = 1L;
	@NotBlank
	@Length(min = 10, max = 10)
	private String isbn;
	private String bookTitle;
	private Long authorId;
	private Long publisherId;
	@Positive
	private Integer publicationYear;
	private String imageUrlS;
	private String imageUrlM;
	private String imageUrlL;

	@Builder(setterPrefix = "with")
	@JsonCreator
	public BookDto(Long id, String isbn, String bookTitle, Long authorId, Long publisherId,

			Integer publicationYear, String imageUrlS, String imageUrlM, String imageUrlL) {
		super(id);
		this.isbn = isbn;
		this.bookTitle = bookTitle;
		this.authorId = authorId;
		this.publisherId = publisherId;
		this.publicationYear = publicationYear;
		this.imageUrlS = imageUrlS;
		this.imageUrlM = imageUrlM;
		this.imageUrlL = imageUrlL;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		BookDto bookDto = (BookDto) o;
		return Objects.equals(id, bookDto.id) && Objects.equals(isbn, bookDto.isbn)
				&& Objects.equals(bookTitle, bookDto.bookTitle) && Objects.equals(authorId, bookDto.authorId)
				&& Objects.equals(publisherId, bookDto.publisherId)
				&& Objects.equals(publicationYear, bookDto.publicationYear)
				&& Objects.equals(imageUrlS, bookDto.imageUrlS) && Objects.equals(imageUrlM, bookDto.imageUrlM)
				&& Objects.equals(imageUrlL, bookDto.imageUrlL);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, isbn, bookTitle, authorId, publisherId, publicationYear, imageUrlS, imageUrlM,
				imageUrlL);
	}
}
