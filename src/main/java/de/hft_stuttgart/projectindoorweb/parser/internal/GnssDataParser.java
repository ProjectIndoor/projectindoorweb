package de.hft_stuttgart.projectindoorweb.parser.internal;

public class GnssDataParser extends SensorDataParser{

    public GnssDataParser(String filePath) {
        super(filePath);
    }

    @Override
    public void run() {

    }

    @Override
    protected boolean isValidLine(String line) {
        return false;
    }

}
