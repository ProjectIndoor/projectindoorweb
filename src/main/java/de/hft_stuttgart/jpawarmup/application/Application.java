package de.hft_stuttgart.jpawarmup.application;

import de.hft_stuttgart.jpawarmup.entities.GnssData;
import de.hft_stuttgart.jpawarmup.entities.LightData;
import de.hft_stuttgart.jpawarmup.helpers.LocalFileHelper;
import de.hft_stuttgart.jpawarmup.repositories.*;
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
import de.hft_stuttgart.jpawarmup.parsers.ParserHandler;
import de.hft_stuttgart.jpawarmup.parsers.ParserTypes;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


@SpringBootApplication
@EntityScan("de.hft_stuttgart.jpawarmup.entities")
@EnableJpaRepositories("de.hft_stuttgart.jpawarmup.repositories")
public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    private static String fileName;


    public static void main(String[] args) {

        if (args == null || args.length == 0) {
            fileName = "logfile_CAR_R01-2017_A5.txt";
        } else {
            fileName = args[0];
        }
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner doRun(LogFileRepository logFileRepository,
                                   AccelerometerDataRepository accelerometerDataRepository,
                                   MagnetometerDataRepository magnetometerDataRepository,
                                   LightDataRepository lightDataRepository,
                                   GnssDataRepository gnssDataRepository) {

        return (args) -> {
            ParserHandler parserHandler;
            if (Files.exists(Paths.get(fileName))) {
                String customFileId = LocalFileHelper.getInstance().getIdFromSourceFileName(fileName);
                boolean fileAlreadyParsed = logFileRepository.countById(customFileId) > 0;

                if (!fileAlreadyParsed) {
                    LOG.info("File not yet present in database, starting parsers...");

                    parserHandler = ParserHandler.getInstance();
                    parserHandler.initParsersForFile(fileName);
                    long start = System.currentTimeMillis();
                    parserHandler.runParsers();
                    long end = System.currentTimeMillis();

                    LOG.info(String.format("File parsing took %d milliseconds.", end - start));

                    List<ParserTypes> allKnownParsers = parserHandler.getAllKnownParsers();

                    for (ParserTypes type :
                            allKnownParsers) {

                        if (type == ParserTypes.ACCELERATION_DATA) {
                            List<AccelerometerData> sensorData = (List<AccelerometerData>) parserHandler.collectParseResultsByType(ParserTypes.ACCELERATION_DATA);
                            accelerometerDataRepository.save(sensorData);
                        } else if (type == ParserTypes.MAGNETOMETER_DATA) {
                            List<MagnetometerData> sensorData = (List<MagnetometerData>) parserHandler.collectParseResultsByType(ParserTypes.MAGNETOMETER_DATA);
                            magnetometerDataRepository.save(sensorData);
                        } else if (type == ParserTypes.LIGHT_DATA) {
                            List<LightData> sensorData = (List<LightData>) parserHandler.collectParseResultsByType(ParserTypes.LIGHT_DATA);
                            lightDataRepository.save(sensorData);
                        } else if (type == ParserTypes.GNSS_DATA) {
                            List<GnssData> sensorData = (List<GnssData>) parserHandler.collectParseResultsByType(ParserTypes.GNSS_DATA);
                            gnssDataRepository.save(sensorData);
                        }

                    }

                    LOG.info("Successfully saved sensor data.");


                }else{
                    LOG.info("File already present in database, won't parse.");
                }

                long numEntries = accelerometerDataRepository.count()
                        + magnetometerDataRepository.count()
                        + lightDataRepository.count()
                        + gnssDataRepository.count();

                LOG.info("Number of entries present in the database: " + numEntries);

                numEntries = accelerometerDataRepository.countByRawName("ACCE");
                LOG.info("Number of entries with sensor type 'ACCE': " + numEntries);

                numEntries = magnetometerDataRepository.countByRawName("MAGN");
                LOG.info("Number of entries with sensor type 'MAGN': " + numEntries);

                numEntries = lightDataRepository.countByRawName("LIGH");
                LOG.info("Number of entries with sensor type 'LIGH': " + numEntries);

                numEntries = gnssDataRepository.countByRawName("GNSS");
                LOG.info("Number of entries with sensor type 'GNSS': " + numEntries);

                List<LightData> lightData = lightDataRepository.findTop10ByLogFileId(LocalFileHelper.getInstance().getIdFromSourceFileName(fileName));
                LOG.info("Found the following top 10 entities of type 'LightData' for file " + fileName + ":");

                for (LightData data :
                        lightData) {
                    LOG.info(data.toString());
                }

                LOG.info("All done.");


            } else {
                LOG.error(String.format("No such file: %s.", fileName));
            }
        };


    }


}