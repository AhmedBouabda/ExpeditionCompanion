# API Documentation

## Overview

The Expedition Companion API provides RESTful endpoints for managing expeditions, teams, equipment, and related resources. All endpoints follow REST conventions and return JSON responses.

## Base URL

```
Production: https://your-domain.com/api
Development: http://localhost:8080/api
```

## Authentication

*Note: Authentication will be implemented in future versions*

Currently, the API is open and does not require authentication. In future versions, we will implement:
- JWT-based authentication
- Role-based access control (RBAC)
- API key authentication for external integrations

## Response Format

All API responses follow a consistent format:

### Success Response
```json
{
  "status": "success",
  "data": {
    // Response data here
  },
  "timestamp": "2024-01-15T10:30:00Z"
}
```

### Error Response
```json
{
  "status": "error",
  "error": {
    "code": "RESOURCE_NOT_FOUND",
    "message": "Expedition with ID 123 not found",
    "details": "The requested expedition does not exist or has been deleted"
  },
  "timestamp": "2024-01-15T10:30:00Z"
}
```

## HTTP Status Codes

The API uses standard HTTP status codes:

- `200 OK` - Successful GET, PUT, or PATCH request
- `201 Created` - Successful POST request
- `204 No Content` - Successful DELETE request
- `400 Bad Request` - Invalid request data
- `401 Unauthorized` - Authentication required
- `403 Forbidden` - Insufficient permissions
- `404 Not Found` - Resource not found
- `409 Conflict` - Resource conflict (e.g., duplicate email)
- `422 Unprocessable Entity` - Validation errors
- `500 Internal Server Error` - Server error

## Pagination

List endpoints support pagination using query parameters:

- `page` - Page number (starting from 0)
- `size` - Number of items per page (default: 20, max: 100)
- `sort` - Sort field and direction (e.g., `name,asc` or `createdDate,desc`)

Example:
```
GET /api/expeditions?page=0&size=10&sort=name,asc
```

Response includes pagination metadata:
```json
{
  "status": "success",
  "data": {
    "content": [...],
    "page": {
      "number": 0,
      "size": 10,
      "totalElements": 45,
      "totalPages": 5,
      "first": true,
      "last": false
    }
  }
}
```

## API Endpoints

### Health Check

#### Get API Health Status
```
GET /actuator/health
```

**Response:**
```json
{
  "status": "UP",
  "components": {
    "db": {
      "status": "UP",
      "details": {
        "database": "MySQL",
        "validationQuery": "isValid()"
      }
    }
  }
}
```

### Expeditions

#### List All Expeditions
```
GET /expeditions
```

**Query Parameters:**
- `page` (optional) - Page number (default: 0)
- `size` (optional) - Page size (default: 20)
- `sort` (optional) - Sort criteria (default: createdDate,desc)
- `status` (optional) - Filter by status (planned, active, completed, cancelled)
- `search` (optional) - Search in name and description

**Response:**
```json
{
  "status": "success",
  "data": {
    "content": [
      {
        "id": 1,
        "name": "Mount Everest Expedition 2024",
        "description": "A challenging expedition to the summit of Mount Everest",
        "status": "planned",
        "startDate": "2024-05-01",
        "endDate": "2024-06-15",
        "maxTeamSize": 12,
        "currentTeamSize": 8,
        "location": {
          "country": "Nepal",
          "region": "Himalayas",
          "coordinates": {
            "latitude": 27.9881,
            "longitude": 86.9250
          }
        },
        "difficulty": "extreme",
        "createdDate": "2024-01-15T10:30:00Z",
        "updatedDate": "2024-01-20T14:45:00Z"
      }
    ],
    "page": {
      "number": 0,
      "size": 20,
      "totalElements": 1,
      "totalPages": 1
    }
  }
}
```

#### Get Expedition by ID
```
GET /expeditions/{id}
```

**Path Parameters:**
- `id` - Expedition ID

**Response:**
```json
{
  "status": "success",
  "data": {
    "id": 1,
    "name": "Mount Everest Expedition 2024",
    "description": "A challenging expedition to the summit of Mount Everest",
    "status": "planned",
    "startDate": "2024-05-01",
    "endDate": "2024-06-15",
    "maxTeamSize": 12,
    "currentTeamSize": 8,
    "location": {
      "country": "Nepal",
      "region": "Himalayas",
      "coordinates": {
        "latitude": 27.9881,
        "longitude": 86.9250
      }
    },
    "difficulty": "extreme",
    "team": [
      {
        "id": 1,
        "name": "John Doe",
        "role": "expedition_leader",
        "email": "john.doe@example.com"
      }
    ],
    "equipment": [
      {
        "id": 1,
        "name": "Mountaineering Boots",
        "category": "footwear",
        "quantity": 12,
        "status": "available"
      }
    ],
    "createdDate": "2024-01-15T10:30:00Z",
    "updatedDate": "2024-01-20T14:45:00Z"
  }
}
```

#### Create New Expedition
```
POST /expeditions
```

**Request Body:**
```json
{
  "name": "Amazon Rainforest Research Expedition",
  "description": "A scientific expedition to study biodiversity in the Amazon rainforest",
  "startDate": "2024-07-01",
  "endDate": "2024-07-21",
  "maxTeamSize": 8,
  "location": {
    "country": "Brazil",
    "region": "Amazon Basin",
    "coordinates": {
      "latitude": -3.4653,
      "longitude": -62.2159
    }
  },
  "difficulty": "moderate"
}
```

**Response:**
```json
{
  "status": "success",
  "data": {
    "id": 2,
    "name": "Amazon Rainforest Research Expedition",
    "status": "planned",
    "currentTeamSize": 0,
    // ... other fields
    "createdDate": "2024-01-25T09:15:00Z",
    "updatedDate": "2024-01-25T09:15:00Z"
  }
}
```

#### Update Expedition
```
PUT /expeditions/{id}
```

**Path Parameters:**
- `id` - Expedition ID

**Request Body:** (same as create, all fields required)

#### Partially Update Expedition
```
PATCH /expeditions/{id}
```

**Path Parameters:**
- `id` - Expedition ID

**Request Body:** (only fields to be updated)
```json
{
  "status": "active",
  "currentTeamSize": 10
}
```

#### Delete Expedition
```
DELETE /expeditions/{id}
```

**Path Parameters:**
- `id` - Expedition ID

**Response:** `204 No Content`

### Team Management

#### Get Expedition Team
```
GET /expeditions/{expeditionId}/team
```

**Response:**
```json
{
  "status": "success",
  "data": [
    {
      "id": 1,
      "name": "John Doe",
      "email": "john.doe@example.com",
      "role": "expedition_leader",
      "skills": ["mountaineering", "first_aid", "navigation"],
      "experience": "10+ years",
      "joinedDate": "2024-01-15T10:30:00Z"
    }
  ]
}
```

#### Add Team Member
```
POST /expeditions/{expeditionId}/team
```

**Request Body:**
```json
{
  "name": "Jane Smith",
  "email": "jane.smith@example.com",
  "role": "guide",
  "skills": ["rock_climbing", "wilderness_survival"],
  "experience": "5 years"
}
```

#### Remove Team Member
```
DELETE /expeditions/{expeditionId}/team/{memberId}
```

### Equipment Management

#### Get Expedition Equipment
```
GET /expeditions/{expeditionId}/equipment
```

#### Add Equipment
```
POST /expeditions/{expeditionId}/equipment
```

**Request Body:**
```json
{
  "name": "Climbing Rope",
  "category": "safety",
  "quantity": 4,
  "description": "Dynamic climbing rope, 60m length",
  "weight": 4.2,
  "status": "available"
}
```

### Statistics and Reports

#### Get Expedition Statistics
```
GET /expeditions/{id}/statistics
```

**Response:**
```json
{
  "status": "success",
  "data": {
    "totalDays": 45,
    "completionPercentage": 75,
    "teamSize": 10,
    "equipmentItems": 150,
    "milestonesCompleted": 8,
    "totalMilestones": 12
  }
}
```

## Error Handling

### Validation Errors

When request validation fails, the API returns a `422 Unprocessable Entity` response:

```json
{
  "status": "error",
  "error": {
    "code": "VALIDATION_FAILED",
    "message": "Request validation failed",
    "details": [
      {
        "field": "name",
        "message": "Name is required"
      },
      {
        "field": "startDate",
        "message": "Start date must be in the future"
      }
    ]
  },
  "timestamp": "2024-01-15T10:30:00Z"
}
```

### Common Error Codes

- `RESOURCE_NOT_FOUND` - Requested resource doesn't exist
- `VALIDATION_FAILED` - Request validation errors
- `DUPLICATE_RESOURCE` - Resource already exists
- `INSUFFICIENT_PERMISSIONS` - User lacks required permissions
- `RESOURCE_CONFLICT` - Operation conflicts with current state
- `INTERNAL_ERROR` - Unexpected server error

## Rate Limiting

*Note: Rate limiting will be implemented in future versions*

Planned rate limits:
- 1000 requests per hour per API key
- 100 requests per minute per IP address

## Webhooks

*Note: Webhooks will be implemented in future versions*

Planned webhook events:
- `expedition.created`
- `expedition.updated`
- `expedition.status_changed`
- `team.member_added`
- `team.member_removed`

## SDK and Client Libraries

*Note: Client libraries will be developed in future versions*

Planned SDKs:
- JavaScript/TypeScript
- Python
- Java
- C#

## Changelog

### Version 1.0.0 (Planned)
- Initial API release
- Basic expedition CRUD operations
- Team management
- Equipment tracking

### Version 0.1.0 (Current)
- Basic Spring Boot application structure
- Database configuration
- Health check endpoint

## Support

For API support:
- GitHub Issues: [ExpeditionCompanion Issues](https://github.com/AhmedBouabda/ExpeditionCompanion/issues)
- Documentation: This file
- Email: support@expeditioncompanion.com (coming soon)