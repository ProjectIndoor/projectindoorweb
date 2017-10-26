package de.hft_stuttgart.jpawarmup.repositories;

import org.springframework.transaction.annotation.Transactional;
import de.hft_stuttgart.jpawarmup.entities.AccelerometerData;

@Transactional
public interface AccelerometerDataRepository extends SensorDataRepository<AccelerometerData> {
}
