package de.hftstuttgart.projectindoorweb.persistence.repositories;

import de.hftstuttgart.projectindoorweb.persistence.entities.LogFile;
import org.springframework.data.repository.CrudRepository;

public interface LogFileRepository extends CrudRepository<LogFile, Long> {

    LogFile findOne(Long id);

}
