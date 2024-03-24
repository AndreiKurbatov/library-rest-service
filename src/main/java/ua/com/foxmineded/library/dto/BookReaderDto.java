package ua.com.foxmineded.library.dto;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
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
	private Long bookReaderId;
	private Set<Long> locationIds;
	private List<Long> bookRatingIds;
	private List<Long> bookIds;
	private Integer age;

	@Builder(setterPrefix = "with")
	@JsonCreator
	public BookReaderDto(Long id, Long bookReaderId, Set<Long> locationIds, List<Long> bookRatingIds, List<Long> bookIds,  Integer age) {
		super(id);
		this.bookReaderId = bookReaderId;
		this.locationIds = locationIds;
		this.bookRatingIds = bookRatingIds;
		this.bookIds = bookIds;
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
				&& Objects.equals(bookRatingIds, bookReaderDto.bookRatingIds)
				&& Objects.equals(bookIds, bookReaderDto.bookIds)
				&& Objects.equals(age, bookReaderDto.age);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, bookReaderId, locationIds, bookRatingIds, bookIds, age);
	}
}
