package ua.com.foxmineded.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder(setterPrefix = "with")
public class BookReaderDto extends AbstractDto<Long>{
	private static final long serialVersionUID = 1L;
	private Long bookReaderId;
	private Integer age;
	
	@Builder(setterPrefix = "with")
	public BookReaderDto(Long id, Long bookReaderId, Integer age) {
		super(id);
		this.bookReaderId = bookReaderId;
		this.age = age;
	}
}
