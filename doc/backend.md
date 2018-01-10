## Description of the REST API

### Overview

(Volkan)
General overview of available REST resources 

### Buildings

(Volkan)
REST Controller Building methods and calls

### Projects

(Volkan)
REST Controller Projects methods and calls

### Positioning

(Volkan)
REST Controller Positioning methods and calls

## Backend architecture

### Service architecture overview

(Volkan)
Overview of available services as class diagram

### Persistency Service

(Volkan)
Overview of persistency service with component as class diagram

### Positioning Service

(Volkan)
Overview of positioning service with component as class diagram

### Calculator Service

(Volkan)
Overview of calculator service with component as class diagram

### Example Workflow

(Nico)
An example workflow that demonstrates how services can be called
using the REST API, how they interact in the backend, and how they
yield results. 

## Persistence Layer

The following section provides a short overview of the __persistence layer__ of the backend. Because it uses __Spring Data JPA__ 
as an intermediate layer above the database, we do not execute any SQL statements ourselves; instead, operations on the 
database are carried out using __Spring's repository mechanism__.

We'll start off with an overview of the __database model__, introducing you to the entities present in the database and how they
are connected to each other. After that, you'll learn about the __database entities__ in detail; that is, why the entity was introduced, 
which functions it serves in the backend, and why its relations to other entities are the way they are.
 

### Database Model

An overview of the database model can be taken from the image below. Entity types are marked in orange; relation types are 
marked in blue.

![Overview ot the database model](./images/ErDiagram.jpg)

### Database Entities

This section explains the details of the database entities. We'll begin with one of the central entities -- the *EvaalFile* entity.

##### EvaalFile
The *EvaalFile* entity is where it all begins -- it's what all other entities are built upon (and no, the name 'Evaal' is not 
a typo -- it was chosen to take account of the fact that this entire project is dedicated to [this competition](http://evaal.aaloa.org)). 
One *EvaalFile* entity represents one processed smartphone sensor log file as recorded by the [GetSensorData 2.0 app](https://lopsi.weebly.com/downloads.html). It doesn't represent 
the entire source file line-by-line because the backend currently -- as of January 2018 -- only supports the WIFI algorithm for 
position calculation which only requires POSI and WIFI lines, hence persisting the entire file, including other content lines as 
well, would be a waste of both processing time and memory.

Each *EvaalFile* entity is recorded in exactly one building (it says in the competition rules that one recording run does not span 
multiple buildings, but might include several floors), is connected to exactly one *RadioMap* entity, and has a list of 
*WifiBlock* entities attached to it. The *RadioMap* is processed from the source file using both its POSI and WIFI lines, 
whereas the list of *WifiBlocks* is extracted using only the WIFI lines.  

#### RadioMap
If *EvaalFile* is the most important entity, then *RadioMap* is the second-most important one because it contains -- encapsulated 
in its sub-entity, which we'll have a look at a bit further in -- the basis for the WIFI-algorithm-based position calculation: 
the radio map processed from one *EvaalFile*. These two entities -- *EvaalFile* and *RadioMap* -- share a one-to-one relationship 
because no matter how often the processing steps are run on the source file, the resulting radio map will always be the same and each 
resulting radio map, of course, has to be connected to its original source file, represented by the according *EvaalFile* 
entity. 

Each *RadioMap* entity consists of arbitrarily many, but at least one entity of 
type *RadioMapElement*. The *RadioMapElements*, in turn, contain the data that is used during position 
calculation using the WIFI algorithm.

#### RadioMapElement
The list of *RadioMapElement* entities connected to exactly one *RadioMap* encapsulates the data necessary the execute the 
position calculation based on the WIFI algorithm. Each *RadioMapElement* is the result of executing certain processing 
steps on the POSI and WIFI lines of the source log file, and the parent *RadioMap* is the result of all its *RadioMapElements*. 

Because the processing steps carried out on the POSI and WIFI lines of the source log file assemble one *RadioMapElement* 
for each POSI line, the number of *RadioMapElements* each *RadioMap* has is equal to the number of POSI lines in the source file. 
If this file does not contain any POSI lines, the backend will skip the radio map generation processing steps so the *RadioMap* 
reference in the *EvaalFile* instance on application level will be 'null'. As a result, this instance (or entity on database level) 
cannot be used as a radio map during the position calculation steps.

#### PosiReference
The entities of type *PosiReference* encapsulate all data concerning the POSI lines in the given source log file (where each 
POSI line yields exactly one entity during the steps involved in generating the radio map), such as the position of the current 
POSI line in the source file, the position given as Latitude and Longitude, as well as the start and end of the time interval for 
this POSI reference.

#### RssiSignal
Each *RssiSignal* entity corresponds to one WIFI line in the source log file, encapsulating data such as the MAC address 
of the access point (which becomes an entity of type *WifiAccessPoint*), the RSSI signal strength, and the timestamp inserted 
by the 'GetSensorData 2.0' app used to record the source files. 

During the processing steps involved in generating a radio map from an EvAAL source file (represented in the database by an entity 
of type *RadioMap* and one of type *EvaalFile*, respectively), each list of RSSI signals is assigned to a POSI reference using 
the app timestamp. As a result, one *RadioMapElement* entity is essentially a container for one POSI reference (represented by 
an entity of *PosiReference*) and the list of RSSI signals (each represented by one *RssiSignal* entity) it is assigned based on 
the app timestamp. 

#### WifiAccessPoint
Each entity of type *WifiAccessPoint* represents one WIFI access point read from the given EvAAL source log file. Each 
*WifiAccessPoint* entity is assembled using the information contained in the WIFI lines of the source file.

#### Position
The *Position* entity encapsulates -- no points for guessing -- a position. It consists of an x, y, and z value as well as 
a field that indicates whether or not the given position is in WGS84 format. 

Within the database, all *Position* entities are always saved as WGS84 positions, meaning that their x and y values are 
Latitude and Longitude, respectively. The z value, on the other hand, depends on the building floor -- in most cases, it's 
simply a '0' for ground floor, '1' for first floor, and so on.

On application level, a given WGS84 position can be converted into a position referring to a local coordinate system or 
into a pixel position. The information necessary to do so is taken from the appropriate *Building* entity (where 'appropriate' 
means that we select the *Building* entity that represents the real-world building where the recording of the EvAAL log file 
containing an element the position in question refers to took place).

#### Building
Ah yes, the entities of type *Building* -- it's probably reasonable to say they are the third-most important type of entity in 
our database model. Each EvAAL source log file is recorded in (or around) one particular building and the *Building* entities were 
introduced to the database to represent them appropriately. Each EvAAL file then -- in turn represented by an *EvaalFile* entity -- 
is always linked to exactly one building, but each building can, of course, be referenced by an arbitrary number (including zero) 
of *EvaalFile* entities.

The database model overview in the previous section indicates that each *Building* refers to exactly one *Position*, and that 
each *Position* can be referenced by arbitrarily many *Buildings*. This is a simplification introduced to make 
the model more readable and less confusing, but that is not, in fact, entirely accurate: Each *Building* object (switching to 
code level for a moment) has five fields of type *Position*, each referencing exactly one *Position* (hence the one-to-many 
simplification made in the model overview). The five positions are comprised of one position for each of the four corner 
points of the real-world building (North-West, North-East, South-East, and South-West), as well as one position for the 
central reference point of the real-world building. This is all user-supplied information (in fact, the backend can 
calculate the four corner points of the building given that the user has provided the central reference point along with 
some other stuff, but this is not the right place to get bogged down in such details). The positions are used during the 
process of calculating positions for converting WGS84 coordinates into coordinates of a local coordinate system or into 
pixel coordinates. As a result, specifying these positions correctly is extremely important.

#### Project
The *Project* entities were introduced as a means to encapsulate all information necessary to execute a complete 
position calculation run. As such, it is the central entity for the user to specify various different run configurations, which 
typically includes the real-world building (represented by a *Building* entity) as well as the evaluation file and the radio map 
files to use (each represented by an *EvaalFile* entity), but also a set of parameters that specifies the behaviour of the 
position calculation.

#### Parameter
Last but not least, the *Parameter* entities serve provide the possibility to persist specific characteristics concerning 
the behavior of position calculation along with their parents (entities of type *Project* where each *Project* gets its own 
set of *Parameters*). On code level, the parameter objects for one project are simply implemented as a list, which allows to 
dynamically add more or omit some parameters.

## Functionality testing

(Nico)

### Blackbox tests overview

(Nico)
