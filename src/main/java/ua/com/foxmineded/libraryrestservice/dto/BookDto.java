package ua.com.foxmineded.libraryrestservice.dto;

import java.util.List;
import java.util.Objects;
import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
	@NotNull
	@Length(min = 10, max = 10)
	private String isbn;
	@NotBlank
	private String bookTitle;
	@NotBlank
	private String authorName;
	@NotBlank
	private String publisherName;
	private List<Long> bookRatingIds;
	private List<Long> bookReaderIds;
	@Positive
	private Integer publicationYear;
	private String imageUrlS;
	private String imageUrlM;
	private String imageUrlL;

	@Builder(setterPrefix = "with")
	@JsonCreator
	public BookDto(Long id, String isbn, String bookTitle, String authorName, String publisherName,
			Integer publicationYear, List<Long> bookRatingIds, List<Long> bookReaderIds, String imageUrlS,
			String imageUrlM, String imageUrlL) {
		super(id);
		this.isbn = isbn;
		this.bookTitle = bookTitle;
		this.authorName = authorName;
		this.publisherName = publisherName;
		this.bookRatingIds = bookRatingIds;
		this.bookReaderIds = bookReaderIds;
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
				&& Objects.equals(bookTitle, bookDto.bookTitle) && Objects.equals(authorName, bookDto.authorName)
				&& Objects.equals(publisherName, bookDto.publisherName)
				&& Objects.equals(publicationYear, bookDto.publicationYear)
				&& Objects.equals(bookRatingIds, bookDto.bookRatingIds)
				&& Objects.equals(bookReaderIds, bookDto.bookReaderIds) && Objects.equals(imageUrlS, bookDto.imageUrlS)
				&& Objects.equals(imageUrlM, bookDto.imageUrlM) && Objects.equals(imageUrlL, bookDto.imageUrlL);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, isbn, bookTitle, authorName, publisherName, bookRatingIds, bookReaderIds,
				publicationYear, imageUrlS, imageUrlM, imageUrlL);
	}
}
