## GitHub User Info Service

### Overview

This is a simple RESTful service designed to retrieve user information from the GitHub API and return it in a structured format. The service provides the following user information:

- User ID
- GitHub Login
- User Name
- User Type
- Avatar URL
- Creation Date
- Calculations

### API Endpoint

The service exposes a single API endpoint:

#### GET /users/{login}

##### Request

- **login**: GitHub username (e.g., "octocat")

##### Response

```json
{
  "id": "...",
  "login": "...",
  "name": "...",
  "type": "...",
  "avatarUrl": "...",
  "createdAt": "...",
  "calculations": "..."
}
```
