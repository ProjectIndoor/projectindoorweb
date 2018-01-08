package de.hftstuttgart.projectindoorweb.inputHandler;

import de.hftstuttgart.projectindoorweb.persistence.entities.Building;
import de.hftstuttgart.projectindoorweb.persistence.entities.EvaalFile;

import java.io.File;
import java.util.List;


public interface PreProcessingService {

    /**
     * Processes a list of EvAAL files for the given building. The processing of EvAAL files consists of two steps, namely
     * a) to build a radiomap and b) to extract the WIFI blocks. Step a) can only be carried out if the current EvAAL file
     * consists valid POSI references. Step b), on the other hand, only depends on the WIFI blocks themselves, hence will
     * work with all (valid) EvAAL files. The generated radiomaps (if any) will be used during position calculation in case
     * the resulting instance of class {@link EvaalFile} is used as a radiomap file. The extracted WIFI blocks will be used
     * if the resulting instance of class {@link EvaalFile} is used as an evaluation file.
     *
     * @param building        The building for which the EvAAL file will be processed.
     * @param evaluationFiles Indicates whether or not the user wants the given list of EvAAL files to be used later as
     *                        evaluation files.
     * @param evaalFiles      The EvAAL files to be processed.
     * @return A list of instances of class {@link EvaalFile} containing the processed EvAAL files.
     */
    List<EvaalFile> processEvaalFiles(Building building, boolean evaluationFiles, List<File> evaalFiles);


    /**
     * Processes a list of EvAAL files for the given building that contain incomplete POSI references (where 'incomplete'
     * means that instead of a Latitude and Longitude number, the POSI references in question contain only '0.0' for both
     * their Latitude and Longitude). The implementation will complete the POSI references using the data provided by the
     * list of reference point files. After having completed the POSI references, the implementation will carry out two
     * steps, namely a) to build a radiomap and b) to extract the WIFI blocks. Step a) can only be carried out if the current EvAAL file
     * consists valid POSI references. Step b), on the other hand, only depends on the WIFI blocks themselves, hence will
     * work with all (valid) EvAAL files. The generated radiomaps (if any) will be used during position calculation in case
     * the resulting instance of class {@link EvaalFile} is used as a radiomap file. The extracted WIFI blocks will be used
     * if the resulting instance of class {@link EvaalFile} is used as an evaluation file.
     *
     * @param building            The building for which the EvAAL file will be processed.
     * @param evaluationFiles     Indicates whether or not the user wants the given list of EvAAL files to be used later as
     *                            evaluation files.
     * @param emptyEvaalFiles  The EvAAL files to be processed.
     * @param referencePointFiles The files containing the reference points used to complete the POSI references' positions.
     *                            The index of each file must correspond to the index of the EvAAL file it corresponds to.
     * @return
     */
    List<EvaalFile> processEmptyEvaalFiles(Building building, boolean evaluationFiles,
                                           List<File> emptyEvaalFiles, List<File> referencePointFiles);

}
