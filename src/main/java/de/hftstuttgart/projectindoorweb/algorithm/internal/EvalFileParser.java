package de.hftstuttgart.projectindoorweb.algorithm.internal;

import de.hftstuttgart.projectindoorweb.persistence.entities.EvalFile;
import de.hftstuttgart.projectindoorweb.persistence.entities.RssiSignal;
import de.hftstuttgart.projectindoorweb.persistence.entities.WifiAccessPoint;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class EvalFileParser {

    private final EvalFile evalFile;
    private final Map<Integer, List<String>> wifiBlocks;

    public EvalFileParser(File evalFile) {
        this.evalFile = generateEvalFile(evalFile);
        this.wifiBlocks = new HashMap<>();
        initWifiBlockList();
    }

    public int getTotalNumberOfWifiBlocks() {

        return wifiBlocks.size();
    }

    public List<RssiSignal> retrieveAveragedRssiSignalsForWifiBlock(int block) {

        List<String> wifiBlock = wifiBlocks.get(block);
        Map<String, Double> rssiSignalsForMacs = retrieveRssiSignalsForWifiBlock(wifiBlock);
        Map<String, Integer> numberOfMacs = retrieveNumMacsForWifiBlock(wifiBlock);

        List<RssiSignal> result = new ArrayList<>();
        int numMacs;
        double rssiSignalStrength;
        boolean averaged;
        for (String currentMac:
             rssiSignalsForMacs.keySet()) {
            numMacs = numberOfMacs.get(currentMac);
            rssiSignalStrength = rssiSignalsForMacs.get(currentMac) / Double.valueOf(numMacs);
            averaged = numMacs > 1;
            result.add(new RssiSignal(0.0, rssiSignalStrength, averaged, new WifiAccessPoint(currentMac)));
        }

        return result;
    }

    private Map<String, Double> retrieveRssiSignalsForWifiBlock(List<String> wifiBlock){

        Map<String, Double> result = new HashMap<>();

        String mac;
        double rssiSignalStrength;
        for (String currentWifiLine :
                wifiBlock) {
            mac = currentWifiLine.split(";")[4];
            rssiSignalStrength = Double.parseDouble(currentWifiLine.split(";")[5]);
            if(!result.containsKey(mac)){
                result.put(mac, rssiSignalStrength);
            }else{
                result.put(mac, result.get(mac) + rssiSignalStrength);
            }
        }

        return result;

    }

    private Map<String, Integer> retrieveNumMacsForWifiBlock(List<String> wifiBlock) {

        Map<String, Integer> result = new HashMap<>();

        String mac;
        for (String currentWifiLine :
                wifiBlock) {
            mac = currentWifiLine.split(";")[4];
            if(!result.containsKey(mac)){
                result.put(mac, 1);
            }else{
                result.put(mac, result.get(mac) + 1);
            }
        }

        return result;


    }


    private void initWifiBlockList() {

        List<String> fileLines = this.evalFile.getFilteredFileContent();

        int currentBlock = 0;
        boolean insideWifiBlock = false;
        for (String currentLine :
                fileLines) {
            if (currentLine.startsWith("WIFI")) {
                if (!insideWifiBlock) {
                    insideWifiBlock = true;
                    wifiBlocks.put(currentBlock, new ArrayList<>());
                }
                wifiBlocks.get(currentBlock).add(currentLine);
            }
            if (insideWifiBlock && !currentLine.startsWith("WIFI")) {
                insideWifiBlock = false;
                currentBlock++;
            }
        }

    }

    private EvalFile generateEvalFile(File file) {

        try {
            List<String> filteredFileContents = Files.lines(file.toPath()).filter(s -> {

                return !s.isEmpty() && !s.startsWith("%");
            }).collect(Collectors.toList());

            return new EvalFile(file.getName(), null, filteredFileContents);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }


}
