[![coverage](https://raw.githubusercontent.com/WicioW/ShipLog/badges/jacoco.svg)](https://github.com/WicioW/ShipLog/actions/workflows/github-ci.yml)

# ShipLog
ShipLog is a small backend application that collects data from marine vessels and displays them for users. The project consists of two distinct parts:

1. Fake Data Generation Algorithm(`route` package): As real data from vessels is not currently collected, this algorithm generates fake data for vessels in the database.
2. CRUD API(rest of the packages): The CRUD API provides two controllers for managing vessels and logs. A log entity contains ship data at a specific timestamp.


## Prerequisites
Java 20  
Spring 3.1.0  
Maven  
Docker  

## Installation
Start the database and kafka by running the following commands in the project directory:

```commandline
docker-compose up -d
```
Start the Spring application from your IDE. Please note that this setup is for development purposes only, as there is no live environment available.

##  Configuration
You can optionally edit the application.properties file, although it is not necessary for the current state of the application.
##  Project Structure
The project follows a standard Java Spring structure. You can explore the code to understand its organization and purpose.

## APIs and Endpoints
In the future, Swagger documentation will be added to provide a comprehensive view of the REST endpoints and their usage.

## Testing
The project includes standard Maven tests for ensuring its functionality. You can run these tests using the appropriate Maven commands.

## Deployment
Currently, the project is intended for local development purposes only. You can deploy it locally from your IDE.


## Troubleshooting
No specific troubleshooting information is available at this time.

## Contributing
There are currently no guidelines for contributing to the project. Feel free to explore and make modifications as needed.

## License
This project is currently not licensed.

Feel free to modify the generated README file as needed to align with your project's specific requirements and preferences.

## Hot to improve this project in the future

- Add Swagger documentation
- Divide the project into microservices. `route` package should be a separate service.
- Add a frontend application to display the data.
- Add authentication and authorization.
