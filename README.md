# Project Indoor (Web)

A web application which helps to test and evaluate indoor positioning algorithms.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

To get started with development you first need to ensure a current Java JDK is installed on your System (JDK8 or later). 
You can get version 8 of the Java Development Kit from Oracle [here](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) or from the repository of your distribution when using Linux.

### Installing

To get a basic setup you just need to clone the project and run a few commands to get started:

```sh
# Clone the project
git clone https://github.com/ProjectIndoor/projectindoorweb.git
# Go to the cloned directory
cd projectindoorweb
# Preload client (JS) dependencies
./gradlew clientInstall
```

This is the basic state you need to continue development.
You could use any IDE compatible with gradle projects (or basically any text editor, even vim, and execute gradle from the commandline). We recommend using IntelliJ IDEA for development though.

To build the application using gradle you just have to run:

```sh
./gradlew build
```

To start up the application during development you can also use gralde calling:

```sh
./gradlew bootRun
```

The application then can be accessed with your browser on [http://localhost:8080](http://localhost:8080)

## Deployment

For deployment a [Dockerfile](Dockerfile) is provided it can be used to create an image of the application. It can be build from gradle using:

```sh
./gradlew build docker
```

Please note that you need a working basic development setup like it is described above.

Pre build docker image releases are also available directly on [docker hub](https://hub.docker.com/r/hftstuttgart/projectindoorweb), you can find more information about the image there too.

### Push a new image

To publish a new image you first build it with the command above, this will create a new local version of the docker image.
This can than be tagged and pushed to docker hub (see [here](https://docs.docker.com/docker-cloud/builds/push-images/) for a detailed explanation).

```sh
# Create a tag (latest as default)
docker tag hftstuttgart/projectindoorweb hftstuttgart/projectindoorweb
# Create a tag for a versioned build (e.g. 1.0)
docker tag hftstuttgart/projectindoorweb hftstuttgart/projectindoorweb:1.0

# Push the image to docker hub (see link above to setup account)
docker push hftstuttgart/projectindoorweb
```

### Docker-compose

To make the usage of the docker images even easier this repository also comes with an example [docker-compose](docker-compose.yml) setup.
This setup consists of three containers: mariadb, nginx and the indoor web application.
To start this setup it is enough to just run in the checked out repository:

```sh
docker-compose up
# or run it in the background with
docker-compose up -d
```

Note that the provided compose file defines all three services to restart always.

To remove the application run, again in the repository folder:

```sh
docker-compose down
```
But note that the defined example volume indoor_map will be removed too with this method, so either use another volume or a path on the disk to persist them.

## Documentation

Additional information about the application is available in the doc folder in this repository.

* [Frontend](doc/frontend.md) - Structure and architecture of the frontend
* [Frontend User Guide](doc/frontend_usage.md) - User guide for the frontend
* [Backend](doc/backend.md) - Usage and architecture of the backend


## Built With

* [Spring](https://spring.io/) - The Java web framework used
* [AngularJS](https://angularjs.org/) - HTML/JavaScript framework used
* [Gradle](https://gradle.org/) - Build tool used


## Authors

This application was initially developed at the HFT Stuttgart for a software project by the students of the Software Technology Master in winter semester 17/18.

See also the list of [contributors](https://github.com//ProjectIndoor/projectindoorweb/contributors) who participated in this project.

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details
