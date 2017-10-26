package de.hft_stuttgart.jpawarmup.repositories;

import de.hft_stuttgart.jpawarmup.entities.LocalFile;
import org.springframework.data.repository.CrudRepository;

public interface LocalFileRepository<T extends LocalFile> extends CrudRepository<T, String> {

    LocalFile findById(String id);

    long countById(String id);
}
