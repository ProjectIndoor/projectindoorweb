package de.hft_stuttgart.jpawarmup.repositories;

import org.springframework.transaction.annotation.Transactional;
import de.hft_stuttgart.jpawarmup.entities.MagnetometerData;

@Transactional
public interface MagnetometerDataRepository extends SensorDataRepository<MagnetometerData> {
}
