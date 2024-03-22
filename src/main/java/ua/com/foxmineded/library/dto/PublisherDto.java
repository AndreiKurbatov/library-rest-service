package ua.com.foxmineded.library.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonCreator;
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
	private String publisherName;

	@Builder(setterPrefix = "with")
	@JsonCreator
	public PublisherDto(Long id, String publisherName) {
		super(id);
		this.publisherName = publisherName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		PublisherDto publisherDto = (PublisherDto) o;
		return Objects.equals(id, publisherDto.id) && Objects.equals(publisherName, publisherDto.publisherName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, publisherName);
	}
}
