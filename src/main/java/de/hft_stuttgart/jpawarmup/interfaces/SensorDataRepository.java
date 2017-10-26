package de.hft_stuttgart.jpawarmup.interfaces;

import org.springframework.data.repository.CrudRepository;
import de.hft_stuttgart.jpawarmup.entities.SensorData;

import java.util.List;

public interface SensorDataRepository<T extends SensorData> extends CrudRepository<T, Long> {

    List<T> findTopByRawName(String rawName);

    List<T> findAllByRawName(String rawName);

    List<T> findAllByRawNameOrderByAppTimestampAsc(String rawName);

    long countByRawName(String rawName);

}
