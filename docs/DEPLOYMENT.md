# Deployment Guide

This guide covers different deployment options for the Expedition Companion application.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Local Development Deployment](#local-development-deployment)
- [Docker Deployment](#docker-deployment)
- [Cloud Deployment](#cloud-deployment)
- [Production Considerations](#production-considerations)
- [Monitoring and Logging](#monitoring-and-logging)
- [Backup and Recovery](#backup-and-recovery)
- [Troubleshooting](#troubleshooting)

## Prerequisites

### System Requirements

- **Java**: 8 or higher (Java 11 recommended for production)
- **Memory**: Minimum 2GB RAM (4GB+ recommended for production)
- **Storage**: 10GB+ available disk space
- **Database**: MySQL 5.7+ or MySQL 8.0+

### Development Tools

- **Maven**: 3.6+
- **Git**: Latest version
- **Docker**: For containerized deployment (optional)

## Local Development Deployment

### 1. Database Setup

Install and configure MySQL:

```bash
# On Ubuntu/Debian
sudo apt update
sudo apt install mysql-server

# On macOS with Homebrew
brew install mysql

# On Windows, download from MySQL website
```

Create the database:

```sql
CREATE DATABASE expedition_companion;
CREATE USER 'expedition_user'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON expedition_companion.* TO 'expedition_user'@'localhost';
FLUSH PRIVILEGES;
```

### 2. Application Configuration

Copy and configure the application properties:

```bash
cp src/main/resources/application-development.properties.example src/main/resources/application-development.properties
```

Update the database credentials in `application-development.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/expedition_companion?useSSL=false&serverTimezone=UTC
spring.datasource.username=expedition_user
spring.datasource.password=your_password
```

### 3. Build and Run

```bash
# Build the application
./mvnw clean compile

# Run the application
./mvnw spring-boot:run

# Or build JAR and run
./mvnw clean package
java -jar target/Expedetion_Companion-0.0.1-SNAPSHOT.jar
```

The application will be available at `http://localhost:8080`

## Docker Deployment

### 1. Create Dockerfile

Create a `Dockerfile` in the project root:

```dockerfile
FROM openjdk:8-jdk-alpine

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src src

# Build application
RUN ./mvnw clean package -DskipTests

# Create final image
FROM openjdk:8-jre-alpine

WORKDIR /app

# Copy JAR file
COPY --from=0 /app/target/Expedetion_Companion-0.0.1-SNAPSHOT.jar app.jar

# Create non-root user
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

USER appuser

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD wget --quiet --tries=1 --spider http://localhost:8080/api/actuator/health || exit 1

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### 2. Create Docker Compose

Create `docker-compose.yml`:

```yaml
version: '3.8'

services:
  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=production
      - SPRING_DATASOURCE_URL=jdbc:mysql://db:3306/expedition_companion?useSSL=false&serverTimezone=UTC
      - SPRING_DATASOURCE_USERNAME=expedition_user
      - SPRING_DATASOURCE_PASSWORD=expedition_password
    depends_on:
      - db
    networks:
      - expedition-network
    restart: unless-stopped

  db:
    image: mysql:8.0
    environment:
      - MYSQL_ROOT_PASSWORD=root_password
      - MYSQL_DATABASE=expedition_companion
      - MYSQL_USER=expedition_user
      - MYSQL_PASSWORD=expedition_password
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql
      - ./init.sql:/docker-entrypoint-initdb.d/init.sql
    networks:
      - expedition-network
    restart: unless-stopped

volumes:
  mysql_data:

networks:
  expedition-network:
    driver: bridge
```

### 3. Deploy with Docker Compose

```bash
# Build and start services
docker-compose up -d

# View logs
docker-compose logs -f app

# Stop services
docker-compose down

# Stop and remove volumes
docker-compose down -v
```

## Cloud Deployment

### AWS Deployment

#### Using Elastic Beanstalk

1. **Prepare Application**

```bash
# Build JAR file
./mvnw clean package

# Create deployment directory
mkdir eb-deployment
cp target/Expedetion_Companion-0.0.1-SNAPSHOT.jar eb-deployment/application.jar
```

2. **Create Elastic Beanstalk Configuration**

Create `.ebextensions/application.config`:

```yaml
option_settings:
  aws:elasticbeanstalk:application:environment:
    SERVER_PORT: 5000
    SPRING_PROFILES_ACTIVE: production
    SPRING_DATASOURCE_URL: jdbc:mysql://your-rds-endpoint:3306/expedition_companion
    SPRING_DATASOURCE_USERNAME: expedition_user
    SPRING_DATASOURCE_PASSWORD: your_password
```

3. **Deploy**

```bash
# Install EB CLI
pip install awsebcli

# Initialize EB application
eb init expedition-companion

# Create environment
eb create production

# Deploy
eb deploy
```

#### Using ECS (Fargate)

1. **Create Task Definition**

```json
{
  "family": "expedition-companion",
  "networkMode": "awsvpc",
  "requiresCompatibilities": ["FARGATE"],
  "cpu": "512",
  "memory": "1024",
  "executionRoleArn": "arn:aws:iam::account:role/ecsTaskExecutionRole",
  "containerDefinitions": [
    {
      "name": "expedition-companion",
      "image": "your-account.dkr.ecr.region.amazonaws.com/expedition-companion:latest",
      "portMappings": [
        {
          "containerPort": 8080,
          "protocol": "tcp"
        }
      ],
      "environment": [
        {
          "name": "SPRING_PROFILES_ACTIVE",
          "value": "production"
        }
      ],
      "logConfiguration": {
        "logDriver": "awslogs",
        "options": {
          "awslogs-group": "/ecs/expedition-companion",
          "awslogs-region": "us-east-1",
          "awslogs-stream-prefix": "ecs"
        }
      }
    }
  ]
}
```

### Google Cloud Platform

#### Using App Engine

1. **Create `app.yaml`**

```yaml
runtime: java8
service: default

env_variables:
  SPRING_PROFILES_ACTIVE: production
  SPRING_DATASOURCE_URL: jdbc:mysql://google/expedition_companion?cloudSqlInstance=project:region:instance&socketFactory=com.google.cloud.sql.mysql.SocketFactory
  SPRING_DATASOURCE_USERNAME: expedition_user
  SPRING_DATASOURCE_PASSWORD: your_password

automatic_scaling:
  min_instances: 1
  max_instances: 10
```

2. **Deploy**

```bash
# Deploy to App Engine
gcloud app deploy
```

### Azure Deployment

#### Using App Service

1. **Create Resource Group and App Service**

```bash
# Create resource group
az group create --name expedition-companion-rg --location "East US"

# Create App Service plan
az appservice plan create --name expedition-companion-plan --resource-group expedition-companion-rg --sku B1 --is-linux

# Create web app
az webapp create --resource-group expedition-companion-rg --plan expedition-companion-plan --name expedition-companion-app --runtime "JAVA|8-jre8"
```

2. **Configure Application Settings**

```bash
az webapp config appsettings set --resource-group expedition-companion-rg --name expedition-companion-app --settings \
  SPRING_PROFILES_ACTIVE=production \
  SPRING_DATASOURCE_URL="jdbc:mysql://your-server.mysql.database.azure.com:3306/expedition_companion" \
  SPRING_DATASOURCE_USERNAME=expedition_user@your-server \
  SPRING_DATASOURCE_PASSWORD=your_password
```

3. **Deploy**

```bash
# Deploy JAR file
az webapp deployment source config-zip --resource-group expedition-companion-rg --name expedition-companion-app --src target/Expedetion_Companion-0.0.1-SNAPSHOT.jar
```

## Production Considerations

### 1. Security

- **Use HTTPS**: Configure SSL/TLS certificates
- **Environment Variables**: Store sensitive data in environment variables
- **Database Security**: Use strong passwords and restrict access
- **Firewall**: Configure firewall rules to restrict access

```properties
# Production security settings
server.ssl.enabled=true
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=${SSL_KEYSTORE_PASSWORD}
server.ssl.key-store-type=PKCS12

# Hide error details
server.error.include-stacktrace=never
server.error.include-message=never
```

### 2. Performance

- **JVM Tuning**: Optimize JVM parameters
- **Connection Pooling**: Configure database connection pooling
- **Caching**: Implement application-level caching

```bash
# JVM optimization
java -Xms2g -Xmx4g -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -jar app.jar
```

```properties
# Connection pool settings
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=30000
spring.datasource.hikari.idle-timeout=600000
spring.datasource.hikari.max-lifetime=1800000
```

### 3. Database

- **Use Managed Database**: Consider cloud-managed database services
- **Backup Strategy**: Implement regular backups
- **Read Replicas**: Use read replicas for scaling
- **Monitoring**: Monitor database performance

### 4. Load Balancing

For high-traffic applications, deploy multiple instances behind a load balancer:

```yaml
# docker-compose.yml with load balancer
version: '3.8'

services:
  nginx:
    image: nginx:alpine
    ports:
      - "80:80"
      - "443:443"
    volumes:
      - ./nginx.conf:/etc/nginx/nginx.conf
    depends_on:
      - app1
      - app2

  app1:
    build: .
    environment:
      - SPRING_PROFILES_ACTIVE=production

  app2:
    build: .
    environment:
      - SPRING_PROFILES_ACTIVE=production
```

## Monitoring and Logging

### 1. Application Monitoring

Enable Spring Boot Actuator endpoints:

```properties
# Actuator configuration
management.endpoints.web.exposure.include=health,info,metrics,prometheus
management.endpoint.health.show-details=when-authorized
management.metrics.export.prometheus.enabled=true
```

### 2. Log Management

Configure centralized logging:

```properties
# Logging configuration
logging.level.root=INFO
logging.level.spring.esprit.expedetion_companion=DEBUG
logging.file.name=logs/expedition-companion.log
logging.file.max-size=10MB
logging.file.max-history=30

# JSON logging for better parsing
logging.pattern.console=%d{ISO8601} [%thread] %-5level %logger{36} - %msg%n
logging.pattern.file=%d{ISO8601} [%thread] %-5level %logger{36} - %msg%n
```

### 3. Monitoring Stack

Deploy monitoring with Prometheus and Grafana:

```yaml
# monitoring/docker-compose.yml
version: '3.8'

services:
  prometheus:
    image: prom/prometheus
    ports:
      - "9090:9090"
    volumes:
      - ./prometheus.yml:/etc/prometheus/prometheus.yml

  grafana:
    image: grafana/grafana
    ports:
      - "3000:3000"
    environment:
      - GF_SECURITY_ADMIN_PASSWORD=admin
```

## Backup and Recovery

### 1. Database Backup

```bash
# Daily backup script
#!/bin/bash
DATE=$(date +%Y%m%d_%H%M%S)
BACKUP_DIR="/backups"
DB_NAME="expedition_companion"

# Create backup
mysqldump -u expedition_user -p expedition_companion > ${BACKUP_DIR}/expedition_companion_${DATE}.sql

# Compress backup
gzip ${BACKUP_DIR}/expedition_companion_${DATE}.sql

# Clean old backups (keep last 30 days)
find ${BACKUP_DIR} -name "expedition_companion_*.sql.gz" -mtime +30 -delete
```

### 2. Application Data Backup

```bash
# Backup application logs and configurations
tar -czf app_backup_$(date +%Y%m%d).tar.gz \
  logs/ \
  src/main/resources/application*.properties \
  docker-compose.yml
```

## Troubleshooting

### Common Issues

1. **Application Won't Start**
   - Check database connectivity
   - Verify configuration properties
   - Check Java version compatibility

2. **Database Connection Issues**
   - Verify MySQL is running
   - Check credentials and connection string
   - Ensure database exists

3. **Performance Issues**
   - Monitor memory usage
   - Check database query performance
   - Review connection pool settings

4. **High Memory Usage**
   - Tune JVM parameters
   - Check for memory leaks
   - Monitor garbage collection

### Debugging Commands

```bash
# Check application logs
docker-compose logs -f app

# Monitor resource usage
docker stats

# Check database connectivity
mysql -h localhost -u expedition_user -p expedition_companion

# Test API endpoints
curl -X GET http://localhost:8080/api/actuator/health

# Check Java processes
jps -v

# Monitor JVM
jstat -gc <pid> 5s
```

### Health Checks

```bash
# Application health
curl http://localhost:8080/api/actuator/health

# Database health
mysql -u expedition_user -p -e "SELECT 1"

# Memory check
free -h

# Disk space check
df -h
```

For additional support:
- GitHub Issues: [ExpeditionCompanion Issues](https://github.com/AhmedBouabda/ExpeditionCompanion/issues)
- Documentation: [README.md](../README.md)
- API Documentation: [API.md](API.md)