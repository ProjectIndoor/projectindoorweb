package de.hft_stuttgart.jpawarmup.entities;

import javax.persistence.Entity;

@Entity
public class LogFile extends LocalFile {

    public LogFile(String id, String sourceFileName, long lastModified){
        super(id, sourceFileName, lastModified);
    }
}
