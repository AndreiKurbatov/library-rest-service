package ua.com.foxmineded.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true) 
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class PublisherDto extends AbstractDto<Long> {
	private static final long serialVersionUID = 1L;
	private String publisherName;
	
	@Builder(setterPrefix = "with")
	public PublisherDto(Long id, String publisherName) {
		super(id);
		this.publisherName = publisherName;
	}
}
