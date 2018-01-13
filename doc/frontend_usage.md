# User Guide

In this document, different components and pages of the front end will be discussed as the front end plays an important role in our project. The components are grouped according to the tasks with respect to different elements of the project.

# Table of Contents

  * [Navigation](#Navigation)
  * [Buildings](#Buildings)
  * [Radiomap Data and Evaluation Data](#RadiomapDataandEvaluationData)
  * [Map View](#MapView)
  * [Validation](#Validation)

# Navigation

The toolbar is the main navigation element of the site. It is available on all pages which makes the navigation easy. Since the project makes use of the single page application, the toolbar is not required to be added to each HTML page and implemented only at one place.

![Toolbar](images/fe_toolbar.png)

The toolbar has two options represented by following icons.


 The first icon is ![Toolbar](images/fe_ic_menu_black_24dp_1x.png). Once this menu button is clicked, the following sidebar will appear.

 ![Navigation](images/fe_navigation.png)

 It has the following options

 - Map View
 - View Projects
 - Import Data
 - Manage Data

 The secondary toolbar is also available below the main toolbar for some of the menu options. It differs according to the menu item selected.

 Once the user clicks on the Import Data option from the navigation sidebar, a secondary toolbar will appear below the main toolbar providing different import options. The second toolbar is shown below.

 ![Import Toolbar](images/fe_import.png)

 The secondary toolbar changes like below once the Manage Data menu option is clicked below the main toolbar.

 ![Manage Data Toolbar](images/fe_manageData.png)

# Buildings

This section explains about all the tasks the user can do regarding the buildings. It explains how to add the building data, fetch the list of buildings and delete the building data.

## Add Building

In this page, you can add building-related data such as name, number of floors and dimensions of the floor map image. This page can be accessed under *Import Data / Buildings*.

![Building Data](images/fe_addBuilding1.png)

It also has an option to add the coordinates either with the *center and rotation* or with *Latitudes and Longitudes*. Below images show these options.

![Building Data with Latitudes and Longitudes](images/fe_addBuilding2.png)

To add the coordinates with *Center and Rotation* the radio button has to be turned on.

![Building Data with Center and Rotation](images/fe_addBuilding3.png)

Even though these parts are explained separately, they are present in the single page.

Once the building is added successfully, a toast will appear like below.

![Success Toast](images/fe_successMessage.png)

This page allows the user to upload the image of the floor along with the building and floor selection which was added previously. This page is available at *Import Data / Floor Maps*

![Floor Map](images/fe_addFloorMap.png)

If the file is uploaded successfully, a toast will be displayed indicating the success or an error toast will guide the user.

## List of Buildings

This page lists all the buildings uploaded by the user. This can be accessed at *Manage Data / Buildings*.

![Manage Buildings](images/fe_manageBuildings.png)

## View / Delete Building

The user can click on any building to view the details. Also, the building can be deleted with the *Delete* button provided. But if the building is associated with any radio map, evaluation files or any other project, the user cannot delete the building.

![Detailed View](images/fe_detailedView.png)

# Radiomap Data and Evaluation Data

This section explains tasks related to Radio maps.

## Add Radio Map Data

The page in the below image will allow the user to upload Radiomap file. The dropdown will provide the list of the building names which were added before. The user can also add transformed log file by switching on the *Add a transformed points file*. This file will be automatically converted to get the positions. This page can be accessed under *Import Data / Radiomap Data*.

![Radiomap Data](images/fe_addRadioMap.png)

### Add Evaluation Data

The upload page for Evaluation file works similar to Radiomap upload. It also has a list of buildings for which the user wants to upload the evaluation file. Once the file is uploaded successfully, a toast will be displayed. This page can be accessed under *Import Data / Evaluation Data*.

![Evaluation Data](images/fe_addEvaluation.png)

## List of Files

This page lists all the evaluation and radio map files uploaded. This can be accessed at path *Manage Data / Evaluation Files*.

![Manage Evaluation File](images/fe_manageEval.png)

## View / Delete Log File

The user can view or delete the radio map file or evaluation file similar to buildings. By clicking on any listed file, a popup with details appears with the delete option.

![Detailed View](images/fe_detailedViewLogFile.png)

# Map View

Once all the data is uploaded successfully in the Import Data section, the results can be fetched in Map View. At the right-hand side, ![Settings icon](images/fe_ic_settings_black_24dp_1x.png) button will toggle a side panel. The panel has different parameters to choose from.

The first block provides the list of buildings and the floors available for that building. Once the user clicks on *CHOOSE* button, next block will appear. The floor map image will change according to the building and floor selected.

![Choose Map](images/fe_chooseMap.png)

Next block allows the user to choose from a prerecorded track for the evaluation.

![Choose Evaluation file](images/fe_chooseEval.png)

After the evaluation file has been chosen, the user has to choose from different algorithms.

![Choose Algorithm](images/fe_chooseAlgorithm.png)

The panel provides many more parameters to play with. These can be accessed by clicking *SHOW MORE PARAMETERS*. Also, these settings can be saved as a project so that the user can reuse the same set of parameters.

![Save Project](images/fe_saveProject.png)

After all the parameters are set, the user needs to click on *CALCULATE*. This will populate the results on the map showing the reference points, calculated positions, and error lines.

![Map](images/fe_map.png)

At the end of the panel, there is also an indication of the error in the calculation of positions with this set of parameters and algorithm.

![Error Display](images/fe_errorDisplay.png)

# Validation

In this project, the frontend validation is handled through a module called *ngMessages* provided by AngularJS. It provides an elegant way to validate mandatory fields, emails, min/max for a field and customized validation using regular expressions. In this project, we are using it to check for required fields, the range of Latitudes and Longitudes.

![Validation](images/fe_validation.png)
