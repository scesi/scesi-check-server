# Scesi Check Server - API Reference for Web Client

## Project Overview
- **Framework**: Spring Boot 4.1.0 + Java 25
- **Database**: PostgreSQL
- **Message Broker**: MQTT (Mosquitto) on port 1883
- **Build**: Gradle
- **Auth**: JWT in HttpOnly cookies (access_token + refresh_token)
- **Base URL**: Configurable via `baseUrl` parameter in login

---

## Authentication Flow

### Login (Email Magic Link)
```
POST /auth/login
Content-Type: application/json

{
  "email": "user@example.com"
}
Query Param: baseUrl=https://your-frontend.com
```

**Response** (200):
```json
{
  "statusCode": 200,
  "message": "Login link sent to your email",
  "data": {
    "emailSent": true,
    "message": "Login link sent to your email"
  }
}
```

### Refresh Token
```
POST /auth/refresh
Cookie: refresh_token=<token>
```

**Response** (200):
```json
{
  "statusCode": 200,
  "message": "Token refreshed",
  "data": null
}
```

### Logout
```
POST /auth/logout
```

**Response** (200):
```json
{
  "statusCode": 200,
  "message": "Logout successful",
  "data": null
}
```

### Callback (Magic Link Landing)
```
GET /auth/callback
```

**Response** (200):
```json
{
  "statusCode": 200,
  "message": "Login successful",
  "data": null
}
```

---

## Standard Response Format
All endpoints return:
```json
{
  "statusCode": 200,
  "message": "Human readable message",
  "data": <T>
}
```

---

## Settings (`/settings`)

### Get Settings
```
GET /settings
```

**Response** (200):
```json
{
  "statusCode": 200,
  "message": "Settings retrieved successfully",
  "data": {
    "id": 1,
    "absenceCost": "2.00",
    "lateArrivalCost": "20.00",
    "toleranceTimeMinutes": 5,
    "absenceThresholdMinutes": 30,
    "lastLateFeeGenerationDate": "2024-01-15T10:30:00Z"
  }
}
```

### Update Settings
```
PATCH /settings
Content-Type: application/json

{
  "absenceCost": "3.00",
  "lateArrivalCost": "25.00",
  "toleranceTimeMinutes": 10,
  "absenceThresholdMinutes": 45
}
```

**Validation**: All fields required, positive values, max 6 integer + 2 decimal digits

**Response** (200):
```json
{
  "statusCode": 200,
  "message": "Settings updated successfully",
  "data": {
    "id": 1,
    "absenceCost": "3.00",
    "lateArrivalCost": "25.00",
    "toleranceTimeMinutes": 10,
    "absenceThresholdMinutes": 45,
    "lastLateFeeGenerationDate": "2024-01-15T11:00:00Z"
  }
}
```

---

## User Management (`/user`)

### Get User by ID
```
GET /user/{userId}
```

### Get All Users
```
GET /user/
```

### Create User
```
POST /user/
Content-Type: application/json

{
  "name": "John",
  "lastName": "Doe",
  "email": "john@example.com"
}
```

### Update User
```
PATCH /user/{userId}
Content-Type: application/json

{
  "name": "Jane",
  "lastName": "Smith",
  "email": "jane@example.com",
  "active": false
}
```

### Delete User
```
DELETE /user/{userId}
```

### Assign Role to User
```
POST /user/{userId}/role/{rolId}
```

### Remove Role from User
```
DELETE /user/{userId}/role/{rolId}
```

### Get User Roles
```
GET /user/{userId}/rol/
```

---

## Role Management (`/rol`)

### Get Role by ID
```
GET /rol/{rolId}
```

### Get All Roles
```
GET /rol/
```

### Create Role
```
POST /rol/
Content-Type: application/json

{
  "rol": "SUPERVISOR"
}
```

### Update Role
```
PATCH /rol/{rolId}
Content-Type: application/json

{
  "rol": "MODERATOR"
}
```

### Delete Role
```
DELETE /rol/{rolId}
```

---

## Event Management (`/event`)

### Get Event by ID
```
GET /event/{eventId}
```

### Get All Events
```
GET /event/
```

### Create Event
```
POST /event/
Content-Type: application/json

{
  "title": "Morning Check-in",
  "description": "Daily attendance",
  "startTime": "2024-01-15T08:00:00Z",
  "endTime": "2024-01-15T10:00:00Z",
  "nextControl": "2024-01-16T08:00:00Z"
}
```

### Update Event
```
PATCH /event/{eventId}
```

### Delete Event
```
DELETE /event/{eventId}
```

---

## Attendance (`/attendance`)

### Upload CSV Attendance
```
POST /attendance/upload-csv
Content-Type: multipart/form-data
file: <CSV file>
```

---

## Late Fees (`/late-fee`)

### Get All Late Fees
```
GET /late-fee/
```

### Toggle Late Fee Payment Status
```
PATCH /late-fee/{lateFeeId}
```

### Download Unpaid Late Fees PDF Report
```
GET /late-fee/pdf
Accept: application/pdf
```

---

## Fingerprint / MQTT Device Management (`/user`)

### Enroll Fingerprint
```
POST /user/{userId}/fingerprint
```

### Delete Specific Fingerprint
```
DELETE /user/{userId}/fingerprint/{finger}
```

### Delete All User Fingerprints
```
DELETE /user/{userId}/fingerprint
```

---

## WiFi Configuration (`/user`)

### Add WiFi Network
```
POST /user/wifi
Content-Type: application/x-www-form-urlencoded
ssid=MyNetwork&pass=password123
```

### Delete WiFi Network
```
DELETE /user/wifi/{ssid}
```

### List WiFi Networks
```
GET /user/wifi
```

---

## Authentication Details for Client

### Cookie-Based Auth
- **Access Token**: `access_token` (HttpOnly, Secure, SameSite=Lax)
- **Refresh Token**: `refresh_token` (HttpOnly, Secure, SameSite=Lax)

### CORS Configuration
- Allowed origins: Configurable via `CORS_ALLOWED_ORIGINS` env var
- Credentials: `true` (cookies included)

### Protected Routes
- All routes except `/auth/**` require valid `access_token` cookie
- 401 response: `{"error":"Unauthorized","message":"Authentication required"}`

---

## Environment Variables

```bash
DB_HOST=localhost
DB_PORT=5432
DB_NAME=check_db
DB_USER=postgres
DB_PASSWORD=secret

MQTT_HOST=localhost
MQTT_PORT=1883
MQTT_USER=
MQTT_PASS=

MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=
MAIL_PASSWORD=
MAIL_FROM=

JWT_SECRET=your-secret-key
JWT_ACCESS_TOKEN_EXPIRATION=900000
JWT_REFRESH_TOKEN_EXPIRATION=604800000

CORS_ALLOWED_ORIGINS=http://localhost:3000,http://localhost:5173
COOKIE_SECURE=false
COOKIE_DOMAIN=localhost
COOKIE_PATH=/
```

---

## Client Implementation Checklist

### Auth Pages
- [ ] Login page (email input → `/auth/login`)
- [ ] Callback page (`/auth/callback` redirect → dashboard)

### API Client Setup
- [ ] Axios/fetch wrapper with `credentials: 'include'`
- [ ] 401 interceptor → auto-refresh via `/auth/refresh`
- [ ] Base URL from env

### Settings UI
- [ ] Settings view (GET `/settings`)
- [ ] Settings edit form (PATCH `/settings`)

### User Management UI
- [ ] User list (GET `/user/`)
- [ ] Create user modal (POST `/user/`)
- [ ] Edit user (PATCH `/user/{id}`)
- [ ] Delete user (DELETE `/user/{id}`)
- [ ] Role assignment (POST/DELETE `/user/{id}/role/{rolId}`)
- [ ] View user roles (GET `/user/{id}/rol/`)

### Role Management UI
- [ ] Role list (GET `/rol/`)
- [ ] Create/Edit/Delete role

### Event Management UI
- [ ] Event list (GET `/event/`)
- [ ] Create/Edit/Delete event
- [ ] Date/time pickers for `startTime`, `endTime`, `nextControl`

### Attendance UI
- [ ] CSV upload drag-drop (POST `/attendance/upload-csv`)

### Late Fees UI
- [ ] Late fee list (GET `/late-fee/`)
- [ ] Toggle payment status (PATCH `/late-fee/{id}`)
- [ ] Download PDF report (GET `/late-fee/pdf`)

### Device Management UI
- [ ] Fingerprint enrollment (POST `/user/{id}/fingerprint`)
- [ ] Fingerprint list/delete
- [ ] WiFi config list/add/delete

---

## Error Handling

All errors follow StandardResponse format with appropriate HTTP status:
- 400: Validation errors
- 401: Unauthorized (token expired/invalid)
- 404: Not found
- 409: Conflict (duplicate email, role, etc.)
- 500: Server error

Check `statusCode` and `message` in response for user-friendly errors.