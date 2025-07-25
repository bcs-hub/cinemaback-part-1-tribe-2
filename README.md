# Future Cinema Backend Project

This repository contains the backend for the Future cinema application which includes a Spring Boot application and relies on a PostgreSQL database for data persistence.

# Project Structure

## Package Structure
* controller - contains the API endpoints for
    * Genre
    * Movie
    * Room
    * Seance
    * Ticket
      * Type
    * User
* service - contains the business layer for
  * Genre
  * Movie
  * Room
  * Seance
  * Ticket
    * Type
  * User
* persistence - contains database layer for
  * Genre
  * Movie
  * Room
  * Seance
  * Ticket
    * Type
  * User
* Security - security related code

# Project setup

## Getting Started
Development: Install JDK 17 and Your favourite IDE (IntelliJ IDEA, VSCode, Eclipse, etc.)
Your IDE should detect the project as a Gradle project and automatically download the dependencies.
Build: `gradle build`
