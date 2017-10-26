package de.hft_stuttgart.jpawarmup.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class LocalFile {

    @Id
    protected String id;
    protected String sourceFileName;
    protected long lastModified;

    protected LocalFile(){}

    public LocalFile(String id, String sourceFileName, long lastModified) {
        this.id = id;
        this.sourceFileName = sourceFileName;
        this.lastModified = lastModified;
    }

    public String getId() {
        return id;
    }

    public String getSourceFileName() {
        return sourceFileName;
    }

    public void setSourceFileName(String sourceFileName) {
        this.sourceFileName = sourceFileName;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }
}
