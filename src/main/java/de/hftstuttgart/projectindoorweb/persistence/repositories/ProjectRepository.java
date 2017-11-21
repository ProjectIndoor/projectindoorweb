package de.hftstuttgart.projectindoorweb.persistence.repositories;

import de.hftstuttgart.projectindoorweb.persistence.entities.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository<Project> extends CrudRepository<Project, Long> {
}
