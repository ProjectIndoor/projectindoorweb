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

```gradlew
./gradlew build docker
```

## Built With

* [Spring](https://spring.io/) - The Java web framework used
* [AngularJS](https://angularjs.org/) - HTML/JavaScript framework used
* [Gradle](https://gradle.org/) - Build tool used


## Authors

This application was initially developed at the HFT Stuttgart for a software project by the students of the Software Technology Master in winter semester 17/18.

See also the list of [contributors](https://github.com//ProjectIndoor/projectindoorweb/contributors) who participated in this project.

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details