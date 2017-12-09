package de.hftstuttgart.projectindoorweb.persistence.repositories;

import de.hftstuttgart.projectindoorweb.persistence.entities.RadioMap;
import org.springframework.data.repository.CrudRepository;

public interface RadioMapRepository extends CrudRepository<RadioMap, Long> {

    RadioMap findOne(Long id);
}
