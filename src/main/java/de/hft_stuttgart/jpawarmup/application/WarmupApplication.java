package de.hft_stuttgart.jpawarmup.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import de.hft_stuttgart.jpawarmup.entities.AccelerometerData;
import de.hft_stuttgart.jpawarmup.entities.MagnetometerData;
import de.hft_stuttgart.jpawarmup.interfaces.AccelerometerDataRepository;
import de.hft_stuttgart.jpawarmup.interfaces.MagnetometerDataRepository;
import de.hft_stuttgart.jpawarmup.parsers.ParserHandler;
import de.hft_stuttgart.jpawarmup.parsers.ParserTypes;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


@SpringBootApplication
@EntityScan("de.hft_stuttgart.jpawarmup.entities")
@EnableJpaRepositories("de.hft_stuttgart.jpawarmup.interfaces")
public class WarmupApplication {

    private static final Logger LOG = LoggerFactory.getLogger(WarmupApplication.class);

    private static String fileName;


    public static void main(String[] args) {

        if(args == null || args.length == 0){
            fileName = "logfile_CAR_R01-2017_A5.txt";
        }else{
            fileName = args[0];
        }

        SpringApplication.run(WarmupApplication.class, args);
    }

    @Bean
    public CommandLineRunner doRun(AccelerometerDataRepository accelerometerDataRepository, MagnetometerDataRepository magnetometerDataRepository){

        return (args) -> {
            ParserHandler parserHandler;
            if (Files.exists(Paths.get(fileName))) {
                parserHandler = ParserHandler.getInstance();
                parserHandler.initParsersForFile(fileName);
                parserHandler.runParsers();

                List<ParserTypes> allKnownParsers = parserHandler.getAllKnownParsers();

                for (ParserTypes type:
                     allKnownParsers) {

                    if(type ==  ParserTypes.ACCELERATION_DATA){
                        List<AccelerometerData> sensorData = (List<AccelerometerData>) parserHandler.collectParseResultsByType(ParserTypes.ACCELERATION_DATA);
                        accelerometerDataRepository.save(sensorData);
                    }else if(type == ParserTypes.MAGNETOMETER_DATA){
                        List<MagnetometerData> sensorData = (List<MagnetometerData>) parserHandler.collectParseResultsByType(ParserTypes.MAGNETOMETER_DATA);
                        magnetometerDataRepository.save(sensorData);
                    }

                }

                LOG.info("Successfully saved sensor data.");

                long numEntries = accelerometerDataRepository.count() + magnetometerDataRepository.count();
                LOG.info("Number of entries present in the database: " + numEntries);

                numEntries = accelerometerDataRepository.countByRawName("ACCE");
                LOG.info("Number of entries with sensor type 'ACCE': " + numEntries);

                numEntries = magnetometerDataRepository.countByRawName("MAGN");
                LOG.info("Number of entries with sensor type 'MAGN': " + numEntries);

                LOG.info("All done.");

            }else{
                LOG.error(String.format("No such file: %s.", fileName));
            }
        };


    }





}