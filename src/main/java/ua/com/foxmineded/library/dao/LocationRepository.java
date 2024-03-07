package ua.com.foxmineded.library.dao;

import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.foxmineded.library.entities.impl.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
	@Query("from Location l WHERE l.locationName in :locationName")
	Optional<Location> findByLocationName(Set<String> locationName);
}
