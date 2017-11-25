package de.hftstuttgart.projectindoorweb.persistence.entities;

/*
* Our final product will have a possibility for the customer to define a set of parameters as well as a certain
* algorithm as a "Project". We could even include the source files to be part of the project -- the files used to
* generate a result, that is -- so we can link an object of class {@link WifiPositionResult} back to the project.
* This would enable us to not only skip the entire pre-processing, but also the calculation of a result if it
 * is detected that a particular project was already pre-processed and calculated before.
*
* This class then serves as a container for the projects because we currently don't know yet how exactly it will look
* like, but only that we will need it in the near future.
*
* NOTE: It is probably not very reasonable to send the entire project information along with each result object (an object
* of class {@link WifiPositionResult}), but only along with a list of these objects. Therefore, objects of class
* {@link WifiPositionResult} do not hold a reference to an object of class {@link Project} themselves.
* */

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public long getId() {
        return id;
    }


}
