package ua.com.foxmineded.library.dto;

import java.util.Objects;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString(callSuper = true)
@Builder(setterPrefix = "with")
public class AuthorDto extends AbstractDto<Long> {
	private static final long serialVersionUID = 1L;
	private String authorName;
	private Set<String> locations;

	@Builder(setterPrefix = "with")
	@JsonCreator
	public AuthorDto(@JsonProperty Long id, @JsonProperty String authorName, @JsonProperty Set<String> locations) {
		super(id);
		this.authorName = authorName;
		this.locations = locations;
	}
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    AuthorDto authorDto = (AuthorDto) o;
	    return Objects.equals(id, authorDto.id) &&
	           Objects.equals(authorName, authorDto.authorName) &&
	           Objects.equals(locations, authorDto.locations); 
	}

	@Override
	public int hashCode() {
	    return Objects.hash(id, authorName, locations);
	}
}
