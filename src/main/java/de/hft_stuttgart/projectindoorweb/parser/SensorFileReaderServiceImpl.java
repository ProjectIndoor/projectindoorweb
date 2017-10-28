package de.hft_stuttgart.projectindoorweb.parser;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class SensorFileReaderServiceImpl implements SensorFileReaderService {


    private ExecutorService executorService;

    public SensorFileReaderServiceImpl(ExecutorService executorService){
        this.executorService = executorService;
    }

    @Override
    public List parseWifiData() {
        return null;
    }

    @Override
    public List parsePositionData() {
        return null;
    }

    @Override
    public List parseGyroscopeData() {
        return null;
    }

    @Override
    public List parseAccelerationData() {
        return null;
    }

    @Override
    public List parseMagnetometerData() {
        return null;
    }

    @Override
    public List parseGnssData() {
        return null;
    }

    @Override
    public List parseLightData() {
        return null;
    }
}
