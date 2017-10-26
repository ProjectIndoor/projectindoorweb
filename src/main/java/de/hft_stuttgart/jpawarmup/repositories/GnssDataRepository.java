package de.hft_stuttgart.jpawarmup.repositories;

import de.hft_stuttgart.jpawarmup.entities.GnssData;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface GnssDataRepository extends SensorDataRepository<GnssData>{

}
