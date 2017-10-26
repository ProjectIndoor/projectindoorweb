package de.hft_stuttgart.jpawarmup.repositories;

import de.hft_stuttgart.jpawarmup.entities.LightData;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface LightDataRepository extends SensorDataRepository<LightData>{


}
