# Expedition Companion

A Spring Boot web application designed to help manage and organize expedition activities, providing tools for expedition planning, tracking, and management.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [API Documentation](#api-documentation)
- [Development](#development)
- [Contributing](#contributing)
- [Troubleshooting](#troubleshooting)
- [License](#license)

## Overview

Expedition Companion is a web-based application built with Spring Boot that serves as a comprehensive tool for expedition management. Whether you're planning hiking trips, research expeditions, or adventure activities, this application provides the necessary features to organize, track, and manage your expeditions effectively.

## Features

- **Expedition Planning**: Create and manage expedition plans
- **Resource Management**: Track equipment, supplies, and resources
- **Team Coordination**: Manage expedition team members and roles
- **Progress Tracking**: Monitor expedition progress and milestones
- **Data Persistence**: Secure data storage with MySQL database
- **RESTful API**: Clean API for integration with other systems
- **Web Interface**: User-friendly web interface for easy access

## Prerequisites

Before running this application, make sure you have the following installed:

- **Java 8** or higher
- **Maven 3.6+** for building the project
- **MySQL 5.7+** or **MySQL 8.0+** for database
- **Git** for version control

## Installation

### 1. Clone the Repository

```bash
git clone https://github.com/AhmedBouabda/ExpeditionCompanion.git
cd ExpeditionCompanion
```

### 2. Database Setup

Create a MySQL database for the application:

```sql
CREATE DATABASE expedition_companion;
CREATE USER 'expedition_user'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON expedition_companion.* TO 'expedition_user'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Build the Application

```bash
# Using Maven wrapper (recommended)
./mvnw clean compile

# Or using system Maven
mvn clean compile
```

## Configuration

### Database Configuration

Create or update `src/main/resources/application.properties` with your database settings:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/expedition_companion
spring.datasource.username=expedition_user
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Server Configuration
server.port=8080
server.servlet.context-path=/api

# Logging Configuration
logging.level.org.springframework=INFO
logging.level.com.mysql=DEBUG
```

### Environment Variables

You can also configure the application using environment variables:

```bash
export DB_URL=jdbc:mysql://localhost:3306/expedition_companion
export DB_USERNAME=expedition_user
export DB_PASSWORD=your_password
export SERVER_PORT=8080
```

## Usage

### Running the Application

#### Development Mode

```bash
# Using Maven wrapper
./mvnw spring-boot:run

# Or using system Maven
mvn spring-boot:run
```

#### Production Mode

```bash
# Build JAR file
./mvnw clean package

# Run the JAR
java -jar target/Expedetion_Companion-0.0.1-SNAPSHOT.jar
```

### Accessing the Application

Once started, the application will be available at:
- **Base URL**: `http://localhost:8080`
- **API Endpoints**: `http://localhost:8080/api`
- **Health Check**: `http://localhost:8080/api/actuator/health` (if actuator is enabled)

## API Documentation

### Base URL
```
http://localhost:8080/api
```

### Authentication
*Note: Authentication details will be added as the API develops*

### Core Endpoints

#### Expeditions
- `GET /expeditions` - Get all expeditions
- `GET /expeditions/{id}` - Get expedition by ID
- `POST /expeditions` - Create new expedition
- `PUT /expeditions/{id}` - Update expedition
- `DELETE /expeditions/{id}` - Delete expedition

#### Team Management
- `GET /teams` - Get all teams
- `POST /teams` - Create new team
- `GET /teams/{id}/members` - Get team members

*Note: Detailed API documentation will be expanded as endpoints are implemented*

## Development

### Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── spring/esprit/expedetion_companion/
│   │       └── ExpedetionCompanionApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── spring/esprit/expedetion_companion/
            └── ExpedetionCompanionApplicationTests.java
```

### Development Setup

1. **IDE Setup**: Import the project as a Maven project in your preferred IDE (IntelliJ IDEA, Eclipse, VS Code)

2. **Database Setup**: Ensure MySQL is running and the database is created

3. **Configuration**: Set up your `application.properties` file with correct database credentials

4. **Dependencies**: All dependencies are managed through Maven - run `./mvnw dependency:resolve` to download them

### Running Tests

```bash
# Run all tests
./mvnw test

# Run tests with coverage
./mvnw test jacoco:report

# Run specific test
./mvnw test -Dtest=ExpedetionCompanionApplicationTests
```

### Code Style

- Follow Java naming conventions
- Use Spring Boot best practices
- Write meaningful commit messages
- Include tests for new features
- Document public APIs

### Adding New Features

1. Create feature branch: `git checkout -b feature/your-feature-name`
2. Implement feature with tests
3. Update documentation
4. Submit pull request

## Contributing

We welcome contributions! Please see [CONTRIBUTING.md](CONTRIBUTING.md) for detailed guidelines.

### Quick Start for Contributors

1. Fork the repository
2. Create your feature branch
3. Make your changes
4. Add/update tests
5. Update documentation
6. Submit a pull request

## Troubleshooting

### Common Issues

#### Database Connection Issues
```
Error: Failed to determine a suitable driver class
```
**Solution**: Ensure MySQL is running and connection details in `application.properties` are correct.

#### Port Already in Use
```
Error: Port 8080 was already in use
```
**Solution**: Change the port in `application.properties`:
```properties
server.port=8081
```

#### Build Issues
```
Error: JAVA_HOME not set
```
**Solution**: Set JAVA_HOME environment variable:
```bash
export JAVA_HOME=/path/to/java
```

### Getting Help

- **Issues**: Report bugs and feature requests on [GitHub Issues](https://github.com/AhmedBouabda/ExpeditionCompanion/issues)
- **Discussions**: Join discussions on [GitHub Discussions](https://github.com/AhmedBouabda/ExpeditionCompanion/discussions)
- **Documentation**: Check this README and inline code documentation

## Technology Stack

- **Backend**: Spring Boot 2.6.3
- **Database**: MySQL 8.0
- **Build Tool**: Maven 3.6+
- **Java Version**: Java 8+
- **Testing**: JUnit 5, Spring Boot Test
- **Development Tools**: Spring Boot DevTools, Lombok

## Roadmap

- [ ] Implement core expedition management features
- [ ] Add user authentication and authorization
- [ ] Create web UI interface
- [ ] Add real-time notifications
- [ ] Implement file upload for expedition documents
- [ ] Add reporting and analytics features
- [ ] Mobile application development

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

**Author**: Ahmed Bouabda  
**GitHub**: [@AhmedBouabda](https://github.com/AhmedBouabda)

---

*Last Updated: $(date +'%Y-%m-%d')*