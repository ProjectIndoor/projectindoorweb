package de.hftstuttgart.projectindoorweb.persistence.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    private Set<Position> buildingAnchor;

    @OneToMany(mappedBy = "recordedInBuilding", cascade = CascadeType.ALL)
    private Set<LogFile> logFiles;

    public Building(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Position> getBuildingAnchor() {
        return buildingAnchor;
    }

    public void setBuildingAnchor(Set<Position> buildingAnchor) {
        this.buildingAnchor = buildingAnchor;
    }

    public Set<LogFile> getLogFiles() {
        return logFiles;
    }

    public void setLogFiles(Set<LogFile> logFiles) {
        this.logFiles = logFiles;
    }
}
