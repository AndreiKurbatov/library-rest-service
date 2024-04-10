package ua.com.foxmineded.libraryrestservice.dto;

import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class PublisherDto extends AbstractDto<Long> {
	private static final long serialVersionUID = 1L;
	@NotBlank
	private String publisherName;
	private List<Long> bookIds;

	@Builder(setterPrefix = "with")
	@JsonCreator
	public PublisherDto(Long id, String publisherName, List<Long> bookIds) {
		super(id);
		this.publisherName = publisherName;
		this.bookIds = bookIds;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		PublisherDto publisherDto = (PublisherDto) o;
		return Objects.equals(id, publisherDto.id) && Objects.equals(publisherName, publisherDto.publisherName)
				&& Objects.equals(bookIds, publisherDto.bookIds);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, publisherName, bookIds);
	}
}
