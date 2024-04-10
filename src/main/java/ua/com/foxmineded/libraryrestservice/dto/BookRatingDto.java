package ua.com.foxmineded.libraryrestservice.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString(callSuper = true)
@Builder(setterPrefix = "with")
@NoArgsConstructor
public class BookRatingDto extends AbstractDto<Long> {
	private static final long serialVersionUID = 1L;
	private Long bookReaderId;
	private Long bookId;
	@Min(value = 0)
	@Max(value = 10)
	private Integer rating;

	@Builder(setterPrefix = "with")
	@JsonCreator
	public BookRatingDto(Long id, Long bookReaderId, Long bookId, Integer rating) {
		super(id);
		this.bookReaderId = bookReaderId;
		this.bookId = bookId;
		this.rating = rating;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		BookRatingDto bookRatingDto = (BookRatingDto) o;
		return Objects.equals(id, bookRatingDto.id) && Objects.equals(bookReaderId, bookRatingDto.bookReaderId)
				&& Objects.equals(bookId, bookRatingDto.bookId) && Objects.equals(rating, bookRatingDto.rating);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, bookReaderId, bookId, rating);
	}
}
