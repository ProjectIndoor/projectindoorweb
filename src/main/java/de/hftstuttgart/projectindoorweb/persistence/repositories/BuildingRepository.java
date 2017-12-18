package de.hftstuttgart.projectindoorweb.persistence.repositories;

import de.hftstuttgart.projectindoorweb.persistence.entities.Building;
import de.hftstuttgart.projectindoorweb.persistence.entities.Floor;
import org.springframework.data.repository.CrudRepository;

public interface BuildingRepository extends CrudRepository<Building, Long> {

    Building findOne(Long id);

}
