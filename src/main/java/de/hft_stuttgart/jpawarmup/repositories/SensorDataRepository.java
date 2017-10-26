package de.hft_stuttgart.jpawarmup.repositories;

import de.hft_stuttgart.jpawarmup.entities.LightData;
import org.springframework.data.repository.CrudRepository;
import de.hft_stuttgart.jpawarmup.entities.SensorData;

import java.util.List;

public interface SensorDataRepository<T extends SensorData> extends CrudRepository<T, Long> {

    List<T> findTopByRawName(String rawName);

    List<T> findAllByRawName(String rawName);

    List<T> findAllByRawNameOrderByAppTimestampAsc(String rawName);

    List<T> findTop10ByLogFileId(String logFileId);

    long countByRawName(String rawName);

}
