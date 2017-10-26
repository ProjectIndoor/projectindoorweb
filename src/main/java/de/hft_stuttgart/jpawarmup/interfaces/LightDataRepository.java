package de.hft_stuttgart.jpawarmup.interfaces;

import de.hft_stuttgart.jpawarmup.entities.LightData;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface LightDataRepository extends SensorDataRepository<LightData>{
}
