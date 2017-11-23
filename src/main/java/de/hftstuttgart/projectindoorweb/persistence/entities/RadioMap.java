package de.hftstuttgart.projectindoorweb.persistence.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class RadioMap {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(targetEntity = RadioMapElement.class, cascade = CascadeType.ALL)
    private List<RadioMapElement> radioMapElements;

    protected RadioMap(){}


    public RadioMap(List<RadioMapElement> radioMapElements) {
        this.radioMapElements = radioMapElements;
    }

    public Long getId() {
        return id;
    }

    public List<RadioMapElement> getRadioMapElements() {
        return radioMapElements;
    }

    public void setRadioMapElements(List<RadioMapElement> radioMapElements) {
        this.radioMapElements = radioMapElements;
    }

}