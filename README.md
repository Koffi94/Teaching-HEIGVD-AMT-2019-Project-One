# AMT - Project One

**Authors: Nathanaël Mizutani [NatMiz](https://github.com/NatMiz), Olivier Koffi [Koffi94](https://github.com/Koffi94)**

## Specifications

The specifications for the *Project One* can be found [here](Specifications.md).

The main objective of this project is to apply the patterns and techniques presented during the lectures, and to create a simple multi-tiered application in Java EE.

## Prerequisites

You need these programs installed on your system:

- Docker
- Docker Compose
- IDE as [IntelliJ](https://www.jetbrains.com/idea/download/)

Install docker for [Windows 10](https://runnable.com/docker/install-docker-on-windows-10), [MAC](https://runnable.com/docker/install-docker-on-macos) or [Linux](https://runnable.com/docker/install-docker-on-linux).

[Install Docker Compose](https://docs.docker.com/compose/install/).

## Installing

**Make sure you have all the prerequisites before proceeding.**

To get the system running, in a terminal :
1. Run `generateSQL.sh`; it takes a little less than a minute to generate approximatly 2 millions data.
2. Run `runAppWithoutTests.sh` to launch the application *without running the tests*; since there is a lot of data to upload the MySQL container takes a little more than 3 minutes to get up and running.

To stop the application, run `stopApp.sh`.

## Tests

The tests descriptions and results can be found [here](Documentation/Tests.md).

## Built With

* [IntelliJ](https://www.jetbrains.com/idea/) - IDE
* [Maven](https://maven.apache.org/) - Dependency Management
* [Docker](https://www.docker.com/) - Containerisation

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE.md) file for details.

## Acknowledgment

We would like to thanks: 
* the coffee for helping us through the development
* Stackoverflow
* Maven documentation
* Oracle Documentation
