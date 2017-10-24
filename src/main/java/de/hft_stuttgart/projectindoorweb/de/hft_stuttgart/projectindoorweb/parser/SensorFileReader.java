package de.hft_stuttgart.projectindoorweb.de.hft_stuttgart.projectindoorweb.parser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVFormat;

public class SensorFileReader {
    public static final String WIFI_FIELDNAME = "WIFI";
    public static final String POSITION_FIELDNAME = "POSI";
    public static final String ACCELERATION_FIELDNAME = "ACCE";
    public static final String GYROSCOPE_FIELDNAME = "GYRO";
    private String filePath;

    public SensorFileReader(String filePath){
        this.filePath=filePath;
    }

    public List readPositionFromFile() throws IOException{
        return readValuesFromFile(POSITION_FIELDNAME);
    }

    public List readAccelerationFromFile() throws IOException{
        return readValuesFromFile(ACCELERATION_FIELDNAME);
    }

    public List readGyroscopeFromFile() throws IOException{
        return readValuesFromFile(GYROSCOPE_FIELDNAME);
    }

    public List readWifiDataFromFile() throws IOException{
        return readValuesFromFile(WIFI_FIELDNAME);
    }

    private List readValuesFromFile(String fieldName) throws IOException{
        List result= new ArrayList<String>();
        FileReader in = new FileReader(this.filePath);
        Iterable<CSVRecord> records=CSVFormat.EXCEL.withDelimiter(';').parse(in);
        for(CSVRecord record: records){
            result.add(record.get(fieldName));
        }

        return result;
    }
}
