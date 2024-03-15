package ua.com.foxmineded.library.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxmineded.library.entities.impl.BookReader;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {BookReaderRepository.class}))
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
@Sql(scripts = { "/test/sql/clear_tables.sql" })
class BookReaderRepositoryTest {
	@Autowired
	BookReaderRepository bookReaderRepository;
	
	@Test
	@Sql(scripts = { "/test/sql/book-reader-repository/script-0.sql" })
	void testfindAllByLocationNames_AskFindStudentsIfAllLocationParametersConform_AllBookReadersShouldBeReturned() {
		Set<String> locations = new HashSet<>(Stream.of(new String[]{"location1, location2, location3", "location4"}).toList()); 
		Page<BookReader> bookReaders = bookReaderRepository.findAllByLocationNames(Pageable.ofSize(10), locations);
		assertEquals(1, bookReaders.getContent().size());
		assertEquals(4L, bookReaders.getContent().get(0).getBookReaderId());
	}
	
	@Test
	@Sql(scripts = { "/test/sql/book-reader-repository/script-0.sql" })
	void testfindAllByLocationNames_AskFindStudentsIfFewLocationParametersConform_AllBookReadersShouldBeReturned() {
		
	}
	
	@Test
	@Sql(scripts = { "/test/sql/book-reader-repository/script-0.sql" })
	void testfindAllByLocationNames_AskFindStudentsIfNoLocationParametersConform_NoStudentsShouldBeReturned() {
		
	}
}
