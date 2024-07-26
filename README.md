# GitHub Repos API

This application allows you to list all non-fork GitHub repositories of a user and for each branch, it provides its name and last commit SHA.

## Requirements

- Java 21
- Spring Boot 3

## Running the Application

1. Clone the repository.
2. Navigate to the project directory.
3. Run the application using:
   ```sh
   ./gradlew bootRun
   ```
4. The application will start on `http://localhost:8080`.

## API Endpoints

- **List Repositories**
    - URL: `/api/users/{username}/repos`
    - Method: GET
    - Headers: `Accept: application/json`
    - Response:
      ```json
      [
          {
              "name": "repo-name",
              "owner": {
                  "login": "owner-login"
              }
          }
      ]
      ```

- **List Branches**
    - URL: `/api/repos/{owner}/{repo}/branches`
    - Method: GET
    - Headers: `Accept: application/json`
    - Response:
      ```json
      [
          {
              "name": "branch-name",
              "commit": {
                  "sha": "commit-sha"
              }
          }
      ]
      ```

## Error Handling

- If the user does not exist, a 404 response is returned:
  ```json
  {
      "status": 404,
      "message": "User not found"
  }
