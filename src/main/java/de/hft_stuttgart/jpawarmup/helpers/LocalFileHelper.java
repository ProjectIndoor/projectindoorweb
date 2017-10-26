package de.hft_stuttgart.jpawarmup.helpers;

import de.hft_stuttgart.jpawarmup.entities.LocalFile;
import de.hft_stuttgart.jpawarmup.entities.LogFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class LocalFileHelper {

    private static LocalFileHelper theInstance;

    private Map<String, LocalFile> logFileIds;

    private LocalFileHelper(){}

    public static LocalFileHelper getInstance(){
        if(theInstance == null){
            theInstance = new LocalFileHelper();
            theInstance.logFileIds = new HashMap<>();
        }
        return theInstance;
    }


    public LocalFile retrieveLogFileFromSource(String sourceFileName){

        if(Files.exists(Paths.get(sourceFileName))){
            File sourceFile = new File(sourceFileName);
            long lastModified = sourceFile.lastModified();
            String id = generateId(sourceFileName, lastModified);
            logFileIds.put(id, new LogFile(id, sourceFileName, lastModified));
            return logFileIds.get(id);
        }
        return null;

    }

    public String getIdFromSourceFileName(String sourceFileName){

        if(Files.exists(Paths.get(sourceFileName))){
            File sourceFile = new File(sourceFileName);
            long lastModified = sourceFile.lastModified();
            return generateId(sourceFileName, lastModified);
        }
        return null;

    }

    private String generateId(String sourceFileName, long fileLastModified){

        long hash = 1;
        for(int i = 0; i < sourceFileName.length(); i++){
            hash = hash * 3 + sourceFileName.charAt(i);
        }

        return (String.format("%d%d", hash, fileLastModified));
    }
}
