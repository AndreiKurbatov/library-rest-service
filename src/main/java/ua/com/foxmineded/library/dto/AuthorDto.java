package ua.com.foxmineded.library.dto;

import java.util.Objects;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString(callSuper = true)
@Builder(setterPrefix = "with")
public class AuthorDto extends AbstractDto<Long> {
	private static final long serialVersionUID = 1L;
	private String authorName;
	private Set<Long> bookIds;

	@Builder(setterPrefix = "with")
	@JsonCreator
	public AuthorDto(Long id, String authorName, Set<Long> bookIds) {
		super(id);
		this.authorName = authorName;
		this.bookIds = bookIds;
	}
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    AuthorDto authorDto = (AuthorDto) o;
	    return Objects.equals(id, authorDto.id) &&
	           Objects.equals(authorName, authorDto.authorName) &&
	           Objects.equals(bookIds, authorDto.bookIds); 
	}

	@Override
	public int hashCode() {
	    return Objects.hash(id, authorName, bookIds);
	}
}
