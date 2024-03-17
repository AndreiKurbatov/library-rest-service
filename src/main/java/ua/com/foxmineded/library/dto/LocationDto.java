package ua.com.foxmineded.library.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString(callSuper = true)
@Builder(setterPrefix = "with")
public class LocationDto extends AbstractDto<Long>{
	private static final long serialVersionUID = 1L;
	private Long bookReaderId;
	private String locationName;
	
	@Builder(setterPrefix = "with")
	@JsonCreator
	public LocationDto(Long id, Long bookReaderId, String locationName) {
		super(id);
		this.bookReaderId = bookReaderId;
		this.locationName = locationName;
	}
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    LocationDto locationDto = (LocationDto) o;
	    return Objects.equals(id, locationDto.id) && 
	    		Objects.equals(bookReaderId, locationDto.bookReaderId) && 
	    		Objects.equals(locationName, locationDto.locationName);
	}

	@Override
	public int hashCode() {
	    return Objects.hash(id, bookReaderId, locationName);
	}
}
