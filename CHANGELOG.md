# Changelog

All notable changes to the Expedition Companion project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Planned
- Expedition CRUD operations
- Team management functionality
- Equipment tracking system
- User authentication and authorization
- Web user interface
- File upload capabilities
- Real-time notifications
- Reporting and analytics
- Mobile application
- API documentation with Swagger/OpenAPI

## [0.1.0] - 2024-01-31

### Added
- Initial Spring Boot application structure
- Basic project setup with Maven
- MySQL database integration
- Spring Data JPA configuration
- Spring Boot DevTools for development
- Lombok for reducing boilerplate code
- Basic application.properties configuration
- Docker support preparation
- Comprehensive project documentation
  - README.md with setup and usage instructions
  - CONTRIBUTING.md with contributor guidelines
  - API.md with detailed API documentation
  - DEPLOYMENT.md with deployment guides for various platforms
- Example configuration files for different environments
- Basic health check endpoint via Spring Boot Actuator
- Logging configuration with structured output
- Database connection pooling with HikariCP

### Technical Details
- **Framework**: Spring Boot 2.6.3
- **Java Version**: Java 8+
- **Database**: MySQL 8.0 support
- **Build Tool**: Maven 3.6+
- **Testing**: JUnit 5 with Spring Boot Test

### Development Setup
- Maven wrapper for consistent builds
- Development and production configuration examples
- Database setup scripts and documentation
- Local development environment guide

### Documentation
- Complete API documentation with examples
- Deployment guides for major cloud platforms (AWS, GCP, Azure)
- Docker and Docker Compose configurations
- Monitoring and logging setup instructions
- Security considerations and best practices
- Troubleshooting guides

## [0.0.1] - Initial Commit

### Added
- Basic Spring Boot application skeleton
- Maven project structure
- Git repository initialization

---

## Version Numbering

This project follows [Semantic Versioning](https://semver.org/):

- **MAJOR** version when making incompatible API changes
- **MINOR** version when adding functionality in a backwards compatible manner  
- **PATCH** version when making backwards compatible bug fixes

## Release Process

1. Update version number in `pom.xml`
2. Update this CHANGELOG.md
3. Create git tag with version number
4. Create GitHub release with release notes
5. Deploy to production environment

## Contributing

When contributing, please:
1. Add new features under `[Unreleased]` section
2. Follow the format: `### Added/Changed/Deprecated/Removed/Fixed/Security`
3. Include brief description of changes
4. Reference issue numbers when applicable

Example:
```markdown
### Added
- New expedition management API endpoints (#123)
- Real-time notification system (#145)

### Fixed
- Database connection timeout issue (#156)
- Authentication token expiration bug (#167)
```