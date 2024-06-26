package ua.com.foxmineded.libraryrestservice.dto;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;

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
public class BookReaderDto extends AbstractDto<Long> {
	private static final long serialVersionUID = 1L;
	@NotNull
	private Long bookReaderId;
	private Set<Long> locationIds;
	private List<Long> bookRatingIds;
	@Positive
	private Integer age;

	@Builder(setterPrefix = "with")
	@JsonCreator
	public BookReaderDto(Long id, Long bookReaderId, Set<Long> locationIds, List<Long> bookRatingIds, Integer age) {
		super(id);
		this.bookReaderId = bookReaderId;
		this.locationIds = locationIds;
		this.bookRatingIds = bookRatingIds;
		this.age = age;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		BookReaderDto bookReaderDto = (BookReaderDto) o;
		return Objects.equals(id, bookReaderDto.id) && Objects.equals(bookReaderId, bookReaderDto.bookReaderId)
				&& Objects.equals(locationIds, bookReaderDto.locationIds)
				&& Objects.equals(bookRatingIds, bookReaderDto.bookRatingIds) && Objects.equals(age, bookReaderDto.age);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, bookReaderId, locationIds, bookRatingIds, age);
	}
}
