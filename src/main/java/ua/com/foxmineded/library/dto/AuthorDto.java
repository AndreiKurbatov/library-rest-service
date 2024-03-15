package ua.com.foxmineded.library.dto;

import java.util.Set;

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
public class AuthorDto extends AbstractDto<Long> {
	private static final long serialVersionUID = 1L;
	private String authorName;
	private Set<String> locations;

	@Builder(setterPrefix = "with")
	public AuthorDto(Long id, String authorName, Set<String> locations) {
		super(id);
		this.authorName = authorName;
		this.locations = locations;
	}
}
