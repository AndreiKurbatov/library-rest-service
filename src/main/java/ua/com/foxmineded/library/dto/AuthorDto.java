package ua.com.foxmineded.library.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.validation.constraints.NotBlank;
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
public class AuthorDto extends AbstractDto<Long> {
	private static final long serialVersionUID = 1L;
	@NotBlank
	private String authorName;

	@Builder(setterPrefix = "with")
	@JsonCreator
	public AuthorDto(Long id, String authorName) {
		super(id);
		this.authorName = authorName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		AuthorDto authorDto = (AuthorDto) o;
		return Objects.equals(id, authorDto.id) && Objects.equals(authorName, authorDto.authorName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, authorName);
	}
}
