package de.hftstuttgart.projectindoorweb.persistence.repositories;

import de.hftstuttgart.projectindoorweb.persistence.entities.Floor;
import org.springframework.data.repository.CrudRepository;

public interface FloorRepository extends CrudRepository<Floor, Long> {

    Floor findOne(Long id);
}
