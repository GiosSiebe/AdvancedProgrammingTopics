# Microservices Project

## Project Overview

This project implements a microservices architecture with a focus on managing **users**, **moods**, **activities**, and **recommendations**. The backend is designed to allow users to explore activities based on their current mood, creating a personalized experience that enhances their well-being. The frontend complements this functionality, providing an intuitive user interface for interacting with the backend services.

## Microservices Architecture

The project consists of the following microservices:

1. **User Service**: Manages user accounts and authentication.
2. **Mood Service**: Allows users to post, update, and delete their moods.
3. **Activity Service**: Manages various activities available for users.
4. **Recommendation Service**: Generates recommendations based on user moods and selected activities.

### Technical Stack

- **Database**:
  - MongoDB: Used for storing user moods and activities.
  - MySQL: Used for storing user information and recommendations.
- **API Gateway**: Acts as the entry point for clients to interact with the microservices.
- **Authentication**: Implemented using GCP OAuth2 to secure endpoints.

## API Endpoints

### User Service
- **GET** `/users`: Retrieve all users.
- **POST** `/users`: Create a new user.
- **PUT** `/users/{id}`: Update user information.
- **DELETE** `/users/{id}`: Delete a user.

### Mood Service
- **GET** `/moods`: Retrieve all moods.
- **POST** `/moods`: Create a new mood.
- **PUT** `/moods/{id}`: Update a mood.
- **DELETE** `/moods/{id}`: Delete a mood.

### Activity Service
- **GET** `/activities`: Retrieve all activities.
- **POST** `/activities`: Create a new activity.
- **PUT** `/activities/{id}`: Update an activity.
- **DELETE** `/activities/{id}`: Delete an activity.

### Recommendation Service
- **GET** `/recommendations`: Retrieve recommendations based on user moods.
  
### Interaction between Services
The Recommendation Service interacts with both the Mood and Activity Services to generate tailored suggestions for users.

## Deployment

### Deployment Architecture
- **Docker**: Each microservice is containerized using Docker.
- **Docker Compose**: Manages the deployment of all services as a single application stack.
- **GitHub Actions**: Automates the build and deployment process of Docker containers.

### Screenshots of Endpoints
All API endpoints were tested using Postman. Below are some example requests demonstrating the functionality:

- **Get All Moods**: ![Get All Moods](link-to-screenshot)
- **Create a Mood**: ![Create Mood](link-to-screenshot)

## Technical Choices

- **MongoDB**: Chosen for its flexibility in handling unstructured data, ideal for storing diverse mood entries and activities.
- **MySQL**: Used for structured data management, ensuring ACID compliance for user-related data.

## Additional Features

- **Frontend Application**: Developed a user-friendly interface that allows users to manage their moods and activities, seamlessly interacting with the backend.
- **User Authentication**: Secure user login implemented via OAuth2, ensuring that sensitive user data is protected.

## Testing

All service classes include unit tests to verify the functionality of endpoints. Automated tests are run using GitHub Actions to ensure continuous integration and delivery.

## Lessons Learned

Through the development of this project, I gained valuable insights into microservices architecture, effective database management, and secure API development. The experience has enhanced my skills in creating scalable applications and managing complex interactions between services.

## Live Demo

A demo video showcasing the project’s structure, functionality, and frontend interaction is available here: [Demo Video](link-to-demo-video).

## Repository

The complete project code can be found on GitHub: [GitHub Repository](https://github.com/GiosSiebe/AdvancedProgrammingTopics).

## Conclusion

This project not only fulfills the academic requirements but also offers a meaningful application aimed at improving user engagement and mental well-being. The learnings and skills acquired during this project will undoubtedly aid in future software development endeavors.
