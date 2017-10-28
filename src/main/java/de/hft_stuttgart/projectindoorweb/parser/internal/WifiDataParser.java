package de.hft_stuttgart.projectindoorweb.parser.internal;

public class WifiDataParser extends  SensorDataParser {
    public WifiDataParser(String filePath) {
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
