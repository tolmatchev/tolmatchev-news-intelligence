# AGENTS.md

## Build and Run
- Use the Maven wrapper for all builds: `./mvnw`
- Build and install project: `./mvnw clean install`
- Run the application: `./mvnw spring-boot:run`

## Tech Stack & Tools
- **Framework:** Spring Boot (WebMVC, Data JPA)
- **Database:** PostgreSQL (Schema: `tni`)
- **Migrations:** Flyway (Migrations in `src/main/resources/db/migration`)
- **Lombok:** Enabled via Maven compiler plugin.

## Project Structure
- **Service**: Business logic and integrations (src/main/java/com/tolmatchev/newsintelligence/service)
- **DTO**: Data Transfer Objects (src/main/java/com/tolmatchev/newsintelligence/dto)
- **Entity**: Database entities (src/main/java/com/tolmatchev/newsintelligence/entity)
- **Mapper**: Data mapping (src/main/java/com/tolmatchev/newsintelligence/mapper)
- **Configuration**: Application settings


- Data source and Flyway settings are in `src/main/resources/application.yaml`.
- Environment variables like `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_LOGIN`, and `DB_PASSWORD` are preferred for deployment.
