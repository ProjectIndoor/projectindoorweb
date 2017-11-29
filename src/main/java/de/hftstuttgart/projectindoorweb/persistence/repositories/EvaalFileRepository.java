package de.hftstuttgart.projectindoorweb.persistence.repositories;

import de.hftstuttgart.projectindoorweb.persistence.entities.Building;
import de.hftstuttgart.projectindoorweb.persistence.entities.EvaalFile;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EvaalFileRepository extends CrudRepository<EvaalFile, Long> {

    EvaalFile findOne(Long id);

    EvaalFile findBySourceFileName(String sourceFileName);

    List<EvaalFile> findByRecordedInBuildingAndAndEvaluationFileTrue(Building building);

    List<EvaalFile> findByRecordedInBuildingAndEvaluationFileFalse(Building building);

}
