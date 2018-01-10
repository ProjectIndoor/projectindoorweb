# FrontEnd Documentation

# Libraries

To make development easier several libraries were used.
Here is a small overview:

- **AngularJS** - main UI framework
  - **angular-material** - material UI components
- **Openlayers** - html/js map library
  - **angular-openlayers-directive** - connect map with angular
- Others
  - **jquery** 
  - **Material Design Icons**
  
Main framework for HTML/JS was AngularJS which allows us to create single page applications easily.
We used angular-material to get nicer material design UI components.
Openlayers was used to be able to display maps in our app.


# File Structure

The frontend part of the application can be found in the folder `src/main/resources/static`.
It is structured like this:

```
static
├── index.html                      # main html page
├── app.js                          # main application definition
├── manifest.json                   # additional information for browsers
├── assets                          # assets like libraries and css
│   ├── css                             # own css files
│   ├── icons                           # own icons
│   └── vendor                          # external libraries (auto gen) 
├── pages                           # html/js files for pages
│   ├── map.html                        
│   ├── map.js
│   ├── ...
│   ├── forms                           # files describing parts of a page
│   │   ├── edit
│   │   │   ├── building-edit.html
│   │   │   ├── building-edit.js
│   │   │   └── ...
│   │   └── ...
│   └── panels                          # layouts for additional panels
└── components                          # services used by the pages
    ├── mapService.js
    ├── calculationService.js
    └── ...

```

- On the root the index.html can be found which is the entry point of our single page application,
it describes the outer layout and imports all needed libraries.
In addition you also find the app.js file which is the entry point of our angular application.
- The pages folders holds the dedicated pages (consisting of html and js with page logic) which are
injected by angular into the main html file to display the contents (e.g. map.html/js for the map view).
The subfolder forms holds single components describing dedicated parts of a page like the
edit-building.html/js for the building part of the edit page
- The components folder is for additional angular components not bound to a specific page,
it contains services e.g. the calculationService.js which provides access to the calculation API of the backend
to all pages which need it.


# Site Overview

In this section, different components and pages of the front end will be discussed as the front end plays an important role in our project. The components will be discussed according to the workflow i.e in the order how we will use the pages to get the final results.

## Toolbar

![Toolbar](images/toolbar.png)

The toolbar is the main navigation element of the site. It is available at all pages which makes the navigation easy. Since the project makes use of the single page application, the toolbar is not required to be added in each html page and implemented only at one place.

The toolbar has two options represented with following icons.


 The first icon is ![Toolbar](images/ic_menu_black_24dp_1x.png). Once this menu button is clicked, the following side bar will appear.

 ![Navigation](images/navigation.png)

 It has the following options

 - Map View
 - View Projects
 - Import Data
 - Manage Data

 The following sections will explain each of these menu options and submenus under them with respect to the workflow.

## Import Data

This section is dedicated for the import options. The data needs to be fed to the system to obtain the results. Once you click on the Import Data option from the navigation side bar, a secondary toolbar will appear below the main toolbar providing different import options. The second toolbar is shown below.

![Import Toolbar](images/import.png)

### Buildings

In this page, you can add building related data such as name, number of floors and dimensions of the floor map image.

![Building Data](images/addBuilding1.png)

It also has an option to add the cordinations either with *center and rotation* or add with *Latitudes and Longitudes*. Below images show these options.

![Building Data with Latitudes and Longitudes](images/addBuilding2.png)

To add the cordinates with *Center and Rotation* the radio button has to be turned on.

![Building Data with Center and Rotation](images/addBuilding3.png)

Even though these parts are explained separately, they are present in the single page.

Once the building is added successfully, a toast will appear like below.

![Success Toast](images/successMessage.png)


### Floor Maps

This page is dedicated to upload the radio map file along with the building and floor selection which was added previously.

![Floor Map](images/addFloorMap.png)

If the file is uploaded successfully, a toast will be displayed indicating the sucess or a error toast will guide the user.

### Radiomap Data

This page will allow the user to upload Radiomap file. The dropdown will provide the list of the building names which were added before. The user can also add transformed log file by switching on the *Add a transformed points file*. This file will be automatically converted to get the positions.

![Radiomap Data](images/addRadioMap.png)

### Evaluation Data

The upload page for Evaluation file works similar to Radiomap upload. It also has a list of buildings for which the user wants to upload the evaluation file. Once the file is uploaded successfully, a toast will be displayed.

![Evaluation Data](images/addEvaluation.png)

## Map View

Once all the data is uploaded successfully in the Import Data section, the results can be fetched in Map View. At the right hand side, ![Settings icon](images/ic_settings_black_24dp_1x.png) button will toggle a side panel. The panel has different parameters to choose from.

The first block provides the list of buildings and the floors available for that building. Once the user clicks on *CHOOSE* button, next block will appear. The floor map image will change according to the building and floor selected.

![Choose Map](images/chooseMap.png)

Next block allows the user to choose from a prerecorded track for the evaluation.

![Choose Evaluation file](images/chooseEval.png)

After the evaluation file has been choosen, the user has to choose from different algorithms.

![Choose Algorithm](images/chooseAlgorithm.png)

The panel provides many more parameters to play with. These can be accessed under *SHOW MORE PARAMETERS*. Also these settings can be saved as a project so that the user can reuse the same set of parameters.

![Save Project](images/saveProject.png)

After all the parameters are set, the user needs to click on *CALCULATE*. This will populate the results on the map showing the reference points, calculated positions and error lines.

![Map](images/map.png)

# Validation

